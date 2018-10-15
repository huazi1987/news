
$("#addVersion").on('click', function(e) {
	showModal('add','');
})

function editVersion(rowid){
	showModal('edit',rowid);
}

function versionModalHide(){
	$('#versionModal').modal('hide');
}

function saveVersion()
{
	$('#versionfrm').submit();
}

var versionRules =  {
	id : {required: false},
    version : {required: true},
    osType : {required: true}
}

submitForm($('#versionfrm'),versionRules,function (form) {
	var formData = new FormData($("#versionfrm")[0]);
	var uri = '';
    if($("#version_id").val() == ''){
        uri = '/cms/version/add'
    }else{
        uri = '/cms/version/modify'
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
            	versionModalHide();
            	$("#versionGrid").trigger('reloadGrid');
            } else if (result.status == 'false') {
                error(result.msg);
            }
        } 
   });
})

function deleteVersion(rowid) {
    var rowData = $("#fileGrid").getRowData(rowid);
    confirm('删除版本','确定要删除该条记录？',function(result) {
        if(result){
            $.post(contextPath+"/cms/version/delete",{id:rowData.id}, function (data) {
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
