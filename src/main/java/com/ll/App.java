package com.ll;

import java.util.Scanner;

public class App {

    public void run() {
        System.out.println("== 명언 앱 ==");
        int lastQuotationId = 0; // 명언 번호

        while (true) {
            System.out.print("명령) ");

            Scanner s = new Scanner(System.in);
            String cmd = s.nextLine();

            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
                System.out.print("명언 : ");
                s.nextLine();

                System.out.print("작가 : ");
                s.nextLine();

                lastQuotationId++;
                int id = lastQuotationId;

                System.out.printf("%d번 명언이 등록되었습니다.\n", id);
            }
        }
    }
}
