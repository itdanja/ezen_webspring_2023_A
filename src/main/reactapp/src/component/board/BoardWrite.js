import axios from 'axios';
// npm install --save @ckeditor/ckeditor5-react @ckeditor/ckeditor5-build-classic
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

export default function BoardWrite( props ){
    // 1. 등록함수
    const boardWrite = (e)=>{
        // 1. 폼(변수=name)가져오기 [ 첨부파일 ]
        let boardForm = document.querySelectorAll('.boardForm')[0];
        let boardFormData = new FormData( boardForm );
        // 2. axios 전송
        axios.post("/board" , boardFormData )
             .then( result => {
                if( result.data ){
                    alert('글등록 성공');
                    window.location.href='/board/list';
                }else{ alert('글등록 실패') }
              } );
    }

    return(<>
        <div>
            <h3> 게시물 쓰기 </h3>
            <form className="boardForm">
                <input type="text" placeholder ='제목' name="btitle" />       <br/>
                <textarea placeholder='내용' name="bcontent"> </textarea>     <br/>
               <CKEditor
                    editor={ ClassicEditor }
                    data=""
                    onChange={ ( event, editor ) => { const data = editor.getData(); bcontent = data  } }
                />
                <input type="file" name="file" /> <br/>
                <button type="button" onClick={ boardWrite } >등록 </button>
            </form>
        </div>
    </>)
}