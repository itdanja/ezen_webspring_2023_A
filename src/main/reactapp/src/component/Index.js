/*
    Index : 여러 컴포넌트들을 연결하는 최상위 컴포넌트
        - 가상URL 정의해서 컴포넌트를 연결하는 공간/컴포넌트
*/
import{ BrowserRouter , Routes , Route , Link   }
    from 'react-router-dom';
/* 라우터에 적용할 컴포넌트 호출  */
import Header from './Header';
import Main from './Main';
import Footer from './Footer';
export default function Index( props ){
    return(<>
        <BrowserRouter >
            <Header />
                <Routes >
                    <Route path='/' element = { < Main/>} />
                </Routes >
            <Footer />
        </BrowserRouter >
    </>)
}