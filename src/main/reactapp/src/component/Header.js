import { Link } from 'react-router-dom';
import styles from '../css/header.css' // css파일 호출
import axios from 'axios';
import { useState } from 'react'
export default function Header( props ){

    // 1. 로그인 상태를 저장할 상태변수 선언
    let [ login , setLogin ] = useState( null );

    // 로그아웃
    //function 함수명(e) {}
    const logOut = (e) => {
        axios.get('/member/logout')
            .then( r => {
                if( r.data ){ //로그아웃을 성공했으면
                    //window.location.reload(); // 새로고침
                    setLogin( null );
                }
            });
    }
    // - 회원정보 호출 [ 로그인 여부 확인 ]
    axios.get('/member/get').then( r => {
        if( r.data != '' ){  // 2. 만약에 로그인이 되어 있으면
            setLogin( r.data );
        }
    } )

    return(<>
        <header>
            <h2> <Link to='/'> 이젠리액트 </Link> </h2>
            <ul>
                <li> <Link to='/example'>리액트예제</Link></li>
                <li> <Link to='/'>TODO </Link></li>
                <li> <Link to='/'>비회원게시판 </Link></li>
                <li> <Link to='/'>회원게시판 </Link></li>

                {/* 삼항연산자     조건 ? 참 : 거짓 */}
                {
                    login == null
                    ?(<>
                        <li> <Link to='/login'>로그인 </Link></li>
                        <li> <Link to='/signup'>회원가입 </Link></li>
                      </>)
                    :(<>
                        <li> { login.memail }님 </li>
                        <li> <Link to='/'> 내정보 </Link></li>
                        <li> <div onClick={ logOut }> 로그아웃 </div></li>
                      </>)
                }
            </ul>
        </header>
    </>)
}