
import styles from '../../css/login.css'; // css 호출
import axios from 'axios';
import React, { useState , useEffect }  from 'react';

export default function Signup( props ){

    let [ login , setLogin] = useState( {} ); // 로그인 상태

    useEffect( () => {
        setLogin( JSON.parse( sessionStorage.getItem("login_token") ) );
    },[] )



    // 1. 회원가입 버튼을 클릭했을때.
    const onUpdate = (e)=>{ console.log(e);
        // 2. 입력받은 데이터 구성
        let info = {
            mno : login.mno,
            mpassword : document.querySelector('.mpassword').value ,
            mpassword2 : document.querySelector('.mpassword2').value ,
            mname : document.querySelector('.mname').value,
            mphone : document.querySelector('.mphone').value
        }; console.log( info );

        // - 유효성검사 -

        // 3. 통신
        axios
            .put( '/member/put' , info )
            .then( r => {
                if( r.data ){
                    alert('회원수정 성공');
                    window.location.href = '/login'; // get방식 요청
                }
                else{ alert('회원수정 실패'); }
            })

    }

    const onDelete = (e)=>{ console.log(e);
        if( window.confirm('정말 탈퇴하시겠습니까? ') ) {
            axios
                .delete( '/member/delete' , { params: { mno : login.mno }} )
                .then( r => {
                    if( r.data ){
                        alert('회원탈퇴 성공');
                        sessionStorage.removeItem("login_token"); // JS 세션 스토리지 초기화
                        window.location.href = '/member/logout'; // get방식 요청
                    }
                    else{ alert('회원탈퇴 실패'); }
                })
        }
    }

    const onMnameChange = (e)=>{ setLogin( (prevState) => { return { ...prevState, mname: e.target.value } } )  }
    const onMphoneChange = (e)=>{ setLogin( (prevState) => { return { ...prevState, mphone: e.target.value } } )  }
    return(<>
        <div className="loginContainer">
            <h3> ReactEzen Member Info </h3>
            <form>
                <div> { 'mrole' in login ? login.mrole : '' } </div>
                이메일 <input type="text"  className='memail' disabled value={ 'memail' in login ? login.memail : '' }/>
                새 비밀번호 <input type="password" placeholder='특수문자 조합 5~30글자'  className='mpassword' />
                새 비밀번호 확인 <input type="password" placeholder='특수문자 조합 5~30글자' className='mpassword2' />
                이름 <input
                    type="text"
                    placeholder='이름'
                    className='mname'
                    value={ 'mname' in login ? login.mname : '' }
                    onChange={  onMnameChange }
                    />
                전화번호 <input
                    type="text"
                    placeholder='연락처'
                    className='mphone'
                    value={ 'mphone' in login ? login.mphone : '' }
                    onChange={  onMphoneChange  }

                    />
                <button onClick={ onUpdate } type="button">회원수정</button>
                <button onClick={ onDelete } type="button">회원탈퇴</button>
            </form>
        </div>
    </>)
}