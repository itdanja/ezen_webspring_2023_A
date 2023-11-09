import { useSearchParams , Link } from 'react-router-dom'
import axios from 'axios'
import { useState , useEffect } from 'react'

export default function Category( props ){
    const category = props.category;
    return(<>
        <div style={{ display: 'flex' ,justifyContent: 'space-between' }}>
            <div>
                { category.pcname }
            </div>
            <div>
                <button onClick={ props.updateCategory } >수정</button>
                <button onClick={ (e) => { props.deleteCategory( e, category.pcno ) } } >삭제</button>
            </div>
        </div>
    </>)
}

