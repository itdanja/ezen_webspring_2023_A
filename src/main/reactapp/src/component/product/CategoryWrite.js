import { useSearchParams , Link } from 'react-router-dom'
import axios from 'axios'
import { useState , useEffect } from 'react'

import Category from './Category'

export default function CategoryWrite( props ){

    const [ categoryList , setCategoryList ] = useState( [] );

    const addCategory = (e)=>{
        let info = {  pcname : document.querySelector('.pcname').value  };
        axios.post( '/product/category' , info )
            .then( r => {
                if( r.data ){ alert('카테고리 성공'); printCategory(); }
                else{ alert('카테고리 실패'); }
            })
    }
    const printCategory = (e  )=>{
        axios.get( '/product/category'   )
            .then( r => { setCategoryList( r.data ); })
    }
    useEffect( ()=>{ printCategory()  } , [])

    const updateCategory = (e)=>{
        let info = { }
        axios.put( '/product/category' , info )
            .then( r => {})
    }
    const deleteCategory = (e , pcno)=>{
        axios.delete( '/product/category' , { params : { pcno : pcno } } )
            .then( r => { printCategory(); })
    }

    return(<>
        <div style={{ width: '300px', margin : '0 auto'}}>

            <h3> 카테고리 등록 </h3>

            <form>
                카테고리명 <input type="text" placeholder='등록할 카테고리명' className='pcname' />
                <button onClick={ addCategory } type="button">등록</button>
            </form>

            <h3> 카테고리 목록 </h3>
            {
                categoryList.map( (c)=>{
                    return <Category
                        category={c}
                        updateCategory={updateCategory}
                        deleteCategory={deleteCategory} />
                })
            }
        </div>
    </>)
}

