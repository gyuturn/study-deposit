package com.study.deposit.domain.point.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.study.deposit.domain.point.domain.PointRecord;
import com.study.deposit.domain.point.dto.PointRecordPrepareDto;
import com.study.deposit.global.common.code.pointrecord.PointRecordErrorCode;
import com.study.deposit.global.common.exception.payment.PaymentException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class IamPortService {

    @Value("${imp_key}")
    private String impKey;

    @Value("${imp_secret}")
    private String impSecret;

    private final PointRecordService pointRecordService;

    /**
     * iamport token을 얻어오는 로직
     *
     * @return token -> iam port 에서 가져오는 토큰
     * @throws IOException
     */
    public String getToken() throws IOException {

        HttpsURLConnection conn = null;

        URL url = new URL("https://api.iamport.kr/users/getToken");

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        JsonObject json = new JsonObject();

        json.addProperty("imp_key", impKey);
        json.addProperty("imp_secret", impSecret);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        Gson gson = new Gson();

        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();

        System.out.println(response);

        String token = gson.fromJson(response, Map.class).get("access_token").toString();

        br.close();
        conn.disconnect();

        return token;
    }

    public boolean validPayment(Long chargeForIamPort, PointRecord chargeForDb) throws IOException {
        //아이엠포트 결제 금액과 DB내의 결제금액 비교
        log.info("Iamport 결제금액:{}, db내의 금액:{}", chargeForIamPort, chargeForDb.getChargeAmount());
        if (!chargeForIamPort.equals(chargeForDb.getChargeAmount())) {
            return false;
        }
        return true;
    }

    public void rollbackPayment(PointRecord notValidPointRecord, String accessToken, String imp_uid)
            throws IOException {
        log.error("Iamport 결제금액과 db내의 금액이 일치하지 않음. 해당 pointRecord 롤백(삭제)");
        pointRecordService.deleteRecord(notValidPointRecord);
        paymentCancel(accessToken, imp_uid);
        throw new PaymentException(PointRecordErrorCode.NOT_VALID_PAYMENT, HttpStatus.CONFLICT);

    }


    public void paymentPrepare(PointRecordPrepareDto prepareInfo, String token) throws IOException {
        log.info("iam port 사전결제 진행");
        String url = "https://api.iamport.kr/payments/prepare?_token=" + token;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestJson = new JSONObject();
        requestJson.put("merchant_uid", prepareInfo.getMerchant_uid()); // 가맹점 주문번호
        requestJson.put("amount", prepareInfo.getAmount()); // 결제 예정금액
        HttpEntity<String> entity = new HttpEntity<String>(requestJson.toString(), headers);
        restTemplate.postForEntity(url, entity, String.class);
    }

    /**
     * 결제 정보 확인(iamport)
     *
     * @param imp_uid -> 프론트 결제 과정에서 생성되는 imp_uid
     * @param token   -> Iamport access token
     * @return -> 결제 금액
     * @throws IOException
     */
    public Long paymentInfo(String imp_uid, String token) throws IOException, ParseException {

        log.info("iam port 단건조회, imp_uid:{}", imp_uid);
        String url = "https://api.iamport.kr/payments/" + imp_uid + "?_token=" + token;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        Long amount = parseForAmount(response);
        return amount;
    }

    private Long parseForAmount(ResponseEntity<String> response) {
        JSONParser parser = new JSONParser();
        Long amount = null;
        try {
            JSONObject obj = (JSONObject) parser.parse(response.getBody());
            JSONObject responseObject = (JSONObject) obj.get("response");
            amount = (Long) responseObject.get("amount");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return amount;
    }

    /**
     * 결제 취소
     *
     * @param access_token
     * @param imp_uid      -> 프론트 결제에서 보내는 imp_uid
     * @throws IOException
     */
    private void paymentCancel(String access_token, String imp_uid) throws IOException {
        System.out.println("결제 취소");

        System.out.println(access_token);

        System.out.println(imp_uid);

        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/payments/cancel");

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");

        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", access_token);

        conn.setDoOutput(true);

        JsonObject json = new JsonObject();

        json.addProperty("imp_uid", imp_uid);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        br.close();
        conn.disconnect();


    }
}
