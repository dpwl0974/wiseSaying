package com.back.domain.wiseSaying.global;

import com.back.domain.wiseSaying.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;
import com.back.domain.wiseSaying.service.WiseSayingService;

import java.util.Scanner;

public class AppContext {
    public static Scanner sc = new Scanner(System.in);
    public static WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();
    public static WiseSayingService wiseSayingService = new WiseSayingService();
    public static WiseSayingController wiseSayingController = new WiseSayingController(sc);
    public static SystemController systemController = new SystemController();
}
