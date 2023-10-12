package example.day04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 스프링 빈 : 스프링 컨테이너(저장소)에 저장된 객체 [ 이유 : 스프링이 대신 객체 관리 => 여러개발자들이 작업했을때 기준  ]

// @Controller // Spring MVC 중 해당 클래스를 Controller 로 사용 // 스프링 컨테이너[공간] 빈[bean] 등록
@RestController // Controller + ResponseBody
@RequestMapping("/todo") // HTTP로부터의 해당 클래스의 매핑 주소 만들기 // 공통URL
public class TodoController {

    @Autowired // 미리 등록된 스프링 컨테이너[공간/상자=힙영역]에서 빈(bean/객체) 찾아서 주입
    private TodoService todoService; // 서비스 객체

    //REST : HTTP기반으로  GET , POST , PUT , DELETE 메소드 이용한 웹 서비스
    // 1. [C]
    //@ResponseBody             // 응답객체 자동 지원 [ *단 해당 클래스가 @RestController 사용했을때 생략 가능 ]
    @PostMapping("")            // HTTP 요청중 POST 메소드 요청일때.  http://localhost:80/todo
    public boolean doPost( @RequestBody TodoDto todoDto ){   // 요청 매개변수 : 입력받은 정보들 [ Dto ]
                         // @RequestBody : HTTP BODY( post,put ) JSON형식으로 요청 매핑
        System.out.println("TodoController.doPost"); System.out.println("todoDto = " + todoDto);
        return todoService.doPost( todoDto );
    }
    // 2. [R]
    @GetMapping("")         // HTTP 요청중 GET 메소드 요청일때.    http://localhost:80/todo
    public List<TodoDto> doGet( ) {   // 요청 매개변수 : 출력에 필요한 정보들 [ X ]
        System.out.println("TodoController.doGet");
        return todoService.doGet();    // List<TodoDto> 객체 타입 반환 [ 테스트할때는 null ]
    }
    // 3. [U]
    @PutMapping("")      // HTTP 요청중 PUT 메소드 요청일때.       http://localhost:80/todo
    public boolean doPut( @RequestBody TodoDto todoDto ){    // 요청 매개변수 : 수정에 필요한 정보들 [ DTO ]
        System.out.println("TodoController.doPut");  System.out.println("todoDto = " + todoDto);
        return todoService.doPut( todoDto );
    }
    // 4. [D]
    @DeleteMapping("")   // HTTP 요청중 DELETE 메소드 요청일때.    http://localhost:80/todo
    public boolean doDelete( @RequestParam int tno ){ // 요청 매개변수 : 삭제에 필요한 정보들 [ int  ]
                        // @RequestParam : 쿼리스트링 에서의 매개변수 요청 매핑
        System.out.println("TodoController.doDelete");   System.out.println("tno = " + tno);
        return todoService.doDelete( tno );
    }

    // 5. HTML 반환 매핑 주소만들기
    @GetMapping("/index") // http://localhost/todo/index
    public Resource getIndex(){
        return new ClassPathResource("templates/todo.html");
    }
        // Resource : import org.springframework.core.io.Resource;
        // ClassPathResource : import org.springframework.core.io.ClassPathResource;
}












