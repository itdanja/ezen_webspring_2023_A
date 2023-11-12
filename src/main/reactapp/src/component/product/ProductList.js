import axios from 'axios';
import { useEffect , useState } from 'react'
// ------------ mui table 관련 컴포넌트 import --------------- //
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';


import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';



export default function ProductList( props ){

    // 1.
    const [ productList , setProductList ] = useState([]);
    const printProduct = (e  )=>{
        axios.get( '/product'   )
            .then( r => { setProductList( r.data ); console.log( r.data ); })
    }
    useEffect( ()=>{ printProduct()  } , [])


      const [open, setOpen] = useState(false);
      const [ product , setProduct ] = useState({});

      const handleClickOpen = ( e , p , type )=> {
        setOpen(true);
        setProduct( {...p , 'type' : type } );
      };

      const handleClose = () => {
        setOpen(false);
      };

      const onDelete = () =>{
             axios.delete( '/product' , { params: { pno : product.pno } }   )
                    .then( r => { setOpen(false); printProduct(); })
      }
      const onUpdate = ()=>{
            axios.put( '/product' , product   )
                    .then( r => { setOpen(false); printProduct(); })
      }

    return(<>
        <h3> 제품 목록 </h3>
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            {/*테이블 제목 구역*/}
            <TableHead>
              <TableRow>
                <TableCell style={{ width : '10%' }} align="center">번호</TableCell>
                <TableCell style={{ width : '10%' }} align="center">대표이미지</TableCell>
                <TableCell style={{ width : '10%' }} align="center">카테고리</TableCell>
                <TableCell style={{ width : '10%' }} align="center">제품명</TableCell>
                <TableCell style={{ width : '10%' }} align="center">가격</TableCell>
                <TableCell style={{ width : '10%' }} align="center">상태</TableCell>
                <TableCell style={{ width : '10%' }} align="center">재고</TableCell>
                <TableCell style={{ width : '10%' }} align="center">비고</TableCell>
              </TableRow>
            </TableHead>
             {/*테이블 내용 구역*/}
            <TableBody>

                  {
                    productList.map( (p)=>{
                        return (<>
                               <TableRow   sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                                   <TableCell align="center"> { p.pno} </TableCell>
                                   <TableCell align="center">
                                       <img style={{ width:'100%'}} src={ 'http://localhost:80/static/media/'+p.imgList[0].uuidFileName } />
                                   </TableCell>
                                   <TableCell align="center">{p.categoryDto.pcname}</TableCell>
                                   <TableCell align="center">{p.pname} </TableCell>
                                   <TableCell align="center">{p.pprice.toLocaleString()}</TableCell>
                                   <TableCell align="center">{p.pstate}</TableCell>
                                   <TableCell align="center">{p.pstock}</TableCell>
                                   <TableCell align="center">

                                        <button onClick={ (e)=> handleClickOpen( e , p , 1 ) } > 변경 </button>
                                        <button onClick={ (e)=> handleClickOpen( e , p , 2 ) } > 삭제 </button>

                                   </TableCell>
                               </TableRow>
                           </>)
                    })
                  }

            </TableBody>
          </Table>
        </TableContainer>


          <Dialog
            open={open}
            onClose={handleClose}
          >
            <DialogTitle id="alert-dialog-title">
              {product.pno} 제품 { product.type == 1 ? '변경' : '삭제' }
            </DialogTitle>
            <DialogContent>
              <DialogContentText id="alert-dialog-description">

                { product.type == 1 ? (<>
                    <input type="text" value={product.pname} onChange={ (e)=>{  setProduct( { ...product , pname : e.target.value } )  } }/> <br/>
                    <input type="text" value={product.pprice} onChange={ (e)=>{  setProduct( { ...product , pprice : e.target.value } )  } }/> <br/>
                    <input type="text" value={product.pstate} onChange={ (e)=>{  setProduct( { ...product , pstate : e.target.value } )  } }/> <br/>
                    <input type="text" value={product.pstock} onChange={ (e)=>{  setProduct( { ...product , pstock : e.target.value } )  } }/> <br/>
                 </>) : '정말 삭제 하시겠습니까?' }
              </DialogContentText>
            </DialogContent>
            <DialogActions>
                { product.type == 1 ? (<> <Button onClick={onUpdate}> 수정하기 </Button> </>) : (<> <Button onClick={onDelete}> 삭제하기 </Button> </>) }
              <Button onClick={handleClose} autoFocus> 닫기 </Button>
            </DialogActions>
          </Dialog>

    </>)
}