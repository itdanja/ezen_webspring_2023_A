import { Link } from 'react-router-dom';
import styles from '../css/header.css' // css파일 호출
import axios from 'axios';
import React, { useState , useEffect }  from 'react';

export default function Header( props ){
   let [ login , setLogin] = useState( null ); // 로그인 상태

    // 로그아웃
    const logOut = ()=>{
        sessionStorage.removeItem("login_token"); // JS 세션 스토리지 초기화
        //axios.get("/member/logout").then( r=>{ console.log(r); window.location.reload(); });  // 백엔드의 인증세션 지우기
        window.location.href="/member/logout";
    }
    // 로그인 상태 호출
    useEffect( () => {
        axios.get("/member/get")
            .then( r => { console.log(r.data );
                if( r.data != '' ){ // 로그인되어 있으면 // 서비스에서 null 이면 js에서 '' 이다 .
                    // JS 로컬 스토리지 에 저장
                    sessionStorage.setItem( "login_token" , JSON.stringify( r.data ) );
                    // 상태변수에 로컬 스토리지를 호출해서 상태변수에 데이터 저장 [ 렌더링 하기 위해 ]
                    setLogin( JSON.parse( sessionStorage.getItem("login_token") ) );
                }
            })
    },[] )

    return(<>
        <header>
            <h2> <Link to='/'> 이젠리액트 </Link> </h2>
            <ul>
                <li> <Link to='/example'>리액트예제</Link></li>
                <li> <Link to='/'>TODO </Link></li>
                <li> <Link to='/'>비회원게시판 </Link></li>
                <li> <Link to='/'>회원게시판 </Link></li>

                            {
                                login == null
                                ? ( <>
                                        <li> <Link to='/login'>로그인 </Link></li>
                                        <li> <Link to='/signup'>회원가입 </Link></li>
                                    </> )
                                : ( <>
                                         <li> <Link to='/info'> 내정보 </Link></li>
                                        <li> <span onClick={ logOut }>로그아웃 </span></li>
                                    </> )
                            }

            </ul>
        </header>
    </>)
}