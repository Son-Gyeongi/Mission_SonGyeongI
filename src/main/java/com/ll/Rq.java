package com.ll;

import java.util.ArrayList;
import java.util.List;

// URL에서 queryString 분리 후 저장하는 클래스
public class Rq {
    String cmd; // 입력받는 값
    String action; // 종료, 등록, 삭제, 수정
    String queryString; // id=2&archive=true
    List<String> paramNames;
    List<String> paramValues;

    Rq(String cmd) {
        paramNames = new ArrayList<>();
        paramValues = new ArrayList<>();

        this.cmd = cmd;

        // 예시 :  cmd == 삭제?id=2&archive=true
        String[] cmdBits = cmd.split("\\?", 2);
        action = cmdBits[0].trim(); // 삭제
        queryString = cmdBits[1].trim(); // id=2&archive=true

        String[] queryStringBits = queryString.split("&");

        for (int i = 0; i < queryStringBits.length; i++) {
            String queryParamStr = queryStringBits[i];
            String[] queryParamStrBits = queryParamStr.split("=", 2);

            String _paramName = queryParamStrBits[0];
            String paramValue = queryParamStrBits[1];

            paramNames.add(_paramName);
            paramValues.add(paramValue);
        }
    }

    String getAction() {
        return action;
    }

    // "id"의 값 반환
    public int getParamAsInt(String paramName, int defaultValue) {
        // indexOf() : 리스트에 맞는 값을 넣으면 인덱스값을 주고 없으면 -1을 반환한다.
        int index = paramNames.indexOf(paramName); // paramName은 "id"

        if (index == -1) return defaultValue;

        String paramValue = paramValues.get(index);

        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
