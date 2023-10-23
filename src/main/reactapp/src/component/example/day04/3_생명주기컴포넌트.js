
import { useState ,useEffect   } from 'react';

export default function 생명주기컴포넌트( props ) {
    let [ value , setValue ] = useState( 0 );    console.log( "value : " + value)

    useEffect( () => {
        console.log(' =====> [] 없는 useEffect1  실행')
    }  )

    useEffect( () => {
        console.log(' =====> =====> [] 있는 useEffect2 실행')
    } , [ ] ) //  빈 배열 [ ] 대입  update 제외

    useEffect( () => {
        console.log(' =====> =====> =====> [value] 있는 useEffect3 실행')
    } , [ value ] ) //  배열 [ 상태변수명 ] 대입  해당  상태변수가 update 될때마다

    return (<>
                   <p> { value } </p>
                   <button onClick={ () => setValue( value+1 ) }>
                   +
                   </button>
             </>)
}