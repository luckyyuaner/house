var showImgs=function(obj){
    var file = obj.files;
    for(var i = 0; i<file.length; i ++) {
        var reader    = new FileReader();
        reader.readAsDataURL(file[i]);
        reader.onload=function(e){
            $('#imgs').append('<img src="'+this.result+'" height="300" width="240" alt="照片" style="margin-left:10px;"/>&nbsp;&nbsp;');
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