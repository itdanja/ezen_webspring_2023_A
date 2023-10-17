export default function Clock( props ){
    return ( <>
        <div>
            <h3> 리액트 시계 </h3>
            <h4>
                현재 시간 : { new Date().toLocaleTimeString() }
            </h4>
        </div>
    </>);
}