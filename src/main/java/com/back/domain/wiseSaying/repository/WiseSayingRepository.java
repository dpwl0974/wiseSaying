package com.back.domain.wiseSaying.repository;

import com.back.domain.wiseSaying.WiseSaying;

import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private List<WiseSaying> wiseSayings = new ArrayList<>();

    // findIndexById 보완 - id 찾아서 객체 반환 (Null이 넘어올 수 있음)
    public WiseSaying findByIdOrNull(int id) {
        return wiseSayings.stream()
                .filter(w -> w.getId() == id)
                .findFirst()
                .orElse(null);
    }

    //저장
    public void save(WiseSaying wiseSaying) {
        wiseSayings.add(wiseSaying);
    }


    //삭제
    public boolean delete(int id) {
        return wiseSayings.removeIf(w -> w.getId() == id);
    }

    //내림차순
    public List<WiseSaying> findListDesc() {
        return wiseSayings.reversed();
    }
}
