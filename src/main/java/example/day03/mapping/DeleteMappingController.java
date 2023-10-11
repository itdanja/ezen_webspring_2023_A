package example.day03.mapping;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 해당 클래스를 컨트롤러임을 선언 // + ResponseBody
@RequestMapping("/day03/delete")
public class DeleteMappingController {
    // 1.
    @DeleteMapping("/method1")
    public boolean method1( @RequestParam String param1 ){
        System.out.println("param1 = " + param1);
        return true;    // 	application/json // 자동으로 JSON타입으로 전송
    }
    // 2.
    @DeleteMapping("/method2")
    //public boolean method2( @RequestParam Map<String,String> params ){
    public boolean method2( ParamDto params ){
        System.out.println("map = " + params );
        return false;   // 	application/json // 자동으로 JSON타입으로 전송
    }
}
