package day03.mapping;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/day03/post")
public class PostMappingController {
    /*
        HttpServletRequest 요청 가능 하지만 예제 생략
        @PathVariable 요청 가능 하지만 예제 생략
        @ModelAttribute 요청 가능 하지만 예제 생략
     */
    // 1.
    @PostMapping("/method1")
    // public String method1( @RequestParam String param1 ){  // http://localhost:80/day03/post/method1   // form [ param1 = 유재석 ]
    public String method1( @RequestBody String param1 ){  // http://localhost:80/day03/post/method1   // JSON {"param1":"유재석" }
        System.out.println("PostMappingController.method1");    System.out.println("param1 = " + param1);
        return "정상응답";
    }
    // 2.
    @PostMapping("/method2")
    //public String method2( @RequestParam Map<String , String > map ){ // http://localhost:80/day03/post/method2  // form [ param1 = 유재석 , param2 = 50 ]
        // DTO권장하지만 DTO가 없을때 여러개 매개변수 매핑 할때.
    public String method2( @RequestBody Map<String , String > map ){ // http://localhost:80/day03/post/method2   // JSON {"param1":"유재석","param2":50}
        System.out.println("PostMappingController.method2");     System.out.println("map = " + map);
        return "정상응답";
    }
    // 3.
    @PostMapping("/method3")
    // public String method3( ParamDto paramDto ){ // http://localhost:80/day03/post/method3   // form [ param1 = 유재석 , param2 = 50 ]
    public String method3( @RequestBody ParamDto paramDto ){ // http://localhost:80/day03/post/method3    // JSON {"param1":"유재석","param2":50}
        System.out.println("PostMappingController.method3");     System.out.println("paramDto = " + paramDto);
        return "정상응답";
    }
}
