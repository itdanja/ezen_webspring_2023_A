import axios from 'axios'

export default function Signup( props ) {

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

    return(<>
        <h3> 회원가입 페이지 </h3>
        <form>
            아이디[이메일] : <input type="text" className="memail" /> <br/>
            비밀번호 : <input type="password" className="mpassword" />  <br/>
            전화번호 : <input type="text" className="mname" />  <br/>
            연락처 :  <input type="text" className="mphone" />  <br/>
            <button onClick={ onSignup } type="button"> 회원가입 </button>
        </form>
    </>)
}