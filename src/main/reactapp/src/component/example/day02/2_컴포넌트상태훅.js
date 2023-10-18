
import React , { useState ,useEffect   } from 'react';

export default function 컴포넌트상태훅(props){
    let [ value , setValue ] = useState( 0 );    console.log( "value : " + value)

    useEffect( () => {
        console.log(' =====> [] 없는 useEffect1  실행')
        return () => { // mount 실행x
            console.log(" =====> [] 없는 useEffect1 종료 ")
        }
    }  )

    useEffect( () => {
        console.log(' =====> =====> [] 있는 useEffect2 실행')
        return () => { // mount 실행x
            console.log(" =====> =====> [] 있는 useEffect2 종료 ")
        }
    } , [ ] ) //  빈 배열 [ ] 대입  update 제외

    useEffect( () => {
        console.log(' =====> =====> =====> [value] 있는 useEffect3 실행')
        return () => { // mount 실행x
            console.log(" =====> =====> =====> [value] 있는 useEffect3 종료 ")
        }
    } , [ value ] ) //  배열 [ 상태변수명 ] 대입  해당  상태변수가 update 될때마다

    return ( <>
                   <p> { value } </p>
                   <button onClick={ () => setValue( value+1 ) }>
                   +
                   </button>
             </> )

    /*
    let [ count , setCount ] = useState( 0 );
    const countHandler =  ()=>{
        alert(' countHandler 함수 실행');
        setCount( count+1 );
    }
    return (<>
        <div>
            <p> 총 { count } 번 클릭했습니다 </p>
            <button onClick={ countHandler }>
                증가
            </button>
        </div>
    </>)
    */
    /*
    console.log('Hook1 컴포넌트 실행 ')
    let count = 0;
    const countHandler =  ()=>{
        alert(' countHandler 함수 실행');
        count++;
        alert('현재 count : ' + count );
    }
    return (<>
        <div>
            <p> 총 { count } 번 클릭했습니다 </p>
            <button onClick={ countHandler }>
                증가
            </button>
        </div>
    </>)
    */

}