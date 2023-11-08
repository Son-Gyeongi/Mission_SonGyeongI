package com.ll;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

// URL에서 queryString 분리 후 저장하는 클래스
public class Rq {
    private String cmd; // 입력받는 값
    @Getter
    private String action; // 종료, 등록, 삭제, 수정
    private String queryString; // id=2&archive=true
    private Map<String, String> paramMap;

    public Rq(String cmd) {
        paramMap = new HashMap<>();
        this.cmd = cmd;

        // 예시 :  cmd == 삭제?id=2&archive=true
        String[] cmdBits = cmd.split("\\?", 2);
        action = cmdBits[0].trim(); // 삭제

        if (cmdBits.length == 1) {
            return;
        }

        queryString = cmdBits[1].trim(); // id=2&archive=true

        String[] queryStringBits = queryString.split("&");

        for (int i = 0; i < queryStringBits.length; i++) {
            String queryParamStr = queryStringBits[i];
            String[] queryParamStrBits = queryParamStr.split("=", 2);

            String paramName = queryParamStrBits[0];
            String paramValue = queryParamStrBits[1];

            paramMap.put(paramName, paramValue);
        }
    }

    // "id"의 값 반환
    public int getParamAsInt(String paramName, int defaultValue) {
        String paramValue = paramMap.get(paramName);

        if (paramValue != null) {
            try {
                return Integer.parseInt(paramValue);
            } catch (NumberFormatException e) {

            }
        }

        return defaultValue;
    }
}
