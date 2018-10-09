$('#startTime').datetimepicker({
	format: 'yyyy/mm/dd hh:ii',
	startDate: new Date(new Date().getTime() + 1000 * 60 * 30)
});

$('#endTime').datetimepicker({
	format: 'yyyy/mm/dd hh:ii',
	startDate: "2017/12/28 10:00",
}).on("click",function(){
    $("#endTime").datetimepicker("setStartDate",$("#startTime").val())
});

$('.selectpicker').selectpicker({
    'selectedText': 'cat',
    'width':'20%'
});

$("#topicAdd").on('click', function(e) {
	showModal('add','');
});

function topicEdit(rowid){
	showModal('edit',rowid);
}

function addTopicSave()
{
	$('#topicfrm').submit();
}

$("#topicSearch").on('click', function(e) {
	var s_topic = $("#s_topic").val()||"";
	var s_type = $("#s_type").val()||"";
	var s_status = $("#s_status").val()||"";
	$("#topicGrid").jqGrid('setGridParam', {
	    url : contextPath + '/showtopic/query',
	    postData : {'topic':s_topic,'type':s_type,'status':s_status},
	    page : 1
	}).trigger("reloadGrid");
});

function topicModalHide(){
	$('#topicModal').modal('hide');
}

$('#position').ace_spinner({
	value:0,
	min:0,
	max:9999,
	step:1, 
	on_sides: true, 
	icon_up:'ace-icon fa fa-plus smaller-75', 
	icon_down:'ace-icon fa fa-minus smaller-75', 
	btn_up_class:'btn-success', 
	btn_down_class:'btn-danger'
});

$('#musicId').on('hidden.bs.select', function (e) {
	showAudio();
});

function showAudio()
{
	$('#auditionDiv').html('');
	var musicValue = $('#musicId').selectpicker('val');
    if(musicValue == '' || musicValue == undefined){
         return;
    }
    var musicInfo = musicValue.split('|');
    $('#auditionDiv').html('<audio controls="controls" ><source src="'+musicInfo[1]+'" type="audio/mpeg" />Your browser does not support the audio element.</audio>');
}

function formatTopicImg(cellValue, options, rowObject) {
	if(rowObject.topicImgUrl!=undefined && rowObject.topicImgUrl!=''){
		return '<img style="width: 100px;height: 64px" src=' + rowObject.topicImgUrl + '>';
	}
	return '';
}

function formatMarkTitle(cellValue, options, rowObject) {
	if(rowObject.markTitle!=undefined && rowObject.markTitle!=''){
		return '【'+rowObject.markTitle+'】';
	}else
	if(rowObject.markImgUrl!=undefined && rowObject.markImgUrl!=''){
		return '<img style="width: 100px;height: 64px" src=' + rowObject.markImgUrl + '/>';
	}
	return '';
}

var rules =  {
	topic : {required: true}
}

submitForm($('#topicfrm'),rules,function (form) {
	var musicChannelId = $('#musicChannelId').val();
	var musicId = $('#musicId').val();
    if (musicChannelId =='ALL'){
    	$("input[name='musicKey']").val('-1');
    	$("input[name='musicValue']").val('ALL');
    	$("input[name='musicChannelId']").val('');
    }
    if (musicChannelId !='ALL' && musicChannelId!= ''){
    	$("input[name='musicKey']").val('0');
    	$("input[name='musicValue']").val(musicChannelId);
    	$("input[name='musicChannelId']").val(musicChannelId);
        if(musicId != '' && musicId != undefined){
        	var musicInfo = musicId.split('|');
        	$("input[name='musicKey']").val('1');
        	$("input[name='musicValue']").val(musicInfo[1]);
        	$("input[name='musicId']").val(musicInfo[0]);
        }
    }
	$.post(contextPath + '/showtopic/add',$("#topicfrm").serialize(),function(data){
		var result = eval('(' + data + ')')
        if (result.status == 'true') {
        	topicModalHide();
        	$("#topicGrid").jqGrid().trigger('reloadGrid');
        } else if (result.status == 'false') {
            error(result.msg);
        }
	});
})

function topicStatus(rowid, status){
	var rowData = $("#topicGrid").getRowData(rowid);
	$.post(contextPath+"/showtopic/modifyStatus?topicIds="+rowData.topicId+"&status="+status, 
    	function (data) {
     		result = eval('(' + data + ')')
       		if (result.status == 'true') {
 				$("#topicGrid").jqGrid().trigger('reloadGrid');
          	} else if (result.status == 'false') {
 				error(result.msg);
  			}
	});
}