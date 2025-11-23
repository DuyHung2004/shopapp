package com.example.shopapp.service;

import com.example.shopapp.dto.reponse.AuthenticationReponse;
import com.example.shopapp.dto.reponse.IntrospectReponse;
import com.example.shopapp.dto.request.IntrospectRequest;
import com.example.shopapp.dto.request.LoginRequest;
import com.example.shopapp.exception.AppException;
import com.example.shopapp.exception.ErrorCode;
import com.example.shopapp.models.User;
import com.example.shopapp.repository.TokenRepository;
import com.example.shopapp.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    UserRepository userRepository;
    TokenRepository tokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;
    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    public IntrospectReponse introspectReponse(IntrospectRequest request) throws JOSEException, ParseException {
        var token= request.getToken();
        boolean inValid= true;
        String role = null;
        try {
            SignedJWT jwt =verifyToken(token,false);
            role = jwt.getJWTClaimsSet().getStringClaim("scope").substring(5);
        }catch (AppException e){
            inValid= false;
        }
        return IntrospectReponse.builder()
                .valid(inValid)
                .role(role)
                .build();
    }


    public AuthenticationReponse login(LoginRequest request){
        var user= userRepository.findByPhonenumber(request.getPhonenumber())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPass(), user.getPass());
        if(!authenticated){
            throw  new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        var token= generateToken(user);
        return  AuthenticationReponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public AuthenticationReponse loginadmin(LoginRequest request){
        var user= userRepository.findByPhonenumber(request.getPhonenumber())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        if(user.getRole_id().getId()!=2){
            throw new RuntimeException() ;
        }
        PasswordEncoder passwordEncoder= new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPass(), user.getPass());
        if(!authenticated){
            throw  new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        var token= generateToken(user);
        return  AuthenticationReponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    String generateToken(User user){
        JWSHeader header= new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet= new JWTClaimsSet.Builder()
                .subject(user.getPhonenumber())
                .issuer("theend5x11")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("userid",user.getId())
                .claim("scope","ROLE_"+user.getRole_id().getName())
                .build();
        Payload payload= new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject= new JWSObject(header,payload);
        try{
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch(JOSEException e){
            Logger log = null;
            log.error("cannot create token",e);
            throw new RuntimeException(e);
        }
    }
    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        log.info("Verifying token: {}", token);
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT= SignedJWT.parse(token);
        Date expityTime= (isRefresh)
                ? new Date( signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                :signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified= signedJWT.verify(verifier);
        log.info("Token verified: {}", verified);
        if (!(verified  && expityTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        if (tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        return signedJWT;
    }
}
