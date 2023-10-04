package day04;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/day04/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;
    // 2. 저장
    @PostMapping("")
    public boolean doPost(  TodoDto todoDto ){
        System.out.println("TodoController.doPost");
        System.out.println("todoDto = " + todoDto);
        return todoService.doPost( todoDto );
    }
    // 3. 출력
    @GetMapping("")
    public List< TodoDto > doGet(){
        System.out.println("TodoController.doGet");
        return todoService.doGet();
    }
    // 4. 수정
    @PutMapping("")
    public boolean doPut(  TodoDto todoDto ){
        System.out.println("TodoController.doPut");
        System.out.println("todoDto = " + todoDto);
        return todoService.doPut( todoDto );
    }
    // 5. 삭제
    @DeleteMapping("")
    public boolean doDelete( @RequestParam int tno ){
        System.out.println("TodoController.doDelete");
        System.out.println("tno = " + tno);
        return todoService.doDelete( tno );
    }
    // 1.
    @GetMapping("/index")
    public Resource getTodoView(){
        return new ClassPathResource("templates/todo.html");
    }

}
/*

 */
