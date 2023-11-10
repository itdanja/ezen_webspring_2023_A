
import axios from 'axios';
import { useEffect , useState } from 'react'
export default function CategoryWrite( props ){

    // 0.출력할 카테고리 목록 을 저장하는 상태변수
    const [ categoryList , setCategoryList ] = useState( [] );

    // 1. 카테고리 등록 AXIOS // 등록버튼을 클릭했을때
    const addCategory = (e)=>{
        const info = { pcname : document.querySelector('.pcname').value }
        axios.post( '/product/category' , info )
            .then( r => {
                if( r.data ){alert("카테고리등록성공"); printCategory() }
                else{ alert("카테고리등록실패")}
             } )
    }
    // 2. 카테고리 출력 AXIOS // 컴포넌트가 열렸을때 / 등록되었을때[재랜더링]
    const printCategory = (e)=>{
        axios.get('/product/category')
            .then( r => { console.log(r.data); setCategoryList(r.data); } );
    }
    useEffect( ()=>{ printCategory() } , [ ] )

    // 3. 카테고리 수정 AXIOS // 수정버튼을 클릭했을때
    const updateCategory = (e , pcno )=>{
        const info = { pcno : pcno   , pcname : "" }
        axios.put('/product/category' , info )
            .then( r => { console.log(r.data); } );
    }
    // 4. 카테고리 삭제 AXIOS // 삭제버튼을 클릭했을때
    const deleteCategory = ( e , pcno )=>{
        axios.delete('/product/category' , { params : { pcno : pcno } }  )
            .then( r=>{ console.log(r.data); })
    }

    return(<>
        <div style={{ width : '300px' , margin : '0 auto' }}>
            <h3> 카테고리 등록 </h3>
            <form>
                <input type="text"
                    className="pcname"
                    placeholder="등록할카테고리명" />

                <button type="button"
                    onClick={ addCategory }>등록</button>
            </form>
        </div>
    </>)

}