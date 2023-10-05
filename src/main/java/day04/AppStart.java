package day04;
/* 메타 어노테이션 ?? : 실행 또는 컴파일 했을때 사용방법(이미 설치된 라이브러리) 에 대해 정의 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 모든 컴포넌트들을 찾아서 빈 등록
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run( AppStart.class );
    }
}

/*
    - 정적 파일 생성 위치
    스프링이 정적(view) 파일들을 찾는 위치 resources폴더
        - 주의할점 : 본인이 만들고 싶은곳에 정적(view) 파일 만들면 안된다.
    HTML : resources->templates-> html파일
    JS/CSS/Image : resources->static-> JS/CSS/Image파일

    - JSP프로젝트 와 SPRING 프로젝트의 정적파일 경로 차이
        - JSP는 패키지의 경로와 파일명이 곧 URL
            http://localhost/Ezen_teamB/jsp/index.jsp
        - SPRING 는 정적파일 호출하는 URL 매핑
            매핑후 Resource 타입 반환

 */