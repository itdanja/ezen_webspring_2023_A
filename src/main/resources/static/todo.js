console.log('todo.js open')

/*
    1. @RequestBody x
    2.  ---> @RequestBody

        - AJAX
            전송
                형식 : 모양
                    JSON.stringify( ) : JSON타입 -> 문자열
                    JSON.parse( ) : 문자열 -> JSON타입

                타입 : 자료형 [ application/x-www-form-urlencoded ]
                    application/json


                "{ AA:'안녕' }" : JSON형식이지만 타입은 " " ㅇ있어서 문자열타입

*/

// 1. POST AJAX [ ]
function doPost(){
    $.ajax({
        url: '/todo',
        type: 'POST',
        contentType: 'application/json;',
        data: JSON.stringify( {
            tcontent : document.querySelector('.tcontent').value,
            tstate : false
        }) ,
        success: function(data){
            if( data )doGet();
        },
    });
}
// 2. GET AJAX
doGet();
function doGet(){
    $.ajax({
        url: '/todo',
        type: 'GET',
        data: {},
        success: function(data){
             let html = ``;
            data.forEach( (item)=>{
                html += `            <div class="todo ${ item.tstate ? 'successTodo' : '' }">
                                         <div class="tcontent"> ${ item.tcontent } </div>
                                         <div class="etcbtns">
                                             <button onclick="doPut( ${ item.tno },${ item.tstate })" type="button">상태변경</button>
                                             <button onclick="doDelete(${ item.tno })" type="button">제거하기</button>
                                         </div>
                                     </div>`;
            })
            document.querySelector('.todo_bottom').innerHTML = html;
        },
    });
}
// 3. PUT  AJAX
function doPut( tno , tstate ){
    $.ajax({
        url: '/todo',
        type: 'PUT',
        data: JSON.stringify( {
            tno : tno ,
            tstate : !tstate
        }),
        contentType: 'application/json;',
        success: function(data){
            if( data )doGet();
        },
    });
}
// 4. DELETE  AJAX
function doDelete( tno ){
    $.ajax({
        url: '/todo',
        type: 'DELETE',
        data:  { tno : tno},
        success: function(data){
            if( data )doGet();
        },
    });
}
