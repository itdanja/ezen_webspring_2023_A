import { useSearchParams , Link } from 'react-router-dom'
import axios from 'axios'

export default function BoardView( props ){

    const [ searchParams, setSearchParams ] = useSearchParams();
    const bno = searchParams.get('bno');

    // 1. 삭제 axios
    const onDelete = (e) =>{
        axios.delete( '/board' , { params : { bno : bno } } )
            .then( r =>{
                if( r.data ){
                    alert('게시물 삭제 성공');
                    window.location.href='/board/list'
                }
                else{ alert('게시물 삭제 실패')}
            })
    }
    return(<>
        <div>
            <h3> 개별 게시물 { bno } </h3>
            <button type="button" onClick={ onDelete } > 삭제 </button>
            <Link to={'/board/update?bno='+bno}>
                <button type="button"> 수정 </button>
            </Link>
        </div>
    </>)
}