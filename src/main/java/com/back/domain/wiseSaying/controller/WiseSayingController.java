package com.back.domain.wiseSaying.controller;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.global.AppContext;
import com.back.domain.wiseSaying.global.Rq;
import com.back.domain.wiseSaying.global.Ut;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private Scanner sc;
    WiseSayingService wiseSayingService =  AppContext.wiseSayingService;

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
    }

    //등록 (사용자와 상호작용 담당)
    public void actionWrite() {
        System.out.print("명언 : ");
        String saying = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        WiseSaying wiseSaying = wiseSayingService.write(saying, author); //지역변수 접근위해 파라미터 전달

        System.out.println("%d번 명언이 등록되었습니다.".formatted(wiseSaying.getId())); //return 받은 값 사용
    }

    //목록
    public void actionList() {
        System.out.println("번호 / 작가 / 명언 / 작성날짜 / 수정날짜");
        System.out.println("----------------------");

        List<WiseSaying> wiseSayings = wiseSayingService.findListDesc();

        //stream 사용 가능하지만 통일성을 위해 굳이 안고쳐도 되면 하지 ❌
        for (WiseSaying wiseSaying : wiseSayings) { //배열 훑기
            // 변수 사용 시 코드 길어짐 -> 포맷팅 효율적
            System.out.println("%d / %s / %s / %s / %s".formatted(wiseSaying.getId(),
                    wiseSaying.getSaying(), wiseSaying.getAuthor(),
                    Ut.getFormattedDate(wiseSaying.getCreatedDate()),
                    Ut.getFormattedDate(wiseSaying.getModifiedDate())));


        }
    }

    //삭제
    public void actionDelete(Rq rq) {
        //getParam 사용 -> rq클래스 활용
        //함수마다 defaultValue 따로 사용
        int id = rq.getParamAsInt("id", -1);

        boolean result = wiseSayingService.delete(id);
        if (result) {
            System.out.println("%d번 명언이 삭제되었습니다.".formatted(id));
        } else {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
        }
    }

    //수정
    public void actionModify(Rq rq) {
        //getParam 사용하면 됨
        int id = rq.getParamAsInt("id", -1);
        WiseSaying wiseSaying = wiseSayingService.getByIdOrNull(id);

        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }


        System.out.println("명언(기존) : %s".formatted(wiseSaying.getSaying()));
        System.out.print("명언 : ");
        String newSaying = sc.nextLine();
        System.out.println("작가(기존) : %s".formatted(wiseSaying.getAuthor()));
        System.out.print("작가 : ");
        String newAuthor = sc.nextLine();

        wiseSayingService.modify(wiseSaying, newSaying, newAuthor); //새 명언, 작가 전달
    }
}
