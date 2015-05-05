function createAjaxGetRequest(path,successFunc, errorFunc){
    $.ajax({
       type: "GET",
       url: path,
       contentType: 'application/json',
       success: successFunc,
       error: errorFunc
   });
}

function createAjaxPostRequest(path,successFunc, errorFunc){
    $.ajax({
               type: "POST",
               url: path,
               contentType: 'application/json',
               success: successFunc,
               error: errorFunc
           });
}