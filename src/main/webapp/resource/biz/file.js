$('#s_startTime').datetimepicker({
	format: 'yyyy/mm/dd hh:ii'
})

$('#s_endTime').datetimepicker({
	format: 'yyyy/mm/dd hh:ii'
}).on("click",function(){
    $("#s_endTime").datetimepicker("setStartDate",$("#s_startTime").val())
})

$('.selectpicker').selectpicker({
    'selectedText': 'cat',
    'width':'100%'
})


$("#addFile").on('click', function(e) {
	showModal('add','');
})

function editFile(rowid){
	showModal('edit', rowid);
}

function fileModalHide(){
	$('#fileModal').modal('hide');
}

function saveFile()
{
	$('#filefrm').submit();
}



var fileRules =  {
    id : {required: false},
    filename : {required: true},
    path : {required: true},
    size: {required: true}
}


submitForm($('#filefrm'),fileRules,function (form) {
    var uri = ''
    if($("#file_id").val() == ''){
        uri = '/cms/file/add'
    }else{
        uri = '/cms/file/modify'
    }
	var formData = new FormData($("#filefrm")[0]);
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
            	fileModalHide();
            	$("#fileGrid").trigger('reloadGrid');
            } else if (result.status == 'false') {
                error(result.msg);
            }
        } 
   });
})


function showModal(type, rowid) {
    $('#filefrm')[0].reset();
    $('#filefrm').validate().resetForm();
    $('.form-group').removeClass('has-error');
    var fileUrl = '';
    if (type == 'add') {
        $('#fileTitle').html('<@spring.message code="button.add"/>');
        $("#file_id").val('');
    } else if (type == 'edit') {
        $('#fileTitle').html('<@spring.message code="button.edit"/>');
        var rowData = $("#fileGrid").getRowData(rowid);
        $("#file_id").val(rowData.id);
        $("#file_filename").val(rowData.filename);
        $("#file_size").val(rowData.size);
        $("#file_path").val(rowData.path);
        fileUrl=rowData.path
    }

    $('#fileUrlDiv').html('<input id="fileInput" name="downloadFile" type="file" class="file fileUrl"/>');

    initFile(fileUrl);

    $('#fileUrlDiv').find(".file-caption").text(fileUrl);

    $("#fileModal").modal('show');
}


function initFile(imgUrl) {
    $("#fileInput").fileinput({
        theme : 'explorer-fa',
        language : $.cookie('W_LANG') == 'zh_CN' ? 'zh' : '',
        overwriteInitial : false,
        showRemove : false,
        initialPreviewAsData : true,
        initialPreview : [ imgUrl ],
        uploadUrl : contextPath + '/cms/file/uploadFile',
        allowedFileExtensions : [ 'jpg', 'png', 'jpeg', 'doc', 'xls', 'mp4', 'mov','json','xml','mp3'],
        maxFileSize : 50000,
        maxFileCount : 1
    });

    $(this).on('filepreupload',function(event, data, previewId, index,jqXHR) {
        // data.form.append("key", $('#itemEdit_key').val());
    });

    $("#fileInput").on('fileuploaded',function(event, data, previewId, index) {
        if (data.response.status == "true") {
            $('#file_path').val(data.response.url)
            $('#file_size').val(data.response.size)
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

    $("#fileInput").on('change',function() {
        var parent = $(this).parents(".file-input");
        $(parent).find(".file-error-message").css("display", "block").html('请点击“上传“按钮，上传文件！\n' +
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