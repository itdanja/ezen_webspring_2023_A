import axios from 'axios'

export default function Signup( props ) {

    // 2. 일반회원 로그인
    const onLogin = ( ) => { console.log( "onLogin open" )
              let info = {
                    memail : document.querySelector(".memail").value,
                    mpassword : document.querySelector(".mpassword").value
                }
        axios
            .post("http://localhost:80/member/login" , info )
            .then( r => {
                if( r.data == false ){
                    alert("동일한 회원정보가 없습니다. ");
                }else{
                    alert("로그인성공");
                    window.location.href="/";
                }
            })
    }

    return ( <>
            <h3> 로그인 페이지 </h3>
            <form>
                아이디[이메일] : <input type="text" className="memail" /> <br/>
                비밀번호 : <input type="text" className="mpassword" />  <br/>
                <button onClick={ onLogin } type="button"> 로그인 </button>
            </form>
    </> )
}