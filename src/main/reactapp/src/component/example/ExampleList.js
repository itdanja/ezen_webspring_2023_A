import { Link } from "react-router-dom"

export default function ExampleList( props ) {
    return ( <>
        <div style={{ display: 'flex', justifyContent: 'space-between' }} >
            <Link to='/example/day01/컴포넌트1'>컴포넌트1</Link>
            <Link to='/example/day01/컴포넌트2'>컴포넌트2</Link>
            <Link to='/example/day01/컴포넌트3'>컴포넌트3</Link>
            <Link to='/example/day01/컴포넌트4'>컴포넌트4</Link>

            <Link to='/example/day02/CSS컴포넌트'>CSS컴포넌트</Link>
            <Link to='/example/day02/CommentList'>CommentList</Link>

            <Link to='/example/day03/Axios컴포넌트'>Axios컴포넌트</Link>

        </div>
    </> )
}