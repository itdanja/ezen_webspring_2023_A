import { useSearchParams } from 'react-router-dom'
import axios from 'axios'
import { useState , useEffect } from 'react'

export default function BoardUpdate( props ){

    const [ searchParams, setSearchParams ] = useSearchParams();
    const bno = searchParams.get('bno');

    // 1. 현재 게시물의 정보를 가지는 상태관리 변수
    const [ board , setBoard ] = useState( { } )
    // 2. 개별 게시물 호출 axios  [ 실행조건 : 컴포넌트 최초 1번 실행 ]
    const onGet = (e) => {
        axios.get( '/board/doGet' , { params : { bno : bno } }  )
            .then( r => { setBoard(r.data); })
    }
    useEffect( ()=>{ onGet() } , [] )

    // 3. 개별 게시물 수정 요청
    const boardUpdate = (e) => {
        // 1. 폼 가져오기
        const boardForm = document.querySelectorAll('.boardForm')[0]
        const boardFormData = new FormData( boardForm );
        // boardFormData : 입력받은 수정할제목,내용 +++ 수정할 게시물번호
        boardFormData.set( 'bno' , bno ); // 수정할 게시물번호를 폼 속성에 추가
        // 2. axios 이용한 수정 요청
        axios.put('/board' , boardFormData )
            .then( r =>{
                if( r.data ){
                    alert('글수정 성공');
                    window.location.href = '/board/view?bno='+bno;
                }else { alert('글수정 실패') }
            } )
    }

    return(<>
        <div>
            <h3> 게시물 수정 { bno } </h3>
            <form className="boardForm">

                <input type="text"
                    placeholder ='제목'
                    name="btitle"
                    value={ board.btitle }
                    onChange={ (e)=>{
                        setBoard( {...board , btitle : e.target.value } )
                    }}
                    />                   <br/>

                <textarea placeholder='내용'
                    name="bcontent"
                    value={ board.bcontent }
                    onChange={ (e)=>{
                        setBoard( {...board , bcontent : e.target.value } )
                    }}
                    > </textarea>     <br/>

                <button type="button"
                    onClick={ boardUpdate } >등록 </button>

            </form>
        </div>
    </>)
}