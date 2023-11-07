/*
    mui : 리액트 전용 라이브러리
        1. 설치
            - cd src/main/reactapp
            npm install @mui/material @emotion/react @emotion/styled
            npm install @mui/material @mui/styled-engine-sc styled-components
        2. 예제
            1.  사용할 mui 컴포넌트를 상단에 import
                import Button from '@mui/material/Button';
            2. 호출된 mui컴포넌트를 사용
                <Button variant="contained">Hello world</Button>
*/

import axios from 'axios';
import { useState , useEffect } from 'react';
import {  Link   } from 'react-router-dom';
// ------------ mui table 관련 컴포넌트 import --------------- //
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
// --------------------------- //
// ---------------- mui table sample -------------- //
// ------------------------------------------------ //
// ---------------- Pagination -------------------- //
import Pagination from '@mui/material/Pagination';
// ------------------------------------------------ //

export default function BoardList( props ){

    // 0. 컴포넌트 상태변수 관리
    let [ pageDto , setPageDto ] = useState( {
        boardDtos : [] ,
        totalPages : 0 ,
        totalCount : 0
    } ); // 스프링에서 전달해주는 DTO와 일치화

    const [ page , setPage ] = useState( 1 );
    // 1. axios를 이용한 스프링의 컨트롤과 통신
    useEffect( ()=>{   // 컴포넌트가 생성/특정 상태변수가 변경 될때  axios
        axios.get('/board' , { params : { page : page } } ).then( r =>{
                // r.data : PageDto
                // r.data.boardDtos : PageDto 안에 있는 boardDtos
               setPageDto( r.data ); // 응답받은 모든 게시물을 상태변수에 저장
               // setState : 해당 컴포넌트가 업데이트(새로고침/재랜더링/return재실행)
           });
    } , [ page ] );

    // 2.
    const onPageSelect = ( e , value )=>{
        console.log( value );
        setPage( value );
    }

    return(<>
        <h3> 게시물 목록 </h3>

        <a href="/board/write">글쓰기</a>

        <p> page : { page  } </p>

        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            {/*테이블 제목 구역*/}
            <TableHead>
              <TableRow>
                <TableCell align="right">번호</TableCell>
                <TableCell align="right">제목</TableCell>
                <TableCell align="right">작성자</TableCell>
                <TableCell align="right">작성일</TableCell>
                <TableCell align="right">조회수</TableCell>
              </TableRow>
            </TableHead>
             {/*테이블 내용 구역*/}
            <TableBody>
              { pageDto.boardDtos.map((row) => ( // map is not a function
                <TableRow key={row.name}  sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                  <TableCell align="right">{row.bno}</TableCell>
                  <TableCell align="right">
                    <Link to={"/board/view?bno="+row.bno}> {row.btitle} </Link>
                  </TableCell>
                  <TableCell align="right">{row.mno}</TableCell>
                  <TableCell align="right">{row.cdate}</TableCell>
                  <TableCell align="right">{row.bview}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
        <div style = {{ display : 'flex' , justifyContent : 'center' }} >
            { /* count : 전체페이지수   onChange : 페이지번호를 클릭/변경 했을떄 이벤트 */}
            <Pagination count={ pageDto.totalPages } onChange={ onPageSelect } />
        </div>

    </>)
}
/*
    let rows = [
        { bno : 1 , btitle : '안녕1' , mno : 1 , cdate : '2023-11-02' , bview : 0 } ,
        { bno : 2 , btitle : '안녕2' , mno : 1 , cdate : '2023-11-02' , bview : 0 } ,
        { bno : 3 , btitle : '안녕3' , mno : 1 , cdate : '2023-11-02' , bview : 0 } ,
        { bno : 4 , btitle : '안녕4' , mno : 1 , cdate : '2023-11-02' , bview : 0 } ,
        { bno : 5 , btitle : '안녕5' , mno : 1 , cdate : '2023-11-02' , bview : 0 } ,
        { bno : 6 , btitle : '안녕6' , mno : 1 , cdate : '2023-11-02' , bview : 0 } ,
    ];







            <div>
                <h3> 게시물 목록 </h3>
                <a href="/board/write">글쓰기</a>
                <table>
                    <tr>
                            <th>번호</th>  <th>제목</th> <th>작성자</th>
                            <th>작성일</th> <th>조회수</th>
                    </tr>
                    {
                        rows.map( (row)=>{
                            return(<>
                                <tr>
                                    <td>{ row.bno }</td>  <td>{ row.btitle }</td>
                                    <td>{ row.mno }</td>  <td>{ row.cdate }</td>
                                    <td>{ row.bview }</td>
                                </tr>
                            </>)
                        })
                    }
                </table>
            </div>

*/