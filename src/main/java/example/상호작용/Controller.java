package example.상호작용;

import org.springframework.beans.factory.annotation.Autowired;

public class Controller {
    // 필드

    // 생성자

    // 메소드
    public void test() {
        // ---------- 객체가 필요하다 --------- //
        // 1. 다른 클래스의 메소드 호출
        Service service = new Service();
         // 스택영역      =   힙영역
        // 스택영역(변수) : 힙영역의 객체주소값
        // 힙영역 : 객체의 메모리가 있는 곳
        service.메소드1();

        // 2. 지역변수가 없다보니.. 주소를 몰라서 1회성
        new Service().메소드1();

        // 3. 싱글(프로그램내 혼자 있는)톤(객체)
            // : 결국엔 객체(미리만들어진) 이용
        Service2.getInstance().메소드2();

        // ---------- 객체가 필요 없는 경우 --------- //
        // 4. static멤버는 JVM메소드영역 저장되므로
        Service.메소드3();

        // 5.
        // ---------- 클래스가 스프링 컨테이너 빈 등록이 되었을때 --------- //
       service3.메소드3();
    }

    @Autowired
    Service3 service3;

}
/*
    1.
 */
