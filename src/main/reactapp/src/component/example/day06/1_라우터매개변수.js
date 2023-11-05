
import{ BrowserRouter , Routes , Route , Link   } from 'react-router-dom';
import { useSearchParams } from "react-router-dom";
import { useParams } from 'react-router-dom';
import { useState } from 'react'

export default function 라우터매개변수(){
    return(<>
        <BrowserRouter >
            <Routes >
                <Route path='/' element = { <목록페이지 />} />
                <Route path='/view1' element = { <상세_쿼리스트링매개변수 />} />
                <Route path='/view2/:bno/:value' element = { <상세_경로매개변수 />} />
            </Routes >
        </BrowserRouter >
    </>);
}

function 목록페이지(){
    let [ value , setValue ] = useState('');
    return(<>
        <h3>목록페이지</h3>
        <input type="text" value={ value } onChange={ (e)=>{ setValue( e.target.value ); } } />  <br/>
        <a href={"/view1?bno=1&value="+value}>상세페이지_쿼리스트링매개변수</a> <br/>
        <a href={"/view2/1/"+value}>상세페이지_경로매개변수</a> <br/>
    </>);
}


function 상세_쿼리스트링매개변수(){
    const [searchParams, setSearchParams] = useSearchParams();
    const bno = searchParams.get('bno');
    const value = searchParams.get('value');

    return(<>
        <h3>상세_쿼리스트링매개변수</h3>
        <div>{ bno } </div>
        <div>{ value } </div>
    </>);
}


function 상세_경로매개변수(){
    let  params  = useParams();
    const bno = params.bno
    const value = params.value

    return(<>
        <h3>상세_쿼리스트링</h3>
        <div>{ bno } </div>
        <div>{ value } </div>
    </>);
}

