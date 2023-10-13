package example.day06;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

// Controller 사용처 웹:JS(AJAX) ,REACT(AXIOS) , 앱 , 소프트웨어
// 역할 : AJAX[외부인] <-----연결다리[자바] -----> 서비스[자바] ---- repository--- entity---> DB table
// 식당 예시
// 예시 : 손님( 주문요청 / 주문응답 ) <-----대화/행위--- 서빙  ----대화/행위-----> 요리사 ------대화/행위----> 냉장고
// 대화/행위 : 상호작용
//  예시 : 손님( 주문요청 / 주문응답 ) <-----대화/행위--- 서빙객체  ----대화/행위-----> 요리사객체 ------대화/행위----> 냉장고객체

@RestController
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    // 1.C
    @PostMapping("/do")
    public boolean bWrite(@RequestBody NoteDto noteDto ){
        boolean result =  noteService.bWrite( noteDto );
        return result;
    }
    // 2.R
    @GetMapping("/do")
    public List<NoteDto> bList(){
        List<NoteDto> result =  noteService.bList();
        return result;
    }
    // 3.U
    @PutMapping("/do")
    public boolean bUpdate( @RequestBody NoteDto noteDto){
        boolean result =  noteService.bUpdate( noteDto );
        return result;
    }
    // 4.D
    @DeleteMapping("/do")
    public boolean bDelete(@RequestParam int no ){
        boolean result = noteService.bDelete( no );
        return result;
    }
}
