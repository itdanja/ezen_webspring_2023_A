import axios from 'axios' // npm i axios

export default function Axios컴포넌트( props ) {

    function 함수1( e ){ console.log(e); }
    const 함수2 = ( e ) => { console.log(e); }
    const 함수3 = ( e , data ) => { console.log(data); }

    function doGet(){
        axios.get("https://jsonplaceholder.typicode.com/posts"  )
            .then( r => {  console.log( r ); })
        axios.get("https://jsonplaceholder.typicode.com/posts/1"  )
            .then( r => {  console.log( r ); })
        axios.get("https://jsonplaceholder.typicode.com/comments" , { params: {  'postId' : 1 } }  )
           .then( r => {  console.log( r ); })
    }
    function doPost(){
        axios.post("https://jsonplaceholder.typicode.com/posts"  )
            .then( r => {  console.log( r ); })
    }
    function doPut(){
        axios.put("https://jsonplaceholder.typicode.com/posts/1"  )
            .then( r => {  console.log( r ); })
    }
    function doDelete(){
        axios.delete("https://jsonplaceholder.typicode.com/posts/1"  )
            .then( r => {  console.log( r ); })
    }
    return ( <>
        <button onClick={ 함수1 } > 함수1 </button>
        <button onClick={ 함수2 } > 함수2 </button>
        <button onClick={ (e)=>함수3( e , 3 ) } > 함수3 </button>
        <button onClick={ doGet } > doGet AXIOS </button>
        <button onClick={ doPost } > doPost AXIOS </button>
        <button onClick={ doPut } > doPut AXIOS </button>
        <button onClick={ doDelete } > doDelete AXIOS </button>
    </> )
}