package day03.restful;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller // 해당 클래스를 스프링MVC중 컨트롤러 객체로 사용 // 스프링 컨트롤러 객체를 빈에 등록
public class RestController1 {
    // 1. GET
    @RequestMapping( value = "/day03/black"  , method = RequestMethod.GET )     // 테스트요청 : http://localhost:80/day03/black?param1=안녕겟
    public void getBlack(HttpServletRequest request , HttpServletResponse response ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println("정상응답");
    }
    // 2. POST
    @RequestMapping( value = "/day03/black"  , method = RequestMethod.POST )     // 테스트요청 : http://localhost:80/day03/black
    public void postBlack(HttpServletRequest request , HttpServletResponse response ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println("정상응답");
    }
    // 3. PUT
    @RequestMapping( value = "/day03/black"  , method = RequestMethod.PUT )     // 테스트요청 : http://localhost:80/day03/black
    public void putBlack(HttpServletRequest request , HttpServletResponse response ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println("정상응답");
    }
    // 4. DELETE
    @RequestMapping( value = "/day03/black"  , method = RequestMethod.DELETE )  // http://localhost:80/day03/black?param1=안녕딜리트
    public void deleteBlack(HttpServletRequest request , HttpServletResponse response ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println("정상응답");
    }
}
