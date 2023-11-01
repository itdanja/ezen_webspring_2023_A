import axios from 'axios';
import { useState , useEffect } from 'react';

export default function BoardWrite( props ){

    // 1. 게시물 쓰기
    const setBoard = () => {

        let boardForm = document.querySelectorAll('.boardForm')[0];
        let boardFormData = new FormData( boardForm );

        axios.post( '/board' , boardFormData )
            .then( r => {
                console.log(r);
            })
    }

    return(<>
        <div >
            <h3> 게시물 쓰기 </h3>
            <form className="boardForm">
                <input type="text" name="btitle"/>
                <textarea name="bcontent"/>
                <button onClick={  setBoard }> 등록 </button>
            </form>
        </div>

    </>)
}
