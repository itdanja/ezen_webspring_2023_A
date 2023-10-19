// 1.리액트 확장자 : jsx , js

    // 1-1. css속성[ 카멜표기법 background-color --> backgroundColor ]이 정의된 객체 선언
    // 1-2  마크업 style속성 ={ CSS속성이있는객체 }

    // 2-1 마크업 style속성 = { { 속성:속성값 , 속성:속성값 } }

    // 3. css 파일에서 순수css문법
        // 1. 마크업에 className 명 정의
        // 2. css파일에서 css 작성
        // 3. 적용할 컴포넌트가 있는 파일내 import
            // import styles from 'css파일경로';
// 1. 컴포넌트 문법 원형
import styles from './컴포넌트.css';

export default function CSS컴포넌트( props ){
    // 1. CSS를 객체에 속성[카멜표기법] 으로 선언하기
    const cssStyle = {
        backgroundColor: 'red',
        width: '500px' ,
        height: '100px',
        margin : '0 auto'
    }
    return(<>
        <div style={ cssStyle } > CSS 적용하는방법1 </div>
        { /* style속성에 { { 속성명:속성값 , 속성명:속성값 } } */}
        <div style={ {  backgroundColor: 'blue',
                         width: '500px' ,
                         height: '100px',
                         margin : '0 auto'
                     } }> CSS 적용하는방법2 </div>
        <div className="box3" onclick > CSS 적용하는방법3 </div>
    </>);
}