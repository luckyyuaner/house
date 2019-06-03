var showImgs=function(obj){
    var file = obj.files;
    $('#img-box').append('<img src ="" height="300" width="200" style="visibility: hidden;" />');
    for(var i = 0; i<file.length; i ++) {
        var reader    = new FileReader();
        reader.readAsDataURL(file[i]);
        reader.onload=function(e){
            $('#img-box').append('<img src="'+this.result+'" height="300" width="320" alt="照片" style="opacity: 1;margin-left:10px;"/>&nbsp;&nbsp;');
        }
    }
}

var checkHouse = function(){
    var arr = document.getElementsByClassName("kind-value");
    var len = arr.length;
    var val = "";
    for(var i=0;i<len;i++){
        val += arr[i].value;
    }
    document.getElementsByName("kind")[0].value = val;
}