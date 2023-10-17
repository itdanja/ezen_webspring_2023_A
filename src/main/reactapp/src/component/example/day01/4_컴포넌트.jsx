function 컴포넌트4() {
    return (
        <>
            <h1> 다른 컴포넌트 호출해보기 </h1>
            <내가만든태그에속성 내용="컴포넌트4에서 넣어준 내용" />
        </>
    )
}
function 내가만든태그에속성( props ) {
    return <div> 안녕.. --속성 : { props.내용 }-- 에서 작성된 HTML 입니다. </div>
}
export default 컴포넌트4;