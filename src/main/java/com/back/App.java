package com.back;

import java.util.Scanner;

//핵심 로직 클래스
public class App {
    // ✅ run() 및 다른 함수 사용위해 인스턴스 변수로 변경 (지역 변수 X)
    Scanner sc = new Scanner(System.in);
    int lastNo = 0; //등록 번호
    int lastIndex = 0; //배열 인덱스
    WiseSaying[] wiseSayings = new WiseSaying[100]; //크기 100 배열

    //배열의 단점을 보완하기 위한 ArrayList 추가 예정


    public void run() {  //main 무조건 static ⭕️ 그 외 static ❌
        System.out.println("== 명언 앱 ==");

        while (true) { //가독성 및 유지보수성 위해 refactoring
            System.out.print("명령) ");
            String command = sc.nextLine();

            if (command.equals("등록")) {
                actionWrite();

            } else if (command.equals("목록")) {
                actionList();

            } else if (command.startsWith("삭제")) { //삭제로 시작하는
                actionDelete(command);

            } else if (command.startsWith("수정")){
                actionModify(command);

            } else if(command.equals("종료")) {
                break;
            }
        }
    }


    //등록 (사용자와 상호작용 담당)
    public void actionWrite() {
        System.out.print("명언 : ");
        String saying = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        WiseSaying wiseSaying = write(saying, author); //지역변수 접근위해 파라미터 전달

        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId())); //return 받은 값 사용
    }

    //등록 (데이터 처리 담당)
    public WiseSaying write(String saying, String author) {
        lastNo++;

        WiseSaying wiseSaying = new WiseSaying(lastNo, saying, author); //인스턴스 생성

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
            System.out.println("%d / %s / %s".formatted(wiseSaying.getId(), wiseSaying.getSaying(), wiseSaying.getAuthor()));
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


    //삭제
    public void actionDelete(String command) {

        String[] commandBits = command.split("=");

        if(commandBits.length < 2) {
            System.out.println("번호를 입력해주세요. ");
            return; //종료
        }

        String idStr = commandBits[1];
        int id = Integer.parseInt(idStr); //문자열 -> 숫자

        boolean result = delete(id);
        if(result) {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        }
        else {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }


    //삭제 (데이터 처리)
    public boolean delete(int id) {
        int deleteTargetIndex = findIndexById(id); // 삭제하고 싶은 명언이 저장된 위치


        if(deleteTargetIndex == -1) {
            return false; //종료
        }

        for(int i = deleteTargetIndex; i < lastIndex; i++) {
            wiseSayings[i] = wiseSayings[i + 1];
        }

        lastIndex--;
        return true;
    }

    //수정
    public void actionModify(String command) {
        String[] commandBits = command.split("="); // '=' 기준 분할

        if(commandBits.length < 2) {
            System.out.println("번호를 입력해주세요. ");
            return; //종료
        }

        String idStr = commandBits[1];
        int id = Integer.parseInt(idStr); //문자열 -> 숫자

        int modifyTargetIndex = findIndexById(id);

        if(modifyTargetIndex == -1) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        WiseSaying modifyTargetWiseSaying = wiseSayings[modifyTargetIndex];

        System.out.println("명언(기존) : %s".formatted(modifyTargetWiseSaying.getSaying()));
        System.out.print("명언 : ");
        String newSaying = sc.nextLine();
        System.out.println("작가(기존) : %s".formatted(modifyTargetWiseSaying.getAuthor()));
        System.out.print("작가 : ");
        String newAuthor = sc.nextLine();

        modify(modifyTargetWiseSaying, newSaying, newAuthor); //새 명언, 작가 전달
    }

    //수정 (데이터 처리)
    public void modify(WiseSaying modifyTargetWiseSaying, String newSaying, String newAuthor) {
        modifyTargetWiseSaying.setSaying(newSaying);
        modifyTargetWiseSaying.setAuthor(newAuthor);
    }


    //id 찾기 (삭제, 수정)
    public int findIndexById(int id) {
        for (int i = 0; i < lastIndex; i++) {
            if (wiseSayings[i].getId() == id) {
                return i; //명언 번호 반환
            }
        }
        return -1;
    }
}
