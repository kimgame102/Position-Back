package com.app.positionback.domain.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Setter @Getter @ToString
public class NoticeMonthRankDTO {
    private String month; // 월 (형식: MM)
    private int count;    // 해당 월의 공지 수
}
