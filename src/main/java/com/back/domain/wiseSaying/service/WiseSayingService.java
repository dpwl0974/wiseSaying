package com.back.domain.wiseSaying.service;

import com.back.domain.wiseSaying.WiseSaying;
import com.back.domain.wiseSaying.repository.WiseSayingRepository;

import java.util.List;

public class WiseSayingService {
    private WiseSayingRepository wiseSayingRepository = new WiseSayingRepository();

    //등록 (데이터 처리)
    public WiseSaying write(String saying, String author) {
        WiseSaying wiseSaying = new WiseSaying(0, saying, author);
        wiseSaying = wiseSayingRepository.save(wiseSaying);

        return wiseSaying;
    }

    //삭제 (데이터 처리)
    public boolean delete(int id) {
        return wiseSayingRepository.delete(id);
    }

    //수정 (데이터 처리)
    public void modify(WiseSaying wiseSaying, String newSaying, String newAuthor) {
        wiseSaying.setSaying(newSaying);
        wiseSaying.setAuthor(newAuthor);
        wiseSayingRepository.save(wiseSaying);
    }
    }

    //내림차순
    public List<WiseSaying> findListDesc() {
        return wiseSayingRepository.findListDesc();
    }

    //id 찾기
    public WiseSaying getByIdOrNull(int id) {
        return wiseSayingRepository.findByIdOrNull(id);
    }

}
