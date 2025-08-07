package com.back;

import java.util.*;

//핵심 로직 클래스
public class App {
    // ✅ run() 및 다른 함수 사용위해 인스턴스 변수로 변경 (지역 변수 X)
    private Scanner sc = new Scanner(System.in);
    private int lastId = 0; //등록 번호
    //private int lastIndex = 0; //배열 인덱스 / ArrayList에는 필요없는 부분
    //private WiseSaying[] wiseSayings = new WiseSaying[100]; //크기 100 배열
    //배열의 단점을 보완하기 위한 ArrayList 추가 / List 안에 ArrayList, LinkedList 등 포함
    private List<WiseSaying> wiseSayings = new ArrayList<>(); //자동 형변환
    Map<String, String> paramMap = new HashMap<>(); //검색 파라미터 맵

    //외부에서 꼭 써야하는 메서드 => public 그 외 기본 private
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
                //actionDelete(command);
                setParams(command);
                actionDelete();

            } else if (command.startsWith("수정")) {
                //actionModify(command);
                setParams(command);
                actionModify();

            } else if (command.equals("종료")) {
                break;
            }
        }
    }


    //등록 (사용자와 상호작용 담당)
    private void actionWrite() {
        System.out.print("명언 : ");
        String saying = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        WiseSaying wiseSaying = write(saying, author); //지역변수 접근위해 파라미터 전달

        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId())); //return 받은 값 사용
    }

    //등록 (데이터 처리 담당)
    private WiseSaying write(String saying, String author) {
        lastId++;

        WiseSaying wiseSaying = new WiseSaying(lastId, saying, author); //인스턴스 생성

        wiseSayings.add(wiseSaying);


        return wiseSaying; //actionWrite()에서 값 받기 위해 return
    }

    //목록
    private void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        List<WiseSaying> wiseSayings = findListDesc();

        //stream 사용 가능하지만 통일성을 위해 굳이 안고쳐도 되면 하지 ❌
        for (WiseSaying wiseSaying : wiseSayings) { //배열 훑기
            // 변수 사용 시 코드 길어짐 -> 포맷팅 효율적
            System.out.println("%d / %s / %s".formatted(wiseSaying.getId(), wiseSaying.getSaying(), wiseSaying.getAuthor()));
        }
    }

    //목록 (데이터 처리)
    private List<WiseSaying> findListDesc() {

        /*//내림차순으로 저장할 배열
        WiseSaying[] resultList = new WiseSaying[lastIndex];
        int resultListIndex = 0;

        //내림차순으로 저장
        for(int i = lastIndex -1; i >= 0; i--) {
            resultList[resultListIndex] = wiseSayings[i];
            resultListIndex++;
        }*/
        return wiseSayings.reversed(); //역순
    }


    //삭제
    private void actionDelete() {
        /*String[] commandBits = command.split("=");

        if (commandBits.length < 2) {
            System.out.println("번호를 입력해주세요. ");
            return; //종료
        }


        String idStr = commandBits[1];*/

        //getParam 사용
        String idStr = getParam("id");
        int id = Integer.parseInt(idStr); //문자열 -> 숫자

        boolean result = delete(id);
        if (result) {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        } else {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }


    //삭제 (데이터 처리)
    private boolean delete(int id) {
        //int deleteTargetIndex = findIndexById(id); // 삭제하고 싶은 명언이 저장된 위치

        //ArrayList에는 "삭제" 존재하므로 필요 없는 코드
        /*for(int i = deleteTargetIndex; i < lastIndex; i++)
            wiseSayings[i] = wiseSayings[i + 1];
        }*/

        // for문으로 break 찾아서 삭제하는 방법이 성능 ⬆️
        // removeIf는 가독성 ⬆️
        return wiseSayings.removeIf(w -> w.getId() == id); //if문 대체
    }

    //수정
    private void actionModify() {
        /*String[] commandBits = command.split("="); // '=' 기준 분할

        if (commandBits.length < 2) {
            System.out.println("번호를 입력해주세요. ");
            return; //종료
        }

        String idStr = commandBits[1];
        int id = Integer.parseInt(idStr); //문자열 -> 숫자*/

        //String keyword = getParma("keyword")

        //int modifyTargetIndex = findIndexById(id); //좀 더 효율적으로 바꾸기

        //getParam 사용하면 됨
        String strId = getParam("id");
        int id = Integer.parseInt(strId);
        WiseSaying wiseSaying = finByIdOrNull(id);


        if (wiseSayings == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        //WiseSaying modifyTargetWiseSaying = wiseSayings.get(modifyTargetIndex);

        System.out.println("명언(기존) : %s".formatted(wiseSaying.getSaying()));
        System.out.print("명언 : ");
        String newSaying = sc.nextLine();
        System.out.println("작가(기존) : %s".formatted(wiseSaying.getAuthor()));
        System.out.print("작가 : ");
        String newAuthor = sc.nextLine();

        modify(wiseSaying, newSaying, newAuthor); //새 명언, 작가 전달
    }

    //수정 (데이터 처리)
    private void modify(WiseSaying wiseSaying, String newSaying, String newAuthor) {
        wiseSaying.setSaying(newSaying);
        wiseSaying.setAuthor(newAuthor);
    }


    /*//id 찾기 (삭제, 수정)
    private int findIndexById(int id) {
        return IntStream.range(0, wiseSayings.size())
                .filter(i -> wiseSayings.get(i).getId() == id) //if문
                .findFirst()
                .orElse(-1); //없으면 return -1 해주는 것 반영

        *//*for (int i = 0; i < wiseSayings.size(); i++) {
            if (wiseSayings.get(i).getId() == id) {
                return i;
            }
        }*//*
    }*/

    // findIndexById 보완 - id 찾아서 객체 반환 (Null이 넘어올 수 있음)
    private WiseSaying finByIdOrNull(int id) {
        return wiseSayings.stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //파라미터를 통해 id 찾기 - key 넘기면 key에 맞는 value 넘김
    private String getParam(String key) {
        return paramMap.get(key);
    }


    private void setParams(String command) {
        String[] commandBits = command.split("\\?");

        String actionName = commandBits[0];
        String queryString = "";

        if (commandBits.length > 1) {
            queryString = commandBits[1];
        }

        String[] queryStringBits = queryString.split("&");

        for (String param : queryStringBits) {
            String[] paramBits = param.split("=");
            String key = paramBits[0];
            String value = null;

            if (paramBits.length > 1) {
                value = paramBits[1];
            }

            if (value == null) {
                continue;
            }

            paramMap.put(key, value);
        }
    }
}

