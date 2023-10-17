import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

import 컴포넌트1 from './component/example/day01/1_컴포넌트';
import 컴포넌트2 from './component/example/day01/2_컴포넌트';
import 컴포넌트3 from './component/example/day01/3_컴포넌트';
import 컴포넌트4 from './component/example/day01/4_컴포넌트';
import 도서목록 from './component/example/day01/5_도서목록';
import Clock from './component/example/day01/6_리액트시계';

const root = ReactDOM.createRoot(document.getElementById('root'));

//root.render( <React.StrictMode> <App /> </React.StrictMode> );
//root.render( <React.StrictMode> <컴포넌트1 /> </React.StrictMode> );
//root.render( <React.StrictMode> <컴포넌트2 /> </React.StrictMode> );
//root.render( <React.StrictMode> <컴포넌트3 /> </React.StrictMode> );
//root.render( <React.StrictMode> <컴포넌트4 /> </React.StrictMode> );
//root.render( <React.StrictMode> <도서목록 /> </React.StrictMode> );
setInterval( () => {  root.render( <React.StrictMode>  <Clock /> </React.StrictMode>  );} , 1000 );

reportWebVitals();
