package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    Scanner s = new Scanner(System.in);
    int lastQuotationId = 0; // 명언 번호
    List<Quotation> quotations = new ArrayList<>(); // 명언 리스트

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");

            String cmd = s.nextLine();

            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
                actionSave();
            } else if (cmd.equals("목록")) {
                actionList();
            } else if (cmd.startsWith("삭제?")) {
                actionDelete(cmd);
            }
        }
    }

    // 명언 등록
    void actionSave() {
        System.out.print("명언 : ");
        String content = s.nextLine();

        System.out.print("작가 : ");
        String authorName = s.nextLine();

        lastQuotationId++;
        int id = lastQuotationId;

        // Quotation 객체 생성
        Quotation quotation = new Quotation(id, authorName, content);
        // 리스트에 저장
        quotations.add(quotation);

        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    // 명언 목록
    void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        // quotations 리스트에서 모두 출력
        for (int i = quotations.size() - 1; i >= 0; i--) {
            Quotation quotation = quotations.get(i);
            System.out.printf("%d / %s / %s\n", quotation.id, quotation.authorName, quotation.quotation);
        }
    }

    // 명언 삭제
    void actionDelete(String cmd) {
        // queryString에서 id 추출하기
        int id = getParamAsInt(cmd, "id", 0);

        if (id == 0) {
            System.out.println("id를 정확히 입력해주세요.");
            return;
        }

        // id값을 확인하고 인덱스 확인 후 삭제
        for (int i = 0; i < quotations.size(); i++) {
            Quotation quotation = quotations.get(i);

            if (quotation.id == id) {
                quotations.remove(i); // 리스트에서 명언 삭제
                System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
                break;
            }
        }

        System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
    }

    // param에서 id 추출
    int getParamAsInt(String cmd, String paramName, int defaultValue) {
        // id=2&archive=true 저장
        List<String> paramNames = new ArrayList<>();
        List<String> paramValues = new ArrayList<>();
        int id = 0; // queryString의 id값 정수형

        try { // '삭제?' 입력이 들어올 경우
            // queryString에서 id 추출하기
            // 예시 :  cmd == 삭제?id=2&archive=true
            String[] cmdBits = cmd.split("\\?", 2);
//                String action = cmdBits[0]; // 삭제
            String queryString = cmdBits[1]; // id=2&archive=true

            // 삭제만 들어왔을 경우
            if (cmdBits.length == 1) {
                return defaultValue;
            }

            String[] queryStringBits = queryString.split("&");

            for (int i = 0; i < queryStringBits.length; i++) {
                String[] queryStrParamBits = queryStringBits[i].split("=", 2);
                String _paramName = queryStrParamBits[0];
                String paramValue = queryStrParamBits[1];

                paramNames.add(_paramName);
                paramValues.add(paramValue);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return defaultValue;
        }

        for (int i = 0; i < paramNames.size(); i++) {
            try {
                // paramNames에서 "id" 찾기
                if (paramNames.get(i).equals(paramName)) {
                    return Integer.parseInt(paramValues.get(i)); // // queryString의 id값
                }
            } catch (NumberFormatException e) {
            }
        }

        return defaultValue;
    }
}
