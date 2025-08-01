package com.hnjee.jemilog.common.dto;

//SimpleMessageResponse → ApiResponse의 data 필드에 들어 가는 메세지
// "이건 중요한 응답 데이터야!" 라는 구조적 신호를 주는 것
//message → 그냥 "알려주는 용도"
//이 두 개를 구분하면 API의 의도가 더 명확해지고, 프론트 협업도 훨씬 쉬워진다.
public class SimpleMessageResponse {
    private String message;
}