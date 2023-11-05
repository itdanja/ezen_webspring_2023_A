import axios from 'axios';
import { useSearchParams } from "react-router-dom";
import { useState , useEffect } from 'react';
import{  Link   } from 'react-router-dom';

export default function BoardView( props ){
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

     //
     const boardDelete = (e)=>{
         // 2. axios 전송
         axios.delete('/board' , { params: { bno: bno} } )
            .then( r =>{
                if( r.data ){
                    alert('글삭제 성공');
                    window.location.href='/board/list';
                }else{ alert('글삭제 실패') }
            });
     }

    return(<>
        <div>
            <h3> 게시물 상세 {bno} </h3>
            <form className="boardForm">
                <input type="text" placeholder ='제목' name="btitle" value={ data.btitle } />       <br/>
                <textarea placeholder='내용' name="bcontent" value={ data.bcontent }> </textarea>     <br/>
                { data.mno == loginMember.mno ?
                (<>
                 <button type="button" onClick={ boardDelete } >삭제 </button>
                 <Link to={'/board/update?bno='+data.bno}>수정</Link>
                 </>)
                 :
                 ''
                 }
            </form>
        </div>
    </>)
}