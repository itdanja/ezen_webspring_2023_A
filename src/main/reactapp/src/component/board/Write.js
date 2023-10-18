import axios from 'axios'
export default function write(){

    const onWrite = () => {

        let loginForm = document.querySelectorAll('.writeForm')[0];
        let loginFormData = new FormData(loginForm);

        axios.post( 'http://localhost:80/board' , loginFormData )
            .then( r => {
                console.log(r);
                if( r.data == 1 ){ alert('카테고리 선택후 쓰기 가능 합니다.[전체보기제외]'); }
                else if( r.data == 2 ){ alert('로그인후 작성이 가능합니다.'); }
                else if( r.data == 3 ){ alert('게시물작성실패[ 관리자에게문의]'); }
                else if( r.data == 4 ){ alert('게시물작성성공');  }
            })
    }

    return(<>
          <h3> 글쓰기 페이지 </h3>
          <form className="writeForm">
               아이디[이메일] : <input type="text" name="btitle" />
               비밀번호 : <input type="text" name="bcontent" />  <br/>
               <button onClick={ onWrite } type="button"> 등록 </button>
           </form>
    </>)

}