
var getFileUrl=function(obj){
    var url;
    if(navigator.userAgent.indexOf("MSIE")>=1){ // IE
        url=obj.value;
    }
    else{ // Firefox,Chrome
        url=window.URL.createObjectURL(obj.files.item(0));
    }
    return url;
}
var showImg=function (obj) {
    var url=getFileUrl(obj);
    $('#img-box').append('<img src="'+url+'" height="300" width="320" alt="照片" style="opacity: 1;margin-top:20px;margin-left:400px;"/>&nbsp;&nbsp;');
}