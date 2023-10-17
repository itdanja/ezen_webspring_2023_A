function 컴포넌트3() {
    return (
        <>
            <h1> 다른 컴포넌트 호출해보기 </h1>
            <내가만든태그 />
        </>
    )
}
function 내가만든태그() {
    return <div> 안녕.. --내가만든태그-- 에서 작성된 HTML 입니다. </div>
}
export default 컴포넌트3;