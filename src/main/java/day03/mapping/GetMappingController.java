package day03.mapping;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController // @Controller + @ResponseBody
@RequestMapping("/day03/get") // 해당 클래스의 URL 매핑 정의 // [ 메소드들의 공통URL ]
public class GetMappingController {

    // 1. HttpServletRequest 객체 이용한 GET메소드 URL쿼리스트링 의 매개변수 요청
    @GetMapping("/method1") // http://localhost:80/day03/get/method1?param1=안녕겟
    public String method1( HttpServletRequest request ){
        System.out.println("GetMappingController.method1"); // soutm
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1); // soutv
        return "정상응답";
    }
    // 2. @RequestParam URL주소상의 쿼리스트링[ URL?매개변수=값&매개변수=값 ] 매개변수 매핑
    @GetMapping("/method2") // http://localhost:80/day03/get/method2?param1=안녕겟
    public String method2( @RequestParam String param1 ){
        // @RequestParam String param1  <====>  String param1 = request.getParameter("param1");
        System.out.println("GetMappingController.method2");
        System.out.println("param1 = " + param1); // soutv
        return "정상응답";
    }
    // 3. @RequestParam 두 개 이상 [ 여러개 가능 ]
    @GetMapping("/method3")  // http://localhost:80/day03/get/method4?param1=유재석&param2=50
    public String method3( @RequestParam String param1 , @RequestParam int param2 ){
        System.out.println("GetMappingController.method3"); // soutm
        System.out.println("param1 = " + param1 + ", param2 = " + param2); // soutp
        return "정상응답";
    }
    // 4. 여러개 매개변수를 DTO로 자동 변환 매핑
    @GetMapping("/method4") // http://localhost:80/day03/get/method4?param1=유재석&param2=50
    public String method4( ParamDto paramDto ) {
        System.out.println("GetMappingController.method4");
        System.out.println("paramDto = " + paramDto);
        return "정상응답";
    }
    // 5. 여러개 매개변수를 DTO로 자동 변환 매핑
    @GetMapping("/method5") // http://localhost:80/day03/get/method4?param1=유재석&param2=50
    public String method5( @ModelAttribute ParamDto paramDto ) {
        System.out.println("GetMappingController.method5");
        System.out.println("paramDto = " + paramDto);
        return "정상응답";
    }
    // 6. 쿼리스트링( URL?매개변수=값&매개변수=값 ) VS 경로매개변수( URL/값1/값2 )     //  3<-->6
        // <a href="" > </a>                        ServerSocket("URL/{매개변수}/{매개변수}")
    @GetMapping("/method6/{param1}/{param2}")  // PathVariable 방식 http://localhost:80/day03/get/method6/유재석/50
    public String method6( @PathVariable("param1") String param1 , @PathVariable("param2") int param2 ){
        System.out.println("GetMappingController.method6");
        System.out.println("param1 = " + param1 + ", param2 = " + param2);
        return "정상응답";
    }
    // 7. 경로매개변수 방식도 DTO 지원.    4<-->7
    @GetMapping("/method7/{param1}/{param2}") // PathVariable 방식    http://localhost:80/day03/get/method6/유재석/50
    public String method7( ParamDto paramDto ){
        System.out.println("GetMappingController.method7");
        System.out.println("paramDto = " + paramDto);
        return "정상응답";
    }
    // 8. 경로매개변수 방식도 DTO 지원.    5<-->8
    @GetMapping("/method8/{param1}/{param2}") // PathVariable 방식    http://localhost:80/day03/get/method6/유재석/50
    public String method8( @ModelAttribute ParamDto paramDto ){
        System.out.println("GetMappingController.method8");
        System.out.println("paramDto = " + paramDto);
        return "정상응답";
    }
}
// 함수의 반환타입이 Strin 로 작성한 이유 : @ResponseBody가 자동으로 반환타입이 String이면  resp.setContentType("text/html; charset=utf-8"); 제공
// return "정상응답";    로 작성한 이유 : @ResponseBody가 자동으로 resp.getWriter().println("정상응답"); 제공









