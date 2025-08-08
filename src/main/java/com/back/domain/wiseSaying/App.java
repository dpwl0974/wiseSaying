package com.back.domain.wiseSaying;

import com.back.domain.wiseSaying.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;

import java.util.*;

//핵심 로직 클래스
public class App {
    // ✅ run() 및 다른 함수 사용위해 인스턴스 변수로 변경 (지역 변수 X)
    private Scanner sc = new Scanner(System.in);

    //세스템 관련
    private SystemController systemController = new SystemController();
    //상호작용 관련
    private WiseSayingController wiseSayingController = new WiseSayingController(sc);


    //외부에서 꼭 써야하는 메서드 => public 그 외 기본 private
    public void run() {  //main 무조건 static ⭕️ 그 외 static ❌
        System.out.println("== 명언 앱 ==");

        while (true) { //가독성 및 유지보수성 위해 refactoring
            System.out.print("명령) ");
            String command = sc.nextLine();

            Rq rq = new Rq(command);
            String actionName = rq.getActionName();

            if (actionName.equals("등록")) {
                wiseSayingController.actionWrite();

            } else if (actionName.equals("목록")) {
                wiseSayingController.actionList();

            } else if (actionName.startsWith("삭제")) { //삭제로 시작하는
                wiseSayingController.actionDelete(rq);

            } else if (actionName.startsWith("수정")) {
                wiseSayingController.actionModify(rq);

            } else if (actionName.equals("종료")) {
                systemController.exit();
                // 종료 시 설정 저장
                // 종료 시 데이터 백업
                // 종료 시 자원 해제..

                break;
            }
        }
    }


   /* private void setParams(String command) {
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
    }*/
}

