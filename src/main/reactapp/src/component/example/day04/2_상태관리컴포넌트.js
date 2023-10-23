import { useState } from 'react'

export default function 상태관리컴포넌트( props ) {

    const 상태 = useState( '유재석' );   console.log( 상태 );
    const 상태값 = 상태[0];              console.log( 상태값 );
    const 갱신함수 = 상태[1];             console.log( 갱신함수 );

    let value = 10;
    const [ text , setText ] = useState('');
    const [ count, setCount] = useState(0);
    const onInputChange = ( e )=>{
        value++;
        setText(  e.target.value  );
    }
    console.log('value : ' + value )
    console.log('상태1 : ' + text )
    console.log('상태2 : ' + count )
    return ( <>
        <input
            type="text"
            value={ text }
            onChange={ onInputChange }
        />
      <p>You clicked {count} times</p>
      <button onClick={() => setCount(count + 1)}>
        +
      </button>
      <button onClick={() => setCount(count - 1)}>
        -
      </button>
    </> )
}