console.log('doto.js open')

function doPost(){
    console.log("doPost method")
    $.ajax({
        url : "/day04/todo" , method : "post",
        data : { tcontent : document.querySelector(".tcontent").value} ,
        success : r => { console.log(r); }
    })
}
doGet();
function doGet(){
    console.log("doGet method")
    $.ajax({
        url : "/day04/todo" , method : "get",
        data : {} ,
        success : r => { console.log(r); }
    })
}
function doPut(){
    console.log("doPut method")
    $.ajax({
        url : "/day04/todo" , method : "put",
        data : { tno : 3   } ,
        success : r => { console.log(r); }
    })
}
function doDelete(){
    console.log("doDelete method")
    $.ajax({
        url : "/day04/todo" , method : "delete",
        data : { tno : 3 } ,
        success : r => { console.log(r); }
    })
}
