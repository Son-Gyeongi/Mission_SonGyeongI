package com.ll;

import java.util.Scanner;

public class App {

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");

            Scanner s = new Scanner(System.in);
            String cmd = s.nextLine();

            if (cmd.equals("종료")) {
                break;
            }
        }
    }
}
