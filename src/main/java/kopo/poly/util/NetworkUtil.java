package kopo.poly.util;

import org.springframework.lang.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class NetworkUtil {

    /**
     * GET 방식으로 OpenAPI 호출하기(전송할 헤더값이 존재하지 않는 경우 사용)
     * 네이버 API 전송 소스 참고하여 구현
     *
     * @param apiUrl 호출할 OpenAPI URL 주소
     */
    public static String get(String apiUrl) {

        return get(apiUrl, null);
    }

    /**
     * GET 방식으로 OpenAPI 호출하기
     * 네이버 API 전송 소스 참고하여 구현
     *
     * @param apiUrl 호출할 OpenAPI URL 주소
     */
    public static String get(String apiUrl, @Nullable Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);

        try {
            con.setRequestMethod("GET");

            if (requestHeaders != null) {
                for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                    con.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            // API 호출 후, 결과 받기
            int responseCode = con.getResponseCode();

            // API 호출이 성공하면..
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }

        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl) {

        try {
            URL url = new URL(apiUrl);

            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    public static String post(String apiUrl, @Nullable Map<String, String> requestHeaders, String postParams) throws ProtocolException {
        HttpURLConnection con = connect(apiUrl);

        try {
            con.setRequestMethod("POST");

            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }

        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

}
