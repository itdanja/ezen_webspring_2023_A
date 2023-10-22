import styles from '../css/header.css';
import { Link } from "react-router-dom"

export default function Header( props ) {
    return ( <>
            <header>
                <h2> <Link to="/">이젠리액트</Link> </h2>
                <ul>
                    <li><Link to="/example">리액트공부방</Link></li>
                    <li><Link to="/">나만의할일목록</Link></li>
                    <li><Link to="/">비회원게시판</Link></li>
                    <li><Link to="/">회원제게시판</Link></li>
                    <li><Link to="/login">로그인</Link></li>
                    <li><Link to="/signup">회원가입</Link></li>
                    <li><Link to="/">로그아웃</Link></li>
                </ul>
            </header>
    </> )
}