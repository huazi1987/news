
$("#addVideo").on('click', function(e) {
    showModal('add','');
})

function editVideo(rowid){
    showModal('edit', rowid);
}

function videoModalHide(){
    $('#videoModal').modal('hide');
}

function saveVideo()
{
    $('#videofrm').submit();
}


var videRules =  {
    id : {required: false},
    title : {required: true},
    contentUrl : {required: false},
    thumbnailUrl: {required: true},
    description: {required: true},
    width: {required: false},
    height: {required: false},
    size: {required: false},
    duration: {required: false}
}


submitForm($('#videofrm'),videRules,function (form) {
    var uri = ''
    if($("#video_id").val() == ''){
        uri = '/cms/video/add'
    }else{
        uri = '/cms/video/modify'
    }
    var formData = new FormData($("#videofrm")[0]);
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
                videoModalHide();
                $("#videoGrid").trigger('reloadGrid');
            } else if (result.status == 'false') {
                error(result.msg);
            }
        }
    });
})


function showModal(type, rowid) {
    $('#videofrm')[0].reset();
    $('#videofrm').validate().resetForm();
    $('.form-group').removeClass('has-error');
    var contentUrl = '';
    var thumbnailUrl = '';
    if (type == 'add') {
        $('#videoTitle').html('<@spring.message code="button.add"/>');
        $("#video_id").val('');
        $("#video_title").val('');
        $("#video_description").val('');
        $("#video_contentUrl").val('');
        $("#video_height").val('');
        $("#video_width").val('');
        $("#video_thumbnailUrl").val('');
        $("#video_size").val('');
        $("#video_duration").val('');
    } else if (type == 'edit') {
        $('#videoTitle').html('<@spring.message code="button.edit"/>');
        var rowData = $("#videoGrid").getRowData(rowid);
        $("#video_id").val(rowData.id);
        $("#video_title").val(rowData.title);
        $("#video_description").val(rowData.desc);
        $("#video_contentUrl").val(rowData.contentUrl);
        $("#video_height").val(rowData.height);
        $("#video_width").val(rowData.width);
        $("#video_thumbnailUrl").val(rowData.thumbnailUrl);
        $("#video_size").val(rowData.size);
        $("#video_duration").val(rowData.duration);
        contentUrl=rowData.contentUrl
        thumbnailUrl=rowData.thumbnailUrl
    }

    $('#videoContent').html('<input id="videoContentUrl" name="itemVideo" type="file" class="file contentUrl"/>');
    $('#videoThumbnail').html('<input id="videoThumbnailUrl" name="itemVideoThumb" type="file" class="file thumbnailuRL"/>');


    initVideoContent(contentUrl);

    initVideoThumbnail(thumbnailUrl);

    $('#videoContentUrl').find(".file-caption").text(contentUrl);
    $('#videoThumbnailUrl').find(".file-caption").text(thumbnailUrl);

    $("#videoModal").modal('show');
}


function initVideoContent(contentUrl) {
    $("#videoContentUrl").fileinput({
        theme : 'explorer-fa',
        language : $.cookie('W_LANG') == 'zh_CN' ? 'zh' : '',
        overwriteInitial : false,
        showRemove : false,
        initialPreviewAsData : true,
        initialPreview : [ contentUrl ],
        uploadUrl : contextPath + '/cms/video/uploadVideo',
        allowedFileExtensions : [ 'mp4', 'mov'],
        maxFileSize : 50000,
        maxFileCount : 1
    });

    $(this).on('filepreupload',function(event, data, previewId, index,jqXHR) {
        // data.form.append("key", $('#itemEdit_key').val());
    });

    $("#videoContentUrl").on('fileuploaded',function(event, data, previewId, index) {
        if (data.response.status == "true") {
            $('#video_contentUrl').val(data.response.url)
            $('#video_size').val(data.response.size)
            $('#video_width').val(data.response.width)
            $('#video_height').val(data.response.height)
            $('#video_duration').val(data.response.duration)
            var parent = $(this).parent().parent().parent().parent();
            $(parent).find(".file-thumb-progress").remove();
            $(parent).find(".file-caption-name").text(data.response.url);
            $(parent).find(".file-caption").text(data.response.url);
        }
        if (data.response.status == "false") {
            error(data.response.msg);
            var parent = $(this).parent().parent().parent().parent();
            $(parent).find(".file-thumb-progress").remove();
        }
    });

    $("#videoContentUrl").on('change',function() {
        var parent = $(this).parents(".file-input");
        $(parent).find(".file-error-message").css("display", "block").html('请点击“上传“按钮，上传视频！\n' +
            '由于上传到国外服务器，时间过长请耐心等待！\n' +
            '进度条消失后在继续操作，谢谢合作！');
    });
}

function initVideoThumbnail(imgUrl) {
    $("#videoThumbnailUrl").fileinput({
        theme : 'explorer-fa',
        language : $.cookie('W_LANG') == 'zh_CN' ? 'zh' : '',
        overwriteInitial : false,
        showRemove : false,
        initialPreviewAsData : true,
        initialPreview : [ imgUrl ],
        uploadUrl : contextPath + '/cms/video/uploadVideoThumbnail',
        allowedFileExtensions : [ 'jpg', 'png', 'jpeg'],
        maxFileSize : 500,
        maxFileCount : 1
    });

    $(this).on('filepreupload',function(event, data, previewId, index,jqXHR) {
        // data.form.append("key", $('#itemEdit_key').val());
    });

    $("#videoThumbnailUrl").on('fileuploaded',function(event, data, previewId, index) {
        if (data.response.status == "true") {
            $('#video_thumbnailUrl').val(data.response.url)
            var parent = $(this).parent().parent().parent().parent();
            $(parent).find(".file-thumb-progress").remove();
            $(parent).find(".file-caption-name").text(data.response.url);
            $(parent).find(".file-caption").text(data.response.url);
        }
        if (data.response.status == "false") {
            error(data.response.msg);
            var parent = $(this).parent().parent().parent().parent();
            $(parent).find(".file-thumb-progress").remove();
        }
    });

    $("#videoThumbnailUrl").on('change',function() {
        var parent = $(this).parents(".file-input");
        $(parent).find(".file-error-message").css("display", "block").html('请点击“上传“按钮，上传图片！\n' +
            '由于上传到国外服务器，时间过长请耐心等待！\n' +
            '进度条消失后在继续操作，谢谢合作！');
    });
}

function deleteFile(rowid) {
    var rowData = $("#fileGrid").getRowData(rowid);
    confirm('删除文件','确定要删除该条记录？',function(result) {
        if(result){
            $.post(contextPath+"/cms/file/delete",{id:rowData.id}, function (data) {
                result = eval('(' + data + ')')
                if (result.status == 'true') {
                    $("#fileGrid").trigger("reloadGrid");
                } else if (result.status == 'false') {
                    error(result.msg);
                }
            });
        }
    })
}