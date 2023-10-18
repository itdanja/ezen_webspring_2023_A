import styles from './1_컴포넌트.css' // css 파일 가져오기

export default function 컴포넌트CSS(){

    const cssStyle = {
        backgroundColor: 'red' ,
        width: '500px', height: '200px',
        margin: '0 auto'
    }

    return(<>
        <div style={ cssStyle } > style속성을 이용한 CSS 적용1 </div>
        <div style={ {
                backgroundColor: 'blue' ,
                width: '500px',height: '200px',
                margin: '0 auto'
                } } >
                style속성을 이용한 CSS 적용2 </div>
        <div className="box"> className[class] 속성을 이용한 CSS 적용3  </div>
    </>)
}


/*
    export default function 컴포넌트CSS(){
        return(<></>)
    }
*/