import { Link } from 'react-router-dom';
import styles from '../css/header.css' // css파일 호출
import axios from 'axios';
import { useState , useEffect , useRef } from 'react'
    // useRef : 해당 컴포넌트/함수 가 재랜더링/재호출 할때 상태유지
export default function Header( props ){

    // 1. 지역변수
    let 변수 = 10
    console.log( 변수  );
    // 2.
    let ref변수 = useRef( 10 );
    console.log( ref변수 );
    console.log( ref변수.current );

    // 2. 웹소켓
    // ================= 소켓 s =================== //
    // 1. 클라이언트소켓 만들기
    let 클라이언트소켓 = new WebSocket("ws://localhost:80/chat");
    console.log( 클라이언트소켓 )
    // 1. 서버소켓과 연동 성공했을때. 이후 행동/메소드 정의
    클라이언트소켓.onopen = (e)=>{ console.log(e); }
    // 2. 서버소켓과 세션 오류가 발생했을때 이후 행동/메소드 정의
    클라이언트소켓.onerror = (e)=>{ console.log(e); }
    // 3. 서버소켓과 연동이 끊겼을때. 이후 행동/메소드 정의
    클라이언트소켓.onclose = (e)=>{ console.log(e); }
    // 4. 서버소켓으로부터 메시지를 받았을때. 이후 행동/메소드 정의
    클라이언트소켓.onmessage = (e)=>{ console.log(e); }
        // 2. 클라이언트소켓 메시지 전송
    const msgSend = (e)=>{ 클라이언트소켓.send("안녕"); }
    // ================= 소켓 e =================== //
    // 1. 로그인 상태를 저장할 상태변수 선언
    let [ login , setLogin ] = useState( null );

    // 로그아웃
    //function 함수명(e) {}
    const logOut = (e) => {
        axios.get('/member/logout')
            .then( r => {   console.log('logOut get');
                if( r.data ){ //로그아웃을 성공했으면
                    //window.location.reload(); // 새로고침
                    // 세션 제거
                    sessionStorage.removeItem('login_token')
                    setLogin( null );
                }
            });
    }
    // - 회원정보 호출 [ 로그인 여부 확인 ]
    // --------------------------- 컴포넌트 생성될때 1번 --------------------------------//
    useEffect( ()=>{
        axios.get('/member/get').then( r => { console.log('login get');
            if( r.data != '' ){
                // 브라우저 세션/쿠키 // 브라우저 F12 -> 애플리케이션탭 -> Local storage / Session storage
                    // localstorage : 모든 브라우저 탭/창 공유 [페이지 전환 해도 유지 ], 브라우저가 꺼져도 유지 , 자동로그인 기능 , 로그인상태유지
                    //  vs
                    // sessionstorage : 페이지 전환 해도 유지 ,  탭/창 종료되면 사라짐 , 로그인 여부
                    // 세션/ 쿠키 저장 :  .setItem( key , value )
                    // 세션/ 쿠키 호출 :  .getItem( key )
                    // 세션/ 쿠키 제거 :  .removeItem( key )
                sessionStorage.setItem( 'login_token' , JSON.stringify(r.data) );
                setLogin( JSON.parse( sessionStorage.getItem('login_token') ) );
            }  // 2. 만약에 로그인이 되어 있으면
        } )
    } , [ ] )
    // -------------------------------------------------------------------------------//
    return(<>
        <header>
            <button type="button" onClick={ msgSend } > 전송 </button>
            <h2> <Link to='/'> 이젠리액트 </Link> </h2>
            <ul>
                <li> <Link to='/example'>리액트예제</Link></li>
                <li> <Link to='/'>TODO </Link></li>
                <li> <Link to='/'>비회원게시판 </Link></li>
                <li> <Link to='/board/list'>회원게시판 </Link></li>
                <li> <Link to='/admin/product'> 제품 관리 </Link></li>

                {/* 삼항연산자     조건 ? 참 : 거짓 */}
                {
                    login == null
                    ?(<>
                        <li> <Link to='/login'>로그인 </Link></li>
                        <li> <Link to='/signup'>회원가입 </Link></li>
                      </>)
                    :(<>
                        <li> { login.memail }님 </li>
                        <li> <a href='/info'> 내정보 </a></li>
                        <li> <div onClick={ logOut }> 로그아웃 </div></li>
                      </>)
                }
            </ul>
        </header>
    </>)
}