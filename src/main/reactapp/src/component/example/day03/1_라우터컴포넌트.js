import { BrowserRouter , Routes , Route , Link }from "react-router-dom"

import 컴포넌트1 from "../day01/1_컴포넌트"
import 컴포넌트2 from "../day01/2_컴포넌트"
import 컴포넌트3 from "../day01/3_컴포넌트"
import 컴포넌트4 from "../day01/4_컴포넌트"

export default function 라우터컴포넌트( props ) {
    return ( <>
        <BrowserRouter>
            <고정컴포넌트 />
            <Routes>
                <Route path="/day01/컴포넌트1" element = { <컴포넌트1/> } />
                <Route path="/day01/컴포넌트2" element = { <컴포넌트2/> } />
                <Route path="/day01/컴포넌트3" element = { <컴포넌트3/> } />
                <Route path="/day01/컴포넌트4" element = { <컴포넌트4/> } />
            </Routes>
        </BrowserRouter>
    </> )
}
function 고정컴포넌트( props ){
    return(<>
        <div>
            <Link to='/day01/컴포넌트1' > 컴포넌트1 </Link>
            <Link to='/day01/컴포넌트2' > 컴포넌트2 </Link>
            <Link to='/day01/컴포넌트3' > 컴포넌트3 </Link>
            <Link to='/day01/컴포넌트4' > 컴포넌트4 </Link>
        </div>
    </>)
}
