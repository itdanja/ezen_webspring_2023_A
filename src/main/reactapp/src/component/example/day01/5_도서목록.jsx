function 도서목록() {
    return (
        <>
            <도서 도서명="이것이 자바다" 저자="유재석" 소비자가격={30000} />
            <도서 도서명="이것이 파이썬" 저자="강호동" 소비자가격={25000} />
            <도서 도서명="이것이 리액트" 저자="신동엽" 소비자가격="28000" />
        </>
    )
}
function 도서( props ) {
    console.log( props );
    return (
        <div>
            <h3> 도서명 : { props.도서명 } </h3>
            <span> 저자 : { props.저자 } / 소비자가격 : {props.소비자가격 } </span>
        </div>
    )
}
export default 도서목록;