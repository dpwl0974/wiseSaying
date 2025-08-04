package com.back;

import java.util.Scanner;

//핵심 로직 클래스
public class App {
    // ✅ run() 및 다른 함수 사용위해 인스턴스 변수로 변경 (지역 변수 X)
    Scanner sc = new Scanner(System.in);
    int lastNo = 0; //등록 번호
    int lastIndex = 0; //배열 인덱스
    WiseSaying[] wiseSayings = new WiseSaying[100]; //크기 100 배열


    public void run() {  //main 무조건 static ⭕️ 그 외 static ❌
        System.out.println("== 명언 앱 ==");

        while (true) { //가독성 및 유지보수성 위해 refactoring
            System.out.print("명령) ");
            String command = sc.nextLine();

            if (command.equals("등록")) {
                actionWrite();

            } else if (command.equals("목록")) {
                actionList();

            } else if (command.equals("삭제?id=1")) {
                actionDelete();

            } else if (command.equals("종료")) {
                break;
            }
        }
    }

    //삭제
    public void actionDelete() {
        delete();
        System.out.println("1번 명언이 삭제되었습니다.");
    }

    //삭제 (데이터 처리)
    public void delete() {
        int deleteTargetIndex = -1; // 삭제하고 싶은 명언이 저장된 위치

        for(int i = 0; i < lastIndex; i++) {
            if(wiseSayings[i].id == 1) {
                deleteTargetIndex = i;
            }
        }

        for(int i = deleteTargetIndex; i < lastIndex; i++) {
            wiseSayings[i] = wiseSayings[i + 1];
        }

        lastIndex--;
    }

    //등록 (사용자와 상호작용 담당)
    public void actionWrite() {
        System.out.print("명언 : ");
        String saying = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        WiseSaying wiseSaying = write(saying, author); //지역변수 접근위해 파라미터 전달

        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.id)); //return 받은 값 사용
    }

    //등록 (데이터 처리 담당)
    public WiseSaying write(String saying, String author) {
        lastNo++;

        WiseSaying wiseSaying = new WiseSaying(); //인스턴스 생성
        wiseSaying.id = lastNo;
        wiseSaying.saying = saying;
        wiseSaying.author = author;

        wiseSayings[lastIndex++] = wiseSaying;

        return wiseSaying; //actionWrite()에서 값 받기 위해 return
    }

    //목록
    public void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        WiseSaying[] wiseSayings = findListDesc();

        for(WiseSaying wiseSaying : wiseSayings) { //배열 훑기
            // 변수 사용 시 코드 길어짐 -> 포맷팅 효율적
            System.out.println("%d / %s / %s".formatted(wiseSaying.id, wiseSaying.saying, wiseSaying.author));
        }
    }

    //목록 (데이터 처리)
    public WiseSaying[] findListDesc() {
        //내림차순으로 저장할 배열
        WiseSaying[] resultList = new WiseSaying[lastIndex];
        int resultListIndex = 0;

        //내림차순으로 저장
        for(int i = lastIndex -1; i >= 0; i--) {
            resultList[resultListIndex] = wiseSayings[i];
            resultListIndex++;
        }
        return resultList;
    }
}
