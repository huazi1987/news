
$("#addNews").on('click', function(e) {
	showModal('add','');
})

function editNews(rowid){
	showModal('edit',rowid);
}

function newsModalHide(){
	$('#newsModal').modal('hide');
}

function saveNews()
{
	$('#newsfrm').submit();
}

var newsRules =  {
	id : {required: false},
    title : {required: true},
    content : {required: true},
    thumbnailUrl : {required: true},
}

submitForm($('#newsfrm'),newsRules,function (form) {
	var formData = new FormData($("#newsfrm")[0]);
	var uri = '';
    if($("#news_id").val() == ''){
        uri = '/cms/news/add'
    }else{
        uri = '/cms/news/modify'
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
            	newsModalHide();
            	$("#newsGrid").trigger('reloadGrid');
            } else if (result.status == 'false') {
                error(result.msg);
            }
        } 
   });
})

function deleteNews(rowid) {
    var rowData = $("#newsGrid").getRowData(rowid);
    confirm('删除版本','确定要删除该条记录？',function(result) {
        if(result){
            $.post(contextPath+"/cms/news/delete",{id:rowData.id}, function (data) {
                result = eval('(' + data + ')')
                if (result.status == 'true') {
                    $("#newsGrid").trigger("reloadGrid");
                } else if (result.status == 'false') {
                    error(result.msg);
                }
            });
        }
    })
}
