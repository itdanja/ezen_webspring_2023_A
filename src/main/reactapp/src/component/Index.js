/*
    Index : 여러 컴포넌트들을 연결하는 최상위 컴포넌트
        - 가상URL 정의해서 컴포넌트를 연결하는 공간/컴포넌트
*/
import{ BrowserRouter , Routes , Route , Link   } from 'react-router-dom';
/* 라우터에 적용할 컴포넌트 호출  */
import Header from './Header';
import Main from './Main';
import Footer from './Footer';

/* Example import */
import ExampleList from './example/ExampleList';
import 컴포넌트1 from './example/day01/1_컴포넌트';
import 컴포넌트2 from './example/day01/2_컴포넌트';
import 컴포넌트3 from './example/day01/3_컴포넌트';
import 컴포넌트4 from './example/day01/4_컴포넌트';
import CSS컴포넌트 from './example/day02/1_CSS적용컴포넌트';
import CommentList from './example/day02/CommentList';
import Axios컴포넌트 from './example/day04/1_Axios컴포넌트';

/* Member import */
import Login from './member/Login';
import Signup from './member/Signup';
import Info from './member/Info';
/* Board import */
import BoardList from './board/BoardList';
import BoardWrite from './board/BoardWrite';
import BoardView from './board/BoardView';
import BoardUpdate from './board/BoardUpdate';
/* Product import */
import ProductAdmin from './product/ProductAdmin';

import { useState , useEffect , useRef , createContext  } from 'react'
import { SnackbarProvider, useSnackbar } from 'notistack'; // npm install notistack

export const AppContext = createContext();


export default function Index( props ){

    const { enqueueSnackbar } = useSnackbar();
      let ws = useRef(null);

      if (!ws.current) {
            // 2. 웹소켓
            // ================= 소켓 s =================== //
            // 1. 클라이언트소켓 만들기
            ws.current = new WebSocket("ws://localhost:80/chat");
            console.log( ws )
            // 1. 서버소켓과 연동 성공했을때. 이후 행동/메소드 정의
            ws.current.onopen = (e)=>{ console.log(e); }
            // 2. 서버소켓과 세션 오류가 발생했을때 이후 행동/메소드 정의
            ws.current.onerror = (e)=>{ console.log(e); }
            // 3. 서버소켓과 연동이 끊겼을때. 이후 행동/메소드 정의
            ws.current.onclose = (e)=>{ console.log(e); }
            // 4. 서버소켓으로부터 메시지를 받았을때. 이후 행동/메소드 정의
            ws.current.onmessage = (e)=>{ console.log(e);
                enqueueSnackbar(e.data,  { variant: "success" } );
            }
      }

    return(<>

        <div className="webContainer">
            <AppContext.Provider value={ws}>
            <BrowserRouter >
                <Header />
                    <Routes >

                        {/* MAIN*/}
                        <Route path='/' element = { <Main />} />
                        {/* EXAMPLE */}
                         <Route path='/example' element = { <ExampleList />} />
                            <Route path='/example/day01/컴포넌트1' element = { <컴포넌트1 />} />
                            <Route path='/example/day01/컴포넌트2' element = { <컴포넌트2 />} />
                            <Route path='/example/day01/컴포넌트3' element = { <컴포넌트3 />} />
                            <Route path='/example/day01/컴포넌트4' element = { <컴포넌트4 />} />
                            <Route path='/example/day02/CSS적용컴포넌트' element = { <CSS컴포넌트 />} />
                            <Route path='/example/day02/CommentList' element = { <CommentList />} />
                            <Route path='/example/day04/Axios컴포넌트' element = { <Axios컴포넌트 />} />
                        {/* MEMBER */}
                        <Route path='/login' element = { <Login />} />
                        <Route path='/signup' element = { <Signup />} />
                        <Route path='/info' element = { <Info />} />
                        {/* BOARD */}
                        <Route path='/board/list' element = { <BoardList />} />
                        <Route path='/board/write' element = { <BoardWrite />} />
                        <Route path='/board/view' element = { <BoardView />} />
                        <Route path='/board/update' element = { <BoardUpdate />} />
                        {/* admin */}
                        <Route path='/admin/product' element = { <ProductAdmin />} />

                    </Routes >
                <Footer />
            </BrowserRouter >
            </AppContext.Provider>
        </div>

    </>)
}