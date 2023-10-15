package ezenweb.controller;

import ezenweb.model.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody
@RequestMapping("/member")
public class MemberController {
    @Autowired private MemberService memberService;
    // 1. [C]회원가입
    @PostMapping("/info")   // URL 같아도 HTTP메소드 다르므로 식별가능
    public boolean write( @RequestBody MemberDto memberDto ){
        boolean result = memberService.write( memberDto);
        return result;
    }
    // 2. [R]회원정보 호출
    @GetMapping("/info")
    public MemberDto info( @RequestParam int mno ){
        MemberDto result = memberService.info( mno );
        return result;
    }
    // 3. [U]회원정보 수정
    @PutMapping("/info")
    public boolean update( @RequestBody MemberDto memberDto ){
        boolean result =  memberService.update( memberDto );
        return result;
    }
    // 4. [D]회원정보 탈퇴
    @DeleteMapping("/info")
    public boolean delete( @RequestParam int mno ){
        boolean result = memberService.delete( mno );
        return result;
    }
    @PostMapping("/login")
    public boolean login( @RequestBody MemberDto memberDto ){
        boolean result = memberService.login( memberDto );
        return result;
    }
    // 2. 회원정보[세션 ] 로그아웃
    @GetMapping("/logout")public boolean logout(){
        return memberService.logout();
    }

}

/*

{
  "memail" : "qweqwe@qweqwe.com",
  "mpassword" : "1234",
  "mname" : "유재석",
  "mphone" : "010-1234-1234"
}

{
  "mno" : 2 ,
  "mpassword" : "4567",
  "mname" : "강호동",
  "mphone" : "010-4567-4567"
}

 */