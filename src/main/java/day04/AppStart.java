package day04;
/* 메타 어노테이션 ?? : 실행 또는 컴파일 했을때 사용방법(이미 설치된 라이브러리) 에 대해 정의 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run( AppStart.class );
    }
}