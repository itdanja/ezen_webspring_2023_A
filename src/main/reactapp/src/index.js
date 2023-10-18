import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

import Login from './component/member/Login';
import 컴포넌트CSS from './component/example/day02/1_컴포넌트CSS';
import CommentList from './component/example/day02/CommentList';
import 컴포넌트상태훅 from './component/example/day02/2_컴포넌트상태훅';

import Todo from './component/example/todo/Todo';

const root = ReactDOM.createRoot(document.getElementById('root'));
/*
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
*/
root.render(
    <Todo />
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
