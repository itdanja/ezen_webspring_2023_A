
import Signup from './member/Signup.js';
import Login from './member/Login.js';
import Write from './board/Write.js';

function Main(){
    return(<>

        <h1> 메인페이지 </h1>

        <Signup />

        <Login />

        <Write />

    </>)
}
export default Main