package com.ll;

import java.io.*;
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
                case "빌드":
                    actionBuild();
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

        /*
        lastQuotationId++;
        int id = lastQuotationId;

        // 리스트에 저장
        quotations.add(quotation);
         */

        // 파일에서 불러오기
        List<Quotation> fileContents = readFiles();
        // 제일 최상위 id 불러오기
        lastQuotationId = fileContents.get(fileContents.size() - 1).getId();
        int id = lastQuotationId +1;

        // Quotation 객체 생성
        Quotation quotation = new Quotation(id, authorName, content);

        // 파일에 저장
        savedFile(quotation);

        System.out.printf("%d번 명언이 등록되었습니다.\n", id);
    }

    // 명언 목록
    private void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        /*
        // quotations 리스트에서 모두 출력
        for (int i = quotations.size() - 1; i >= 0; i--) {
            Quotation quotation = quotations.get(i);
            System.out.printf("%d / %s / %s\n", quotation.getId(), quotation.getAuthorName(), quotation.getQuotation());
        }
         */

        // 파일에서 불러오기
        List<Quotation> fileContents = readFiles();

        for (int i = fileContents.size() - 1; i >= 0; i--) {
            Quotation quotation = fileContents.get(i);
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

    // 명언 데이터 빌드 / data.json 빌드
    private void actionBuild() {

    }

    // 파일 저장
    private void savedFile(Quotation quotation) {
        try {
            FileWriter writer = new FileWriter(filePath + quotation.getId() + ".txt");
            writer.write(quotation.getAuthorName() + " / " + quotation.getQuotation());
            writer.close();
            System.out.println("파일 저장 완료");
        } catch (IOException e) {
            System.out.println("파일 저장 실패");
            e.printStackTrace();
        }
    }

    // 파일들 불러오기
    private List<Quotation> readFiles() {
        List<Quotation> fileContents = new ArrayList<>();

        File folder = new File(filePath);
        File[] files = folder.listFiles();

        List<String> fileList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getName());

                    // 파일 제목에서 id 뽑아내기 / 1.txt
                    String[] fileNameBits = file.getName().split("\\.", 2);
                    int id = Integer.parseInt(fileNameBits[0]);

                    // 파일 내용 출력
                    String fileContent = readContentFromFile(file);
                    String[] contentBits = fileContent.split("/", 2);
                    String authorName = contentBits[0].trim();
                    String content = contentBits[1].trim();

                    Quotation quotation = new Quotation(id, authorName, content);
                    fileContents.add(quotation);
                }
            }
        }

        return fileContents;
    }

    // file 내용 불러오기
    private static String readContentFromFile(File file) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
