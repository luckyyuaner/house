var showImgs2=function(obj){
    var file = obj.files;
    $('#img-box2').append('<img src ="" height="360" width="200" style="visibility: hidden;" />');
    for(var i = 0; i<file.length; i ++) {
        var reader    = new FileReader();
        reader.readAsDataURL(file[i]);
        reader.onload=function(e){
            $('#img-box2').append('<img src="'+this.result+'" height="360" width="320" alt="照片" style="opacity: 1;margin-left:10px;"/>&nbsp;&nbsp;');
        }
    }
}
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
var showImg3=function (obj) {
    var url=getFileUrl(obj);
    $('#img-box3').append('<img src="'+url+'" height="300" width="320" alt="照片" style="opacity: 1;margin-top:20px;margin-left:400px;"/>&nbsp;&nbsp;');
}

var showImgs4=function(obj){
    var file = obj.files;
    $('#img-box4').append('<img src ="" height="360" width="200" style="visibility: hidden;" />');
    for(var i = 0; i<file.length; i ++) {
        var reader    = new FileReader();
        reader.readAsDataURL(file[i]);
        reader.onload=function(e){
            $('#img-box4').append('<img src="'+this.result+'" height="360" width="320" alt="照片" style="opacity: 1;margin-left:5px;"/>');
        }
    }
}