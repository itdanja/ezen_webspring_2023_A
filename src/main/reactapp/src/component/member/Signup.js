
import styles from '../../css/login.css'; // css 호출
import axios from 'axios';
import { useState } from 'react';

export default function Signup( props ){

    // 1. 회원가입 버튼을 클릭했을때.
    const onSignup = (e)=>{ console.log(e);
        // 2. 입력받은 데이터 구성
        let info = {
            memail : document.querySelector('.memail').value ,
            mpassword : document.querySelector('.mpassword').value ,
            mname : document.querySelector('.mname').value ,
            mphone : document.querySelector('.mphone').value
        }; console.log( info );
        // - 유효성검사 -
        // 3. 통신
        axios
            .post( '/member/post' , info )
            .then( r => {
                if( r.data ){
                    alert('회원가입성공');
                    window.location.href = '/login'; // get방식 요청
                }
                else{ alert('회원가입실패'); }
            })
    }
    // 2. 이메일 중복 검사 [ 이메일 입력할때마다. ]
    let [ memail , setMemail ] = useState('')  // import { useState } from 'react';
    let [ memailCheck , setMemailCheck ] = useState('')
    const emailInputChange = ( e ) => {
        // 1. [기존방법]
            //let memail = document.querySelector('.memail').value; console.log( memail );
        // 2. [useState]
        let memailInput = e.target.value;   // e.target.value; : 입력받은 값
        setMemail( memailInput ); // 랜더링 // 바로실행x 해당 함수가 모두 끝나고
        // ------------------------------ //
        axios.get('/member/findMemail' , { params : { 'memail' : memailInput } } ) // 쿼리스트링 형식
            .then( r => {
                if( r.data ){ setMemailCheck('사용중인 아이디입니다.') } // 중복 입니다.
                else{  setMemailCheck('사용가능한 아이디입니다.') } // 중복 아님..
            } )
    }
    return(<>
        <div className="loginContainer">
            <h3> ReactEzen Signup </h3>
            <form>
                이메일 <input type="text" placeholder='@포함 7~30글자' className='memail'
                        value = { memail } onChange={ emailInputChange }  />
                <div>  {memailCheck } </div>

                비밀번호 <input type="password" placeholder='특수문자 조합 5~30글자'  className='mpassword' />
                비밀번호 확인 <input type="password" placeholder='특수문자 조합 5~30글자' className='mpassword2' />
                이름 <input type="text" placeholder='이름' className='mname' />
                전화번호 <input type="text" placeholder='연락처' className='mphone' />
                <button onClick={ onSignup } type="button">회원가입</button>
            </form>
        </div>
    </>)
}
        // axios <---> ajax 비동기 통신 함수
        // axios.HTTP메소드명( 'URL' , { params : { 속성명 : 값 , 속성명 : 값 } } ) : 쿼리스트링 형식 @RequestParam
        // axios.HTTP메소드명( 'URL' , JSON객체 ) : body 형식 @RequestBody