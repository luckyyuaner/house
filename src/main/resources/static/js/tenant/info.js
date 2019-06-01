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
var showHead=function (obj) {
    var url=getFileUrl(obj);
    var img=new Image();
    img.src=url;
    $('#head-img').attr("src",url);
    $('#head-img').css('display','block');
}

var showCard=function (obj) {
    var url=getFileUrl(obj);
    var img=new Image();
    img.src=url;
    $('#card-img').attr("src",url);
    $('#card-img').css('display','block');
}