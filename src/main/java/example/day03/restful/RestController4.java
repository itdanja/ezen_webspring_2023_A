package example.day03.restful;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController //  @Controller 동일한 기능 + @ResponseBody 제공
public class RestController4 {
    // 1. GET
    @GetMapping("/day03/blue")
    public String getBlue(HttpServletRequest request ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        return "정상응답";   // 2.응답
    }
    // 2. POST
    @PostMapping("/day03/blue")
    public String postBlue(HttpServletRequest request  ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        return "정상응답";   // 2.응답
    }
    // 3. PUT
    @PutMapping("/day03/blue")
    public String putBlue(HttpServletRequest request ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        return "정상응답";   // 2.응답
    }
    // 4. DELETE`
    @DeleteMapping("/day03/blue")
    public String deleteBlue(HttpServletRequest request ) throws IOException {
        String param1 = request.getParameter("param1");
        System.out.println("param1 = " + param1);
        return "정상응답";   // 2.응답
    }
}
