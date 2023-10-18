import styles from './todo.css' // css 파일 가져오기
import React , { useState ,useEffect   } from 'react';
import axios from 'axios'

export default function Todo( props ){

    let [ row , setRow ] = useState( [] );

    const getData = ()=>{
         axios
            .get("http://localhost:80/todo"  )
            .then( r => {  setRow( r.data );  })
    }
    useEffect( () => { getData() } , [  ] )

    const setData = ()=>{
        console.log('setData')
         axios
            .post("http://localhost:80/todo" , { "tcontent" : document.querySelector(".tcontent").value } )
            .then( r => { getData(); })
    }

    const doPut = ( e,tno,tstate )=>{
        console.log('doPut : ' + !tstate)
        let info = { "tno" : tno , "tstate" : !tstate }
         axios
            .put("http://localhost:80/todo" , info )
            .then( r => { getData(); })
    }

    const doDelete = ( e,tno )=>{
        console.log('doDelete')
         axios
            .delete("http://localhost:80/todo" , { params : { "tno" :tno }  } )
            .then( r => { getData(); })
    }

    return ( <>
        <div className="todowrap">
            <h1> 나만의 할일 목록 </h1>
            <div className="todo_top">
                <input className="tcontent" type="text"/>
                <button onClick={ setData } type="button"> 등록 </button>
            </div>
            <div className="todo_bottom">
                {
                    row.map( (item)=>{
                         return(<div key={ item.tno } className={ item.tstate ? 'successTodo todo' : 'todo' }>
                             <div className="tcontent"> { item.tcontent } </div>
                             <div className="etcbtns">
                                 <button onClick={ (e)=> doPut( e,item.tno  , item.tstate ) } type="button">상태변경</button>
                                 <button onClick={ (e)=> doDelete(  e,item.tno  ) } type="button">제거하기</button>
                             </div>
                         </div>)
                    })
                }
            </div>
        </div>
    </> );
}