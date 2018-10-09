$('#s_startTime').datetimepicker({
	format: 'yyyy/mm/dd hh:ii'
});

$('#s_endTime').datetimepicker({
	format: 'yyyy/mm/dd hh:ii'
}).on("click",function(){
    $("#s_endTime").datetimepicker("setStartDate",$("#s_startTime").val())
});

$('.selectpicker').selectpicker({
    'selectedText': 'cat',
    'width':'100%'
});

$('#itemDynamicPic_start').ace_spinner({
	value:1,
	min:1,
	max:9999,
	step:1, 
	on_sides: true, 
	icon_up:'ace-icon fa fa-plus smaller-75', 
	icon_down:'ace-icon fa fa-minus smaller-75', 
	btn_up_class:'btn-success', 
	btn_down_class:'btn-danger'
});

$("#itemSearch").on('click', function(e) {
	var s_userid = $("#s_userid").val();
	var s_nickname = $('#s_nickname').val();
	var s_itemid = $('#s_itemid').val();
	var s_topic = $('#s_topic').val();
	var s_userType = $('#s_userType').val();
	var s_status = $('#s_status').val();
	var s_auditStatus = $('#s_auditStatus').val();
	var s_recommendToTopic = $('#s_recommendToTopic').val();
	var s_isTop = $('#s_isTop').val();
	var s_startTime = $('#s_startTime').val();
	var s_endTime = $('#s_endTime').val();
	var s_tagId = $('#s_tagId').val();
	var s_categoryId = $('#s_categoryId').val();
	$("#itemGrid").jqGrid('setGridParam', {
		url : contextPath + '/showitem/query',
		postData : {
			'userId':s_userid,
			'nickName':s_nickname,
			'itemId':s_itemid,
			'topic':s_topic,
			'userType':s_userType,
			'status':s_status,
			'auditStatus':s_auditStatus,
			'recommendToTopic':s_recommendToTopic,
			'isTop':s_isTop,
			'startTime':s_startTime,
			'endTime':s_endTime,
			'tagIds':s_tagId,
			'categoryIds':s_categoryId
			},
	    page : 1
	}).trigger("reloadGrid");
	if(s_topic){
		$.get(contextPath+"/showitem/queryUserCount",{topic:s_topic},function(data) {
			if (data) {
				var result = eval('(' + data + ')')
				if(result.status=='true'){
					$("#s_userCount").html(result.count);
					$("#s_userCountDiv").css("display","block");
				}
			}
		})
	}else{
		$("#s_userCount").html(0);
		$("#s_userCountDiv").css("display","none");
	}
});

