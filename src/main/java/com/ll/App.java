package com.ll;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private Scanner s = new Scanner(System.in);
    private int lastQuotationId = 0; // 명언 번호
    private List<Quotation> quotations = new ArrayList<>(); // 명언 리스트
    private final static String filePath = "C:/techitStudy/mission/Mission_SonGyeongI/savedFile/"; // 파일 경로

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");

            String cmd = s.nextLine();

            // URL에서 queryString 분리 후 저장하는 클래스
            Rq rq = new Rq(cmd);

            switch (rq.getAction()) {
                case "종료":
                    return; // 함수 종료
                case "등록":
                    actionSave();
                    break;
                case "목록":
                    actionList();
                    break;
                case "삭제":
                    actionDelete(rq);
                    break;
                case "수정":
                    actionModify(rq);
                    break;
            }
        }
    }

    // 명언 등록
    private void actionSave() {
        System.out.print("명언 : ");
        String content = s.nextLine();

        System.out.print("작가 : ");
        String authorName = s.nextLine();

        lastQuotationId++;
        int id = lastQuotationId;

        // Quotation 객체 생성
        Quotation quotation = new Quotation(id, authorName, content);
        /*
        // 리스트에 저장
        quotations.add(quotation);
         */
        // 파일에 저장
        savedFile(quotation);

        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    // 명언 목록
    private void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        // quotations 리스트에서 모두 출력
        for (int i = quotations.size() - 1; i >= 0; i--) {
            Quotation quotation = quotations.get(i);
            System.out.printf("%d / %s / %s\n", quotation.getId(), quotation.getAuthorName(), quotation.getQuotation());
        }
    }

    // 명언 삭제
    private void actionDelete(Rq rq) {
        // queryString에서 id 추출하기
        int id = rq.getParamAsInt("id", 0);

        if (id == 0) {
            System.out.println("id를 정확히 입력해주세요.");
            return;
        }

        // id값을 확인하고 인덱스 확인 후 삭제
        for (int i = 0; i < quotations.size(); i++) {
            Quotation quotation = quotations.get(i);

            if (quotation.getId() == id) {
                quotations.remove(i); // 리스트에서 명언 삭제
                System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
                return;
            }
        }

        System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
    }

    private void actionModify(Rq rq) {
        // queryString에서 id 추출하기
        int id = rq.getParamAsInt("id", 0);

        if (id == 0) {
            System.out.println("id를 정확히 입력해주세요.");
            return;
        }

        // id값을 확인하고 인덱스 확인 후 삭제
        for (int i = 0; i < quotations.size(); i++) {
            Quotation quotation = quotations.get(i);

            if (quotation.getId() == id) {
                System.out.println("명언(기존) : " + quotation.getQuotation());
                System.out.print("명언 : ");
                String content = s.nextLine();

                System.out.println("작가(기존) : " + quotation.getAuthorName());
                System.out.print("작가 : ");
                String authorName = s.nextLine();

                quotation.setQuotation(content);
                quotation.setAuthorName(authorName);

                System.out.printf("%d번 명언이 수정되었습니다.\n", id);
                return;
            }
        }

        System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
    }

    private void savedFile(Quotation quotation) {
        try {
            FileWriter writer = new FileWriter(filePath + quotation.getId() + ".txt");
            writer.write(" / " + quotation.getAuthorName() + " / " + quotation.getQuotation());
            writer.close();
            System.out.println("파일 저장 완료");
        } catch (IOException e) {
            System.out.println("파일 저장 실패");
            e.printStackTrace();
        }
    }
}
