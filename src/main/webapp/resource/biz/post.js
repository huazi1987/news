
$("#addpost").on('click', function(e) {
    showModal('add','');
})

function editpost(rowid){
    showModal('edit',rowid);
}

function postModalHide(){
    $('#postModal').modal('hide');
}

function savepost()
{
    $('#postfrm').submit();
}

var postRules =  {
    id : {required: false},
    title : {required: true},
    content : {required: true},
    thumbnailUrl : {required: true},
}

submitForm($('#postfrm'),postRules,function (form) {
    // 取得HTML内容
    var html = editor.html();
    editor.sync();
    html = $('#editor_id').val(); // jQuery

// 设置HTML内容
    editor.html('HTML内容');
    var formData = new FormData($("#postfrm")[0]);
    var uri = '';
    if($("#post_id").val() == ''){
        uri = '/cms/post/add'
    }else{
        uri = '/cms/post/modify'
    }
    $.ajax({
        url: contextPath + uri,
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            var result = eval('(' + data + ')')
            if (result.status == 'true') {
                postModalHide();
                $("#postGrid").trigger('reloadGrid');
            } else if (result.status == 'false') {
                error(result.msg);
            }
        }
    });
})


