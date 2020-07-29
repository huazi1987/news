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


$("#addTheme").on('click', function(e) {
	showModal('add','');
})

function editTheme(rowid){
	showModal('edit', rowid);
}

function themeModalHide(){
	$('#themeModal').modal('hide');
}

function saveTheme()
{
	$('#themefrm').submit();
}



var fileRules =  {
    id : {required: false},
    filename : {required: true},
    path : {required: true},
    size: {required: true}
}


submitForm($('#themefrm'),fileRules,function (form) {
    var uri = ''
    if($("#theme_id").val() == ''){
        uri = '/cms/theme/add'
    }else{
        uri = '/cms/file/modify'
    }
	var formData = new FormData($("#themefrm")[0]);
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
            	themeModalHide();
            	$("#themeGrid").trigger('reloadGrid');
            } else if (result.status == 'false') {
                error(result.msg);
            }
        } 
   });
})


function showModal(type, rowid) {
    $('#themefrm')[0].reset();
    $('#themefrm').validate().resetForm();
    $('.form-group').removeClass('has-error');
    if (type == 'add') {
        $('#themeTitle').html('<@spring.message code="button.add"/>');
        $("#theme_id").val('');
    } else if (type == 'edit') {
        $('#themeTitle').html('<@spring.message code="button.edit"/>');
        var rowData = $("#themeGrid").getRowData(rowid);
        $("#theme_id").val(rowData.id);
        $("#theme_themeName").val(rowData.themeName);
    }

    $("#themeModal").modal('show');
}


function deleteTheme(rowid) {
    confirm('删除主题','确定要删除该条记录？',function(result) {
        if(result){
            $.post(contextPath+"/cms/theme/delete",{id:String(rowid)}, function (data) {
                result = eval('(' + data + ')')
                if (result.status == 'true') {
                    $("#themeGrid").trigger("reloadGrid");
                } else if (result.status == 'false') {
                    error(result.msg);
                }
            });
        }
    })
}