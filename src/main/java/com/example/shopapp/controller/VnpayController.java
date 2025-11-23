package com.example.shopapp.controller;

import com.example.shopapp.dto.request.VnpayRefund;
import com.example.shopapp.dto.request.VnpayRequest;
import com.example.shopapp.exception.AppException;
import com.example.shopapp.exception.ErrorCode;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.User;
import com.example.shopapp.models.Vnpay;
import com.example.shopapp.repository.OrderRepository;
import com.example.shopapp.repository.UserRepository;
import com.example.shopapp.repository.VnpayRepository;
import com.example.shopapp.service.Config;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/api/payment")
@Slf4j
public class VnpayController {
    VnpayRepository vnpayRepository;

    @GetMapping("/vnpay-callback")
    public void VNpayCallBack(HttpServletRequest req, HttpServletResponse response,
                              @RequestParam("vnp_Amount") String amount,
                              @RequestParam("vnp_BankCode") String bankCode,
                              @RequestParam("vnp_BankTranNo") String bankTranNo,
                              @RequestParam("vnp_CardType") String cardType,
                              @RequestParam("vnp_OrderInfo") String orderInfo,
                              @RequestParam("vnp_PayDate") String payDate,
                              @RequestParam("vnp_ResponseCode") String responseCode,
                              @RequestParam("vnp_TmnCode") String tmnCode,
                              @RequestParam("vnp_TransactionNo") String transactionNo,
                              @RequestParam("vnp_TransactionStatus") String transactionStatus,
                              @RequestParam("vnp_TxnRef") String txnRef) throws IOException {
        String qs = req.getQueryString();
        if (qs == null) qs = "";
        int idx = qs.indexOf("vnp_SecureHash=");
        String paramPart = "";
        String secureHash = "";

        if (idx > 0) {
            paramPart = qs.substring(0, idx - 1);
            secureHash = qs.substring(idx + "vnp_SecureHash=".length());
        }
        String vnp_SecureHash=Config.hmacSHA512(Config.secretKey, paramPart.toString());
        if (!vnp_SecureHash.equals(secureHash)){
            response.sendRedirect("http://localhost:3000/?status=fail&reason=invalid-signature");
            return;
        }
        if (responseCode.equals("00")){
            Vnpay vnpay=   vnpayRepository.findByVnpTxnRef(txnRef).orElseThrow(() -> new RuntimeException("Payment not found"));
            vnpay.setVnpTransactionNo(transactionNo);
            vnpay.setVnpPayDate(payDate);
            vnpay.setOrder(vnpay.getOrder());
            vnpay.setStatus("SUCCESS");
            vnpayRepository.save(vnpay);
            response.sendRedirect("http://localhost:3000/?status=success&orderId="+txnRef+"&amount="+amount);
        } else if (responseCode.equals("94")) {
            response.sendRedirect("http://localhost:3000/?status=fail&orderId="+txnRef+"&code="+responseCode);
        } else{
            response.sendRedirect("http://localhost:3000/?status=fail&orderId="+txnRef+"&code="+responseCode);
        }

    }
    @PostMapping("/vnpay-refund")
    public ResponseEntity<?> Refund(HttpServletRequest req, @RequestBody @Valid VnpayRefund request) throws IOException {
        String vnp_RequestId = Config.getRandomNumber(8);
        String vnp_Version = "2.1.0";
        String vnp_Command = "refund";
        String vnp_TmnCode = Config.vnp_TmnCode;
        String vnp_TransactionType = request.getTrantype();
        String vnp_TxnRef = request.getOrder_id();
        long amount = request.getAmount()*100;
        String vnp_Amount = String.valueOf(amount);
        String vnp_OrderInfo = "Hoan tien GD OrderId:" + vnp_TxnRef;
        String vnp_TransactionNo = "";
        String vnp_TransactionDate = request.getTrans_date();
        String vnp_CreateBy = request.getUser();
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        String vnp_IpAddr = Config.getIpAddress(req);

        JsonObject  vnp_Params = new JsonObject();
        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
        vnp_Params.addProperty("vnp_Version", vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.addProperty("vnp_TransactionType", vnp_TransactionType);
        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.addProperty("vnp_Amount", vnp_Amount);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
        if(vnp_TransactionNo != null && !vnp_TransactionNo.isEmpty())
        {
            vnp_Params.addProperty("vnp_TransactionNo", "{get value of vnp_TransactionNo}");
        }

        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransactionDate);
        vnp_Params.addProperty("vnp_CreateBy", vnp_CreateBy);
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);
        String hash_Data= String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode,
                vnp_TransactionType, vnp_TxnRef, vnp_Amount, vnp_TransactionNo, vnp_TransactionDate,
                vnp_CreateBy, vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);

        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hash_Data.toString());

        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);
        URL url = new URL (Config.vnp_ApiUrl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(vnp_Params.toString());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + vnp_Params);
        System.out.println("Response Code : " + responseCode);
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("responseCode", responseCode);
        result.put("response", response.toString());

        return ResponseEntity.ok(result);
    }

}
