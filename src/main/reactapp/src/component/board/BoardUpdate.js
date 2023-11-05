import axios from 'axios';
import { useSearchParams } from "react-router-dom";
import { useState , useEffect } from 'react';
import{  Link   } from 'react-router-dom';

export default function BoardUpdate( props ){
    const [searchParams, setSearchParams] = useSearchParams();
    const bno = searchParams.get('bno');

    let [ data , setData ] = useState( { } )

    let loginMember = JSON.parse( sessionStorage.getItem('login_token') )

    // 1. 개별 출력 함수
     useEffect( ()=>{   // 컴포넌트가 생성될때 1번 되는 axios
         axios.get('/board/doGet' , { params: { bno: bno} } )
            .then( r =>{
                setData( r.data );
            });
     } , [] );

    // 1. 등록함수
    const boardUpdate = (e)=>{
        // 1. 폼(변수=name)가져오기 [ 첨부파일 ]
        let boardForm = document.querySelectorAll('.boardForm')[0];
        let boardFormData = new FormData( boardForm );

        boardFormData.set("bno" , bno );

        // 2. axios 전송
        axios.put("/board" , boardFormData )
             .then( result => {
                if( result.data ){
                    alert('글수정 성공');
                    window.location.href='/board/list';
                }else{ alert('글수정 실패') }
              } );
    }
    return(<>
        <div>
            <h3> 게시물 쓰기 </h3>
            <form className="boardForm">
                <input type="text" placeholder ='제목' name="btitle" value={ data.btitle } onChange={  (e)=>{ setData( {...data , btitle : e.target.value } ) }  } />       <br/>
                <textarea placeholder='내용' name="bcontent" value={ data.bcontent } onChange={ (e)=>{ setData( {...data , bcontent : e.target.value } ) } }> </textarea>     <br/>
                <button type="button" onClick={ boardUpdate } > 수정 </button>
            </form>
        </div>
    </>)
}