
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
                console.log(r )
                if( r.data == false ){
                    alert("동일한 회원정보가 없습니다. ");
                }else{
                    alert("로그인성공");
                    window.location.href="/";
                }
            })
    }

    return(<>
        <div className="loginContainer">
            <h3> ReactEzen LOGIN </h3>
            <form className='loginForm'>
                아이디 <input
                    type="text"
                    placeholder='email address'
                    name='memail' />

                비밀번호 <input type="password"
                    placeholder='password'
                    name='mpassword' />

                <Link to=''>아이디찾기 </Link> <Link to=''> 비밀번호찾기 </Link>
                <button onClick={ onLogin } type="button">로그인</button>
                <a href="/oauth2/authorization/google"> 구글로그인 </a>
                <a href="/oauth2/authorization/kakao"> 카카오로그인 </a>
                <a href="/oauth2/authorization/naver"> 네이버로그인 </a>

            </form>
        </div>
    </>)
}

/*

    // 1. AXIOS 이용한 로그인 처리 형식
        <form>
            아이디 <input
                type="text"
                placeholder='email address'
                className='memail' />

            비밀번호 <input type="password"
                placeholder='password'
                className='mpassword' />

            <Link to=''>아이디찾기 </Link> <Link to=''> 비밀번호찾기 </Link>
            <button onClick={ onLogin } type="button">로그인</button>
        </form>


     <form action="전송할HTTP주소" method="HTTP메소드">
     // 2.
     <form action="/member/login" method="post">
         아이디 <input type="text" placeholder='email address' name='memail' />
         비밀번호 <input type="password"  placeholder='password' name='mpassword' />
         <Link to=''>아이디찾기 </Link> <Link to=''> 비밀번호찾기 </Link>
         <button type="submit">로그인</button>
     </form>
*/