import styles from '../../css/login.css'
import { Link } from 'react-router-dom'
import axios from 'axios'

export default function Login( props ){

    const onLogin = ( e ) => { console.log( "onLogin open" );
        let info = {
            memail : document.querySelector(".memail").value,
            mpassword : document.querySelector(".mpassword").value
        }
        axios.post("http://localhost:80/member/login" , info )
            .then( r => {
                if( r.data == true ){
                    alert("로그인성공"); window.location.href="/";
                }else{
                    alert("동일한 회원정보가 없습니다. ");
                }
            })
    }

    return(<>
        <div className="loginContainer">
            <h3> EZEN LOGIN </h3>
            <form>
                아이디 <input type="text" placeholder="email address" className='memail' /> <br />
                비밀번호 <input type="password" placeholder="password" className='mpassword' /> <br />
                <Link to=''>아이디찾기</Link> <Link to=''>비밀번호찾기</Link>
                <button onClick={ onLogin } type="button">로그인</button>
                <a href=''> 네이버로그인 </a>
                <a href=''> 카카오로그인 </a>
                <a href=''> 구글로그인 </a>
            </form>
        </div>
    </>)
}