export default function Error404( props ){
    return(<>
        <div style={{
            width: '100%',
            height: '500px',
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            flexDirection: 'column',
        }}>
            <h1> 404리액트에서 라우터(경로) 가 없을때 나오는 페이지에요.</h1>
            <div> <a href="/"> 홈으로 </a> </div>
        </div>
    </>)
}