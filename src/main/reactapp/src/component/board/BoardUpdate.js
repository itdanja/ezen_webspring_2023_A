import { useSearchParams } from 'react-router-dom'
import axios from 'axios'

export default function BoardUpdate( props ){

    const [ searchParams, setSearchParams ] = useSearchParams();
    const bno = searchParams.get('bno');

    return(<>
        <div>
            <h3> 게시물 수정 { bno } </h3>
        </div>
    </>)
}