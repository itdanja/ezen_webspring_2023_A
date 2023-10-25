
import { Link } from 'react-router-dom'; // Link 컴포넌트 호출
import styles from '../../css/login.css'; // css 호출
import axios from 'axios';

export default function Login( props ){

    // 1. 로그인 버튼을 클릭했을때.
    function onLogin(e){ console.log(e);

         let loginForm = document.querySelectorAll('.loginForm')[0];
        let loginFormData = new FormData(loginForm);
        axios
            .post("/member/login" , loginFormData )
            .then( r => {
                if( r.data == false ){
                    alert("동일한 회원정보가 없습니다. ");
                }else{
                    alert("로그인성공");
                    window.location.href="/";
                }
            })

            // CORS policy 오류 발생 해결방안
                // - 스프링 controller 클래스 @CrossOrigin("http://localhost:3000")
    }

    return(<>
        <div className="loginContainer">
            <h3> ReactEzen LOGIN </h3>
            <form className="loginForm" method="post" action="/member/login">
                아이디 <input
                    type="text"
                    placeholder='email address'
                    name='memail' />

                비밀번호 <input type="password"
                    placeholder='password'
                    name='mpassword' />

                { /*Link컴포넌트 사용할려면 import */ }
                <Link to=''>아이디찾기 </Link> <Link to=''> 비밀번호찾기 </Link>
                <button  type="submit">로그인</button>
            </form>
        </div>
    </>)
}