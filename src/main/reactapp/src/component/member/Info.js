import styles from '../../css/login.css'; // css 호출
import axios from 'axios';
import { useState , useEffect } from 'react';

export default function Info( props ){ // 로그인 상태별로 , 회원권한(mrol)별로 페이지 접근 제한

    // axios로부터 전달받은 로그인된 회원정보를 상태변수에 저장
    const [ member , setMember ] = useState( null ); // { memail : 'qweqwe' , mname : 'qweqwe' }

    // 로그인 정보를 호출해서 출력하기. [ 최초 1번 실행 ]
    useEffect( ()=>{
        axios.get('/member/get').then( r =>{  setMember( r.data ); } );
    } , [ ] )

    // 1.이름 입력 했을때. 상태 변경.
    const mnameInputChange = (e)=>{
        console.log( e.target.value ); // onChange 이벤트를  실행한 주체자[e.target] 의 값 호출 [.value]
        let mnameInput = e.target.value ;
        setMember( { ...member  , mname : mnameInput } );
        //  요약 : setMember( { ...member  , mname : e.target.value } );
    }
    // 2. 전화번호 변경 [ 이벤트 속성  직접 처리 ]

    return(<>
        <div className="loginContainer">
            <h3> ReactEzen Info </h3>
            <form>

                회원등급 <div> { member != null ? member.mrol : '' } </div>
                이메일 <input value={ member!=null ? member.memail : ''  } disabled type="text" placeholder='@포함 7~30글자' className='memail' />
                새 비밀번호 <input type="password" placeholder='특수문자 조합 5~30글자'  className='mpassword' />
                새 비밀번호 확인 <input type="password" placeholder='특수문자 조합 5~30글자' className='mpassword2' />

                이름 <input value={ member!=null ? member.mname : ''  }
                    type="text"  placeholder='이름'  className='mname'
                    onChange={ mnameInputChange  }
                    />

                전화번호 <input
                        value={ member!=null ? member.mphone : ''  }
                        type="text" placeholder='연락처' className='mphone'
                        onChange={
                            (e)=>{  setMember( { ...member  , mphone : e.target.value } );  }
                         }
                        />

                <button type="button"> 정보 수정 </button>
                <button type="button"> 회원 탈퇴 </button>
            </form>
        </div>
    </>)
}

 // function mnameInputChange(e){ }

        // setMember( mnameInput ); // ????????? 문제점
        // member 상태변수에 전체 수정이 아닌 member 상태변수내 특정 속성만 변경 해야함..

        // let changeMember = member;      // 기존 객체를 새로운 객체?????? 에 대입
        // changeMember.mname = mnameInput; // 객체의 특정 속성만 새로운 값 대입
        // setMember( changeMember );      // 수정된 새로운 객체를 상태변수에 대입
            // 문제점 : setState()는 상태변수의 주소값이 변경될때 반응/랜더링

        //let changeMember = { ...member };      // 기존 객체를 새로운 객체?????? 에 대입
           // !! : 1.객체 복사 방법 { ...객체명 } , 2.배열 복사 방법 [ ...배열명 ]
           //  ... Spread Operator  : 얕은 복제
           //   { ...객체명 }
           //   { ...객체명 , 속성명 : 값 }   // 복사 할때 해당 속성명이 있으면 수정 / 없으면 대입
        //changeMember.mname = mnameInput; // 객체의 특정 속성만 새로운 값 대입
        //setMember( changeMember );      // 수정된 새로운 객체를 상태변수에 대입
