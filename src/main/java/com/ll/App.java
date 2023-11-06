package com.ll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public void run() {
        System.out.println("== 명언 앱 ==");
        int lastQuotationId = 0; // 명언 번호
        List<Quotation> quotations = new ArrayList<>();

        while (true) {
            System.out.print("명령) ");

            Scanner s = new Scanner(System.in);
            String cmd = s.nextLine();

            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
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
            } else if (cmd.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
            }
        }
    }
}
