package ezenweb.controller;

import ezenweb.model.dto.MemberDto;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController // 컨트롤러 + ResponseBody
@RequestMapping("/member") // 클래스 매핑
public class MemberController {
    // Controller -> Service 요청
    // Controller <- Service 응답
    @Autowired
    private MemberService memberService;

    // 1. [C] 회원가입
    @PostMapping("/post")   // http://localhost:80/member/get
    public boolean postMember(@RequestBody MemberDto memberDto){
        boolean result =  memberService.postMember(memberDto);
        return result;
    }
    /*
    // 2. [R] 회원정보 호출 [ 1명 ] : 세션을 구현 안했을때.
    @GetMapping("/get")         // http://localhost:80/member/get?mno=1
    public MemberDto getMember( @RequestParam int mno ){
        MemberDto memberDto = memberService.getMember(mno);
        return memberDto;
    }
    */

    // 3. [U] 회원정보 수정
    @PutMapping("/put")         // http://localhost:80/member/put
    public boolean updateMember( @RequestBody MemberDto memberDto ){
        boolean result =  memberService.updateMember(memberDto);
        return result;
    }
    // 4. [D] 회원탈퇴
    @DeleteMapping("/delete")   // http://localhost:80/member/delete?mno=1
    public boolean deleteMember( @RequestParam int mno ){
        boolean result =  memberService.deleteMember(mno);
        return result;
    }

    // 5. [post] 로그인     get/post   요청(아이디/비밀번호) / 응답( 성공/실패)
    @PostMapping("/login")
    public boolean login( @RequestBody MemberDto memberDto  ) {
        boolean result =  memberService.login(memberDto );
        return result;
    }

    // 6. [get] 로그아웃    get/post    요청/응답( 성공/실패)
    @GetMapping("/logout")
    public boolean logout() {
        boolean result = memberService.logout();
        return result;
    }
    // 2. [R] 회원정보 호출 [로그인된 회원호출 ] 세션을 구현 했을때 [  ]
    @GetMapping("/get")         // http://localhost:80/member/get?mno=1
    public MemberDto getMember( ){
        return  memberService.getMember();
    }

}