$("#itemRefresh").on('click', function(e) {
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

function formatThumbnailUrl(cellValue, options, rowObject) {
	if(rowObject.thumbnailUrl!=undefined && rowObject.thumbnailUrl!=''){
		return '<div class="ace-thumbnails"><a href="'+rowObject.thumbnailUrl+'" data-rel="colorbox"><img width="100" height="100" src="'+rowObject.thumbnailUrl+'"/></a></div>'
	}
	return '';
}

function formatDynamicWebpUrl(cellValue, options, rowObject) {
	if(rowObject.dynamicWebpUrl!=undefined && rowObject.dynamicWebpUrl!=''){
		return '<div class="ace-thumbnails"><a href="'+rowObject.dynamicWebpUrl+'" data-rel="colorbox"><img width="100" height="100" src="'+rowObject.dynamicWebpUrl+'"/></a></div>'
	}
	return '';
}

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

function formatItemCategory(cellValue, options, rowObject) {
	var categoryNames = '';
	if(rowObject.categoryIds!=null && rowObject.categoryIds!=''){
		$.each(categoryList, function (i, n) {
			if(rowObject.categoryIds.indexOf(n.categoryId) >= 0){
				categoryNames += n.categoryName +',';
            }
        })
	}
	if(categoryNames.length>0){
		categoryNames = categoryNames.substring(0,categoryNames.length-1)
    }
	return categoryNames;
}

function modifyAuditStatus(rowid) {
	var rowData = $("#itemGrid").getRowData(rowid);
	$("#itemGrid").setRowData(rowid,{auditStatus:1});
	$.get(contextPath+"/showitem/modifyAuditStatus",{itemId:rowData.itemId,createTime:rowData.sortKey,auditStatus:1});
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

function showCategoryModal(rowid){
	var rowData = $("#itemGrid").getRowData(rowid);
	var categorys = rowData.categoryIds.replace('[','').replace(']','').split(',');
	$('#itemBindCategory_categorys').selectpicker('val',categorys);
	$('#itemBindCategoryModalSave').attr('onclick','saveBindCategory("'+rowid+'")');
	$("#itemBindCategoryModal").modal('show');
}

function itemBindCategoryModalHide(){
	$("#itemBindCategoryModal").modal('hide');
}

function saveBindCategory(rowid){
	var rowData = $("#itemGrid").getRowData(rowid);
	var categorys = $('#itemBindCategory_categorys').selectpicker('val');
	if(categorys && categorys.length>0){
		var categoryIds = '';
		$.each(categorys, function (i, n) {
			categoryIds += n+',';
        })
        if(categoryIds.length>0){
        	categoryIds = categoryIds.substring(0,categoryIds.length-1);
	    }
		
		$.post(contextPath+"/showitem/bindcategory",{itemId:rowData.itemId,createTime:rowData.sortKey,categoryIds:categoryIds}, function(data) {
			if (data) {
			    var result = eval('(' + data + ')')
			    if(result.status=='true'){
			    	$("#itemGrid").setRowData(rowid,{categoryIds:'['+categorys+']',itemCategorys:'['+categorys+']'});
			    	itemBindCategoryModalHide();     
			    }else{
			    	error(result.msg);
			    }
			}
		})
	}
}

function showDynamicPicModal(rowid){
	var rowData = $("#itemGrid").getRowData(rowid);
	var pic = $('input[name="itemDynamicPic_operate"]');
	
	if(rowData.dynamicWebpUrl != '' && rowData.dynamicWebpUrl.length>0){
		$(pic[0]).prop('disabled',true);
		$(pic[1]).prop('checked',true);
    }else{
    	$(pic[0]).prop('checked',true);
    	$(pic[1]).prop('disabled',true);
		$(pic[2]).prop('disabled',true);
    }
	$('#itemDynamicPicModalSave').attr('onclick','saveDynamicPic("'+rowid+'")');
	$("#itemDynamicPicModal").modal('show');
}

function itemDynamicPicModalHide(){
	$("#itemDynamicPicModal").modal('hide');
}

function saveDynamicPic(rowid){
	var rowData = $("#itemGrid").getRowData(rowid);
	var start = $('#itemDynamicPic_start').val();
	var opert = $('input[name="itemDynamicPic_operate"]:checked').val();
	
	$.post(contextPath+"/showitem/handleWebp",{itemId:rowData.itemId,createTime:rowData.sortKey,contentUrl:rowData.contentUrl,operateType:opert,start:start,type:'webp'}, function(data) {
		if (data) {
		    var result = eval('(' + data + ')')
		    if(result.status=='true'){
		    	$("#itemGrid").trigger("reloadGrid");
		    	itemDynamicPicModalHide();
		    }else{
		    	error(result.msg);
		    }
		}
	})
}

function showRemoveTopicModal(rowid){
	var rowData = $("#itemGrid").getRowData(rowid);
	var desc = rowData.description;
    var reg = /#([^@]+?) /g;
	var mStr = desc.match(reg);
	var removeTopicHtml = '';
	for(var key in mStr){
		removeTopicHtml += '<div class="input-group col-md-4">'+
		                       '<div class="form-group" text-align:center>'+
		                       '<input type="checkbox" name="removeTopicCheckBox" '+
		                       'value="'+mStr[key]+'"/>'+mStr[key]+
		                       '</div>'+
       						'</div>';
	}
	$('#itemRemoveTopic_topicdiv').html(removeTopicHtml);
	$('#itemRemoveTopicModalSave').attr('onclick','saveRemoveTopic("'+rowid+'")');
	$("#itemRemoveTopicModal").modal('show');
}

function itemRemoveTopicModalHide(){
	$("#itemRemoveTopicModal").modal('hide');
}

function saveRemoveTopic(rowid){
	var rowData = $("#itemGrid").getRowData(rowid);
	var desc = rowData.description;
    var i = 0;
    $('input[type="checkbox"][name="removeTopicCheckBox"]:checked').each(
    	function() {
    		i++;
    		desc = desc.replace($(this).val(),"");
		}
	);
	if(i==0){
		itemRemoveTopicModalHide();
		return;
	}
	
	$.post(contextPath+"/showitem/bindRemoveTopic",{itemId:rowData.itemId,createTime:rowData.sortKey,desc:desc}, function(data) {
		if (data) {
		    var result = eval('(' + data + ')')
		    if(result.status=='true'){
		    	$("#itemGrid").setRowData(rowid,{description:desc});
		    	itemRemoveTopicModalHide();
		    }else{
		    	error(result.msg);
		    }
		}
	})
}

function modifyStatus(rowid, status) {
	var rowData = $("#itemGrid").getRowData(rowid);
	$.get(contextPath+"/showitem/modifyStatus",{itemKeys:rowData.itemId+'_'+rowData.sortKey,status:status}, function(data) {
        if (data && data == true) {
        	$("#itemGrid").setRowData(rowid,{status:status,statusFormat:status,operat:status});
        }
    })
}

function showItemEditModal(rowid){
	var rowData = $("#itemGrid").getRowData(rowid);
	
	var videoHtml=	'<video width="100%" height="240" controls="controls" autoplay="autoplay" >'+
	    				'<source  src="'+rowData.contentUrl+'" type="video/mp4">'
	    				'Your browser does not support the video tag.'
					'</video>';
					
	$('#itemEdit_mediaHeight').val(rowData.mediaHeight==''?0:rowData.mediaHeight);
	$('#itemEdit_mediaWidth').val(rowData.mediaWidth==''?0:rowData.mediaWidth);
	$('#itemEdit_description').val(rowData.description);
	var firstIndex = rowData.contentUrl.lastIndexOf('/');
	var endIndex = rowData.contentUrl.lastIndexOf('.');
	var key = rowData.contentUrl.substring(firstIndex+1,endIndex);
	$('#itemEdit_userId').val(rowData.userId);
	$('#itemEdit_key').val(key);
	$('#itemEdit_thumbUrl').val(rowData.thumbnailUrl);
	$('#itemEdit_imgDiv').html('<input id="itemEditThumb" name="itemVideoThumb" type="file" class="file"/>');
	initItemEditThumb(rowData.thumbnailUrl);
	
	$(".file-caption-name").text(rowData.thumbnailUrl);
	$(".file-caption").text(rowData.thumbnailUrl);
	$('#itemEdit_videoDiv').html(videoHtml);
	$('#itemEditModalSave').attr('onclick','saveItemEdit("'+rowid+'")');
	$("#itemEditModal").modal('show');
}

function itemEditModalHide(){
	$("#itemEditModal").modal('hide');
}

function saveItemEdit(rowid){
	var rowData = $("#itemGrid").getRowData(rowid);
	var thumbnailUrl = $('#itemEdit_thumbUrl').val();
	var userId = $('#itemEdit_userId').val();
	var mediaHeight = $('#itemEdit_mediaHeight').val();
	var mediaWidth = $('#itemEdit_mediaWidth').val();
	var description = $('#itemEdit_description').val();
	
	$.post(contextPath+"/showitem/modifyItem",{itemId:rowData.itemId,createTime:rowData.sortKey,userId:userId,thumbnailUrl:thumbnailUrl,mediaHeight:mediaHeight,mediaWidth:mediaWidth,description:encodeURIComponent(description,'utf8')}, function(data) {
		if (data) {
		    var result = eval('(' + data + ')')
		    if(result.status=='true'){
		    	$("#itemGrid").setRowData(rowid,{thumbFormat:thumbnailUrl,thumbnailUrl:thumbnailUrl,userId:userId,mediaHeight:mediaHeight,mediaWidth:mediaWidth,description:description});
		    	itemEditModalHide();
		    }else{
		    	error(result.msg);
		    }
		}
	})
}

$("#itemExportExcel").on('click', function(e) {
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

function itemBatchOperatModalHide(){
	$("#itemBatchOperatModal").modal('hide');
}

function saveBatchOperat(){
	var row = $('#itemGrid').getGridParam('selarrrow');
	var itemKeys = new Array();
	if(row){
		$.each(row, function (i, n) {
			var rowData = $("#itemGrid").getRowData(n);
			itemKeys.push(rowData.itemId+'_'+rowData.sortKey);
        })
	}
	
	var batchStatus = $('#itemBatchOperat_batchStatus').selectpicker('val');
	var tags = $('#itemBatchOperat_tagList').selectpicker('val');
	if(batchStatus==3 && (undefined == tags || ''==tags)){
        return;
    }
	
	if(batchStatus==3){
		var tagIds = '';
		$.each(tags, function (i, n) {
			tagIds += n+',';
        })
        if(tagIds.length>0){
	    	tagIds = tagIds.substring(0,tagIds.length-1)
	    }
    	$.post(contextPath+"/showtag/batchBindtag",{itemKeys:itemKeys.join(','),tagIds:tagIds}, function(data) {
			if (data) {
				var result = eval('(' + data + ')')
				if(result.status=='true'){
					$.each(row, function (i, n) {
						$("#itemGrid").setRowData(n,{tagIds:'['+tags+']',itemTags:'['+tags+']'});
	                })
					itemBatchOperatModalHide();
				}else{
					error(result.msg)
				}
			}
		})
		return;
    }else{
    	$.get(contextPath+"/showitem/modifyStatus",{itemKeys:itemKeys.join(','),status:batchStatus}, function(data) {
            if (data && data == true) {
            	//$.each(row, function (i, n) {
            	//	console.log('n:'+n+',status:'+batchStatus);
            	//	$("#itemGrid").setRowData(n,{status:batchStatus,statusFormat:0,operat:batchStatus});
                //})
            	$("#itemGrid").trigger("reloadGrid");
            	itemBatchOperatModalHide();
            } else if (data == false) {
            	error(result.msg)
            }
    	})
    }
}

//推荐内容至话题
function recommentTopicItem(rowid, status){
	var s_topic = $('#s_topic').val();
	if(s_topic==null || s_topic == ''){
		return;
	}
	var rowData = $("#itemGrid").getRowData(rowid);
    $.post(contextPath+"/showtopic/recommendTopicItem",{itemId:rowData.itemId,topic:s_topic,status:status,userId:rowData.userId}, function(data) {
		if (data) {
			var result = eval('(' + data + ')')
			if(result.status=='true'){
				$("#itemGrid").trigger("reloadGrid");
			}else{
				error(result.msg)
			}
		}
	})
}

//热门置顶
function setHotTopicItemTop(rowid, isTop){
	var s_topic = $('#s_topic').val();
	if(s_topic==null || s_topic == ''){
		return;
	}
	var rowData = $("#itemGrid").getRowData(rowid);
	$.post(contextPath+"/showtopic/setHotTopicItemTop",{itemId:rowData.itemId,topic:s_topic,isTop:isTop,userId:rowData.userId}, function(data) {
		if (data) {
			var result = eval('(' + data + ')')
			if(result.status=='true'){
				$("#itemGrid").trigger("reloadGrid");
			}else{
				error(result.msg)
			}
		}
	})
}