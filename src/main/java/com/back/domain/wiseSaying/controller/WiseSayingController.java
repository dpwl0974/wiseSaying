package com.back.domain.wiseSaying.controller;

import com.back.domain.wiseSaying.Rq;
import com.back.domain.wiseSaying.WiseSaying;

import java.util.*;

public class WiseSayingController {
    private Scanner sc;

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
    }

    private int lastId = 0; //등록 번호

    //배열의 단점을 보완하기 위한 ArrayList 추가 / List 안에 ArrayList, LinkedList 등 포함
    private List<WiseSaying> wiseSayings = new ArrayList<>(); //자동 형변환
    Map<String, String> paramMap = new HashMap<>(); //검색 파라미터 맵


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
    private WiseSaying write(String saying, String author) {
        lastId++;

        WiseSaying wiseSaying = new WiseSaying(lastId, saying, author); //인스턴스 생성

        wiseSayings.add(wiseSaying);


        return wiseSaying; //actionWrite()에서 값 받기 위해 return
    }

    //목록
    public void actionList() {
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
    public void actionDelete(Rq rq) {
        /*String[] commandBits = command.split("=");

        if (commandBits.length < 2) {
            System.out.println("번호를 입력해주세요. ");
            return; //종료
        }


        String idStr = commandBits[1];*/

        //getParam 사용 -> rq클래스 활용
        //함수마다 defaultValue 따로 사용
        int id = rq.getParamAsInt("id", -1);

        boolean result = delete(id);
        if (result) {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        } else {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }


    //삭제 (데이터 처리)
    private boolean delete(int id) {
        // for문으로 break 찾아서 삭제하는 방법이 성능 ⬆️
        // removeIf는 가독성 ⬆️
        return wiseSayings.removeIf(w -> w.getId() == id); //if문 대체
    }

    //수정
    public void actionModify(Rq rq) {
        //getParam 사용하면 됨
        int id = rq.getParamAsInt("id", -1);
        WiseSaying wiseSaying = finByIdOrNull(id);



        if (wiseSayings == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }


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


    // findIndexById 보완 - id 찾아서 객체 반환 (Null이 넘어올 수 있음)
    private WiseSaying finByIdOrNull(int id) {
        return wiseSayings.stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
