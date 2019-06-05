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
