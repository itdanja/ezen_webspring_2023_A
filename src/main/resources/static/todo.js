console.log('todo.js open')

function doPost(){
    console.log("doPost method")
    $.ajax({
        url : "/todo" , method : "post",
        data : JSON.stringify( { tcontent : document.querySelector(".tcontent").value } ) ,
        contentType : "application/json" ,
        success : res => {console.log(res);if( res ) doGet();
        }
    })
}
doGet();
function doGet(){
    console.log("doGet method")
    $.ajax({
        url : "/todo" , method : "get",
        data : {} ,
        success : res => { console.log(res);

            let html = ``;

            res.forEach( ( obj )=>{

                html += `            <div class="todo ${ obj.tstate ? "" : "successTodo"  }">
                                         <div class="tcontent"> ${ obj.tcontent } </div>
                                         <div class="etcbtns">
                                             <button onclick="doPut( ${ obj.tno } , ${ obj.tstate } )" type="button">상태변경</button>
                                             <button onclick="doDelete( ${ obj.tno } )" type="button">제거하기</button>
                                         </div>
                                     </div>`;

            })

            document.querySelector('.todo_bottom').innerHTML = html;

        }
    })
}
function doPut( tno , tstate ){
    console.log("doPut method")
    $.ajax({
        url : "/todo" , method : "put",
        data : JSON.stringify( { tno : tno  , tstate : !tstate } ) ,
        contentType : "application/json" ,
        success : res   => { console.log(res); if( res ) doGet();}
    })
}
function doDelete( tno ){
    console.log("doDelete method")
    $.ajax({
        url : "/todo" , method : "delete",
        data : { tno : tno } ,
        success : res  => { console.log(res); if( res ) doGet(); }
    })
}