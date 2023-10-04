package day03.restful;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller // 해당 클래스를 스프링MVC중 컨트롤러 객체로 사용 // 스프링 컨트롤러 객체를 빈에 등록
public class RestController2 {
    // 1. GET
    @RequestMapping( value = "/day03/orange"  , method = RequestMethod.GET )     // 테스트요청 : http://localhost:80/day03/orange?param1=안녕겟
    @ResponseBody
    public String getOrange(HttpServletRequest request ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        return "정상응답";   // 2.응답
    }
    // 2. POST
    @RequestMapping( value = "/day03/orange"  , method = RequestMethod.POST )     // 테스트요청 : http://localhost:80/day03/orange
    @ResponseBody
    public String postOrange(HttpServletRequest request  ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        return "정상응답";   // 2.응답
    }
    // 3. PUT
    @RequestMapping( value = "/day03/orange"  , method = RequestMethod.PUT )     // 테스트요청 : http://localhost:80/day03/orange
    @ResponseBody
    public String putOrange(HttpServletRequest request ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        return "정상응답";   // 2.응답
    }
    // 4. DELETE
    @RequestMapping( value = "/day03/orange"  , method = RequestMethod.DELETE )  // http://localhost:80/day03/orange?param1=안녕딜리트
    @ResponseBody
    public String deleteOrange(HttpServletRequest request ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        return "정상응답";   // 2.응답
    }
}
