import { useSearchParams , Link } from 'react-router-dom'
import axios from 'axios'
import { useState , useEffect } from 'react'


export default function ProductWrite( props ){
    // 1. 등록함수
    const addProduct = (e)=>{
        // 1. 폼(변수=name)가져오기 [ 첨부파일 ]
        let productForm = document.querySelectorAll('.productForm')[0];
        let productFormData = new FormData( productForm );
        // 2. axios 전송
        axios.post("/product" , productFormData )
         .then( result => {
            if( result.data ){
                alert('글등록 성공');
            }else{ alert('글등록 실패') }
          } );
    }

    const [ categoryList , setCategoryList ] = useState( [] );
    const printCategory = (e  )=>{
        axios.get( '/product/category'   )
            .then( r => { setCategoryList( r.data ); })
    }
    useEffect( ()=>{ printCategory()  } , [])

    const [ productList , setProductList ] = useState( [] );
    const printProduct = (e  )=>{
        axios.get( '/product'   )
            .then( r => { setProductList( r.data ); })
    }
    useEffect( ()=>{ printProduct()  } , [])

    console.log( productList )

    return(<>
        <div style={{ width: '300px', margin : '0 auto'}}>
            <h3> 제품 등록 </h3>
            <form className="productForm">
                <select name='pcno'>
                    {
                        categoryList.map( (c)=>{
                            return <option value={c.pcno} > { c.pcname } </option>
                        })
                    }
                </select>
                <input type="text" placeholder ='제목명' name="pname" />       <br/>
                <textarea placeholder='제품설명' name="pcomment"> </textarea>     <br/>
                <input type="text" placeholder ='제품가격' name="pprice" />       <br/>
                <input type="text" placeholder ='초기재고' name="pstock" />       <br/>
                <input type="file" name="fileList" multiple /> <br/>
                <button type="button" onClick={ addProduct } >등록 </button>
            </form>
        </div>
    </>)
}

