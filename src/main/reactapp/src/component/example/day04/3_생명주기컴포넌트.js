import { useState ,useEffect } from 'react';

export default function 생명주기컴포넌트( props ) {
    let [ value , setValue ] = useState( 0 );

    useEffect( () => {
        console.log(' =====> [] 없는 useEffect1  실행')
    }  )

    useEffect( () => {
        console.log(' =====> =====> [] 있는 useEffect2 실행')
    } , [ ] )

    useEffect( () => {
        console.log(' =====> =====> =====> [value] 있는 useEffect3 실행')
    } , [ value ] )

    return (<>
           <p> { value } </p>
           <button onClick={ () => setValue( value+1 ) }>
           +
           </button>
     </>)
}

