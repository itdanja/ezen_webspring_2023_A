
import axios from 'axios';
import { useEffect , useState } from 'react'

export default function Category( props ){
    // props : 속성객체{ 키:값 , 키:값 }
    const category = props.category

    return(<>
        <div style={{ display:'flex' , justifyContent : 'space-between'}}>
            <div> { category.pcname } </div>
            <div>
                <button type="button">수정</button>
                <button
                    onClick={ (e)=>{ props.deleteCategory( e , category.pcno  ) } }
                    type="button">삭제</button>
            </div>
        </div>
    </>)

}