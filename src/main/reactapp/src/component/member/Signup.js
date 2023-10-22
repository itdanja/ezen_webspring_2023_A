import axios from 'axios'

export default function Signup( props ) {

    const onSignup = () =>{ console.log( 'onSignup open' )
        let info = {
            memail : document.querySelector(".memail").value,
            mpassword : document.querySelector(".mpassword").value,
            mname : document.querySelector(".mname").value,
            mphone : document.querySelector(".mphone").value
        }
        console.log( info )
    // ajax ---> axios 변환
        axios
        .post("http://localhost:80/member/post" , info )
        .then( r => {  console.log( r );
            if( r.data == true  ){
                alert('회원가입 성공');
                window.location.href="/login"; // window.location.href="이동할 경로";
            }else{
                alert('회원가입 실패 [ 관리자에게 문의 ]');
            }
        })
        .catch( err => { console.log( err ) });
    }
    return(<div>
                <h3> 회원가입 페이지 </h3>
                <form>
                    아이디[이메일] : <input type="text" className="memail" /> <br/>
                    비밀번호 : <input type="text" className="mpassword" />  <br/>
                    전화번호 : <input type="text" className="mname" />  <br/>
                    연락처 :  <input type="text" className="mphone" />  <br/>
                    <button onClick={ onSignup } type="button"> 가입 </button>
                </form>
    </div>)
}