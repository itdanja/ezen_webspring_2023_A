import axios from 'axios';
import { useState , useEffect } from 'react';

/* ---------table mui -------- */
// npm install @mui/material @emotion/react @emotion/styled
import Table from '@mui/material/Table'; // @mui/material/Table
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
/* ---------------------------*/
import Container from '@mui/material/Container';

export default function BoardList( props ){

    let [ rows , setRows ] = useState( [] )

    useEffect( ()=>{
        axios.get('/board')
            .then( r => { console.log(r);
                setRows( r.data )  // 응답받은 게시물 리스트 대입
            } )
            .catch( err => { console.log(err); })
    } , [] ) // pageInfo( cno , page ) 변경될때마다 해당 useEffect 실행된다.

    return(<>
        <div >
            <h3> 게시물 목록 </h3>
            <a href="/board/write">글쓰기</a>

                <Container>
                    <TableContainer component={Paper}>
                      <Table sx={{ minWidth: 650 }} aria-label="simple table">
                        <TableHead>
                          <TableRow>
                            <TableCell align="center" style={{ width:'10%' }}>번호</TableCell>
                            <TableCell align="center" style={{ width:'60%' }}>제목</TableCell>
                            <TableCell align="center" style={{ width:'10%' }}>작성자</TableCell>
                            <TableCell align="center" style={{ width:'10%' }}>작성일</TableCell>
                            <TableCell align="center" style={{ width:'10%' }}>조회수</TableCell>
                          </TableRow>
                        </TableHead>
                        <TableBody>
                          {rows.map((row) => (
                            <TableRow  sx={{ '&:last-child td, &:last-child th': { border: 0 } }}  >
                              <TableCell component="th" scope="row"> {row.bno} </TableCell>
                              <TableCell align="left">{row.btitle}</TableCell>
                              <TableCell align="center">{row.mname}</TableCell>
                              <TableCell align="center">{row.cdate}</TableCell>
                              <TableCell align="center">{row.bview}</TableCell>
                            </TableRow>
                          ))}
                        </TableBody>
                      </Table>
                    </TableContainer>
                </Container>

        </div>
    </>)
}
