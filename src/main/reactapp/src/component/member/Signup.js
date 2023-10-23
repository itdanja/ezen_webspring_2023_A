import styles from '../../css/login.css'
import { Link } from 'react-router-dom'
import axios from 'axios'
import { useState } from 'react'

export default function Signup( props ){

    const onSignup = (e) =>{ console.log( 'onSignup open' );
        let info = {
            memail : document.querySelector(".memail").value,
            mpassword : document.querySelector(".mpassword").value,
            mname : document.querySelector(".mname").value,
            mphone : document.querySelector(".mphone").value
        }
        axios.post("http://localhost:80/member/post" , info )
            .then( r => {  console.log( r );
                if( r.data == true  ){
                    alert('회원가입 성공'); window.location.href="/login";
                }else{
                    alert('회원가입 실패');
                }
            })
    }

    // 2. 아이디 중복체크
    let [ memailMsg , setMemailMsg ] = useState('');
    const idCheck = (e) => {  // 1. console.log( document.querySelector('.memail').value ) ;  // 2. console.log( e.target.value ) ;
        axios.get( "http://localhost:80/member/idcheck" , { params : { memail : e.target.value } } )
            .then( res => {
                if( res.data == true ){ setMemailMsg('사용중인 이메일 입니다.')}
                else{ setMemailMsg('사용 가능 한 이메일 입니다. ')}
                }
            )  .catch( e => console.log( e ) )
    }

    return(<>
        <div className="loginContainer">
            <h3> EZEN SIGNUP </h3>
            <form>
                이메일(아이디) <input type="text" placeholder="@포함 5~30글자" className='memail' onChange={ idCheck } /> <br />
                <div style={{ fontSize:'10px', marginBottom : '10px' }}> { memailMsg } </div>
                비밀번호 <input type="password" placeholder="대소문자 조합 5~30글자" className='mpassword'/> <br />
                비밀번호 확인 <input type="password" placeholder="대소문자 조합 5~30글자" className='mpassword2' /> <br />
                이름 <input type="text" placeholder="이름" className='mname'/> <br />
                전화번호 <input type="text" placeholder="연락처" className='mphone'/> <br />
                <button onClick={ onSignup } type="button">회원가입</button>
            </form>
        </div>
    </>)
}