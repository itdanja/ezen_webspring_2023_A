
import { useState } from 'react'


export default function 상태컴포넌트( props ) {

    const [ text , setText ] = useState('');

    const [count, setCount] = useState(0);

    const onInputChange = ( e )=>{
        setText(  e.target.value  );
    }

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