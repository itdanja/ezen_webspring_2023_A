import logo from '../../../logo.svg'; // 사진 호출 하기 : import 사진명 from '사진경로';
import styles from './Comment.css' // CSS 파일 호출 하기 : import styles from 'CSS파일경로'
export default function Comment( props ){ /* 하나의 게시물 구역 */
    return(<>
        <div className="wrap">
            <div> <img src={ logo } className="pimg" /> </div> { /* 작성자 프로필 */}
            <div className="commentBox">
                <div className="commentName"> { props.name } </div> { /* 작성자 이름 */}
                <div className="commentContent"> { props.content }  </div> { /* 게시물 내용 */}
            </div>
        </div>
    </>)
}