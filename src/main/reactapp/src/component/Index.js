import { BrowserRouter , Routes , Route }from "react-router-dom"

import Header from "./Header"
import Footer from "./Footer"
import Main from "./Main"
import Example from "./example/ExampleList"

import 컴포넌트1 from './example/day01/1_컴포넌트.jsx'
import 컴포넌트2 from './example/day01/2_컴포넌트.jsx'
import 컴포넌트3 from './example/day01/3_컴포넌트.jsx'
import 컴포넌트4 from './example/day01/4_컴포넌트.jsx'
import CSS컴포넌트 from './example/day02/1_CSS적용컴포넌트'
import CommentList from './example/day02/CommentList.js'

import Axios컴포넌트 from './example/day03/2_Axios컴포넌트.js'

import Login from './member/Login.js'
import Signup from './member/Signup.js'

export default function Index( props ) {
    return ( <>
        <div className="page">
            <BrowserRouter>
                <Header />
                    <Routes>
                        <Route path="/" element = { <Main/> } />
                        <Route path="/example" element = { <Example/> } />
                        <Route path="/example/day01/컴포넌트1" element = { <컴포넌트1/> } />
                        <Route path="/example/day01/컴포넌트2" element = { <컴포넌트2/> } />
                        <Route path="/example/day01/컴포넌트3" element = { <컴포넌트3/> } />
                        <Route path="/example/day01/컴포넌트4" element = { <컴포넌트4/> } />
                        <Route path="/example/day02/CSS컴포넌트" element = { <CSS컴포넌트/> } />
                        <Route path="/example/day02/CommentList" element = { <CommentList/> } />

                        <Route path="/example/day03/Axios컴포넌트" element = { <Axios컴포넌트/> } />

                        <Route path="/login" element = { <Login/> } />
                        <Route path="/signup" element = { <Signup/> } />

                    </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    </> )
}