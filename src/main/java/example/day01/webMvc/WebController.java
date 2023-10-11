package example.day01.webMvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController // 해당 클래스를 restful 로 사용하겠다는 선언
public class WebController {    // Talend API Tester 이용한 테스트
    @GetMapping("/day01/doget") // HTTP로 부터 GET 요청을 했을때.
    public List<WebDto> doGet(){
        WebDao consoleDao = new WebDao();
        List<WebDto> result = consoleDao.doGet();
        return  result;
    }
    @PostMapping("/day01/dopost") // HTTP로 부터 POST 요청을 했을때.
    public boolean doPost( String title ){
        WebDto consoleDto = new WebDto( 0 , title , LocalDate.now() , true );
        WebDao consoleDao = new WebDao();
        boolean result =  consoleDao.doPost( consoleDto );
        return result;
    }
    /*
    @GetMapping("/test") // HTTP로 부터 GET 요청을 했을때.
    public String doGettest(){
        return "안녕 여기는 스프링 컨트롤이야.";
    }
    */
}