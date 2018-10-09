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

$("#userSearch").on('click', function(e) {
	var s_userid = $("#s_userid").val();
	var s_nickname = $('#s_nickname').val();
	var s_email = $('#s_email').val();
	var s_userType = $('#s_userType').val();
	$("#userGrid").jqGrid('setGridParam', {
		url : contextPath + '/showuser/query',
		postData : {
			'userId':s_userid,
			'nickName':s_nickname,
			'email':s_email,
			'userType':s_userType
			},
	    page : 1
	}).trigger("reloadGrid");
})

$("#userRefresh").on('click', function(e) {
	$("#s_userid").val('');
	$('#s_nickname').val('');
	$('#s_itemid').val('');
	$('#s_topic').val('');
	$('#s_userType').val('');
	$('#s_status').val('');
	$('#s_auditStatus').val('');
	$('#s_recommendToTopic').val('');
	$('#s_isTop').val('');
	$('#s_startTime').val('');
	$('#s_endTime').val('');
	$('#s_tagId').selectpicker('val','');
	$('#s_categoryId').selectpicker('val','');
	$('#itemSearch').click();
})

function formatItemTags(cellValue, options, rowObject) {
	var tagNames = '';
	if(rowObject.tagIds!=null && rowObject.tagIds!=''){
		$.each(tagList, function (i, n) {
			if(rowObject.tagIds.indexOf(n.tagId) >= 0){
				tagNames += n.tagName+',';
            }
        })
	}
	if(tagNames.length>0){
		tagNames = tagNames.substring(0,tagNames.length-1)
    }
	return tagNames;
}

function showTagModal(rowid){
	var rowData = $("#itemGrid").getRowData(rowid);
	var tag = rowData.tagIds.replace('[','').replace(']','').split(',');
	$('#itemBindTag_tags').selectpicker('val',tag);
	$('#itemBindTagModalSave').attr('onclick','saveBindTag("'+rowid+'")');
	$("#itemBindTagModal").modal('show');
}

function itemBindTagModalHide(){
	$("#itemBindTagModal").modal('hide');
}

function saveBindTag(rowid){
	var rowData = $("#itemGrid").getRowData(rowid);
	var tags = $('#itemBindTag_tags').selectpicker('val');
	if(tags && tags.length>0){
		var tagIds = '';
		$.each(tags, function (i, n) {
			tagIds += n+',';
        })
        if(tagIds.length>0){
	    	tagIds = tagIds.substring(0,tagIds.length-1)
	    }
		$.post(contextPath+"/showtag/bindtag",{itemId:rowData.itemId,createTime:rowData.sortKey,tagIds:tagIds},function(data) {
			if (data) {
				var result = eval('(' + data + ')')
				if(result.status=='true'){
					$("#itemGrid").setRowData(rowid,{tagIds:'['+tags+']',itemTags:'['+tags+']'});
					itemBindTagModalHide();     
				}else{
					error(result.msg);
				}
			}
		})
	}
}

function modifyStatus(rowid, status) {
	var rowData = $("#itemGrid").getRowData(rowid);
	$.get(contextPath+"/showitem/modifyStatus",{itemKeys:rowData.itemId+'_'+rowData.sortKey,status:status}, function(data) {
        if (data && data == true) {
        	$("#itemGrid").setRowData(rowid,{status:status,statusFormat:status,operat:status});
        }
    })
}

$("#userExportExcel").on('click', function(e) {
	var s_userid = $("#s_userid").val();
	var s_nickname = $('#s_nickname').val();
	var s_itemid = $('#s_itemid').val();
	var s_topic = $('#s_topic').val();
	var s_userType = $('#s_userType').val();
	var s_status = $('#s_status').val();
	var s_auditStatus = $('#s_auditStatus').val();
	var s_startTime = $('#s_startTime').val();
	var s_endTime = $('#s_endTime').val();
	
    var params ="userId=" +s_userid+"&nickName="+s_nickname;
    params+="&itemId="+s_itemid+"&topic="+s_topic;
    params+="&status="+s_status+"&startTime="+s_startTime+"&endTime="+s_endTime+"&auditStatus="+s_auditStatus+"&userType="+s_userType;
    
    window.location.href = contextPath+"/showitem/download?"+params;
})

$("#userAdd").on('click', function(e) {
	showModal('add','');
})

function userEdit(rowid){
	showModal('edit',rowid);
}

function userModalHide(){
	$('#userModal').modal('hide');
}

function saveUser()
{
	$('#userfrm').submit();
}

var userRules =  {
	nickName : {required: true}
}

submitForm($('#userfrm'),userRules,function (form) {
	var formData = new FormData($("#userfrm")[0]);
	$.ajax({  
        url: contextPath + '/showuser/saveUser',
        type: 'POST',  
        data: formData,
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false,  
        success: function (data) {
        	var result = eval('(' + data + ')')
            if (result.status == 'true') {
            	userModalHide();
            	$("#userGrid").trigger('reloadGrid');
            } else if (result.status == 'false') {
                error(result.msg);
            }
        } 
   });
})

function userUploadVideoModalHide(){
	$('#userUploadVideoModal').modal('hide');
}

function saveUserUploadVideo()
{
	$('#userUploadVideofrm').submit();
}

var userUploadVideoRules =  {
	nickName : {required: true}
}

submitForm($('#userUploadVideofrm'),userUploadVideoRules,function (form) {
	var formData = new FormData($("#userUploadVideofrm")[0]);
	$.ajax({  
        url: contextPath + '/showuser/saveUser',
        type: 'POST',  
        data: formData,
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false,  
        success: function (data) {
        	var result = eval('(' + data + ')')
            if (result.status == 'true') {
            	userUploadVideoModalHide();
            	$("#userGrid").trigger('reloadGrid');
            } else if (result.status == 'false') {
                error(result.msg);
            }
        } 
   });
})