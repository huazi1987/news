$('#pkvideo_startTime').datetimepicker({
		minView: "month",
		format: 'yyyy-mm-dd',
		autoclose:true,
		todayBtn: true
	});

$('#pkvideo_endTime').datetimepicker({
	minView: "month",
	format: 'yyyy-mm-dd',
	autoclose:true,
	todayBtn: true
}).on("click",function(){
    $("#s_endTime").datetimepicker("setStartDate",$("#s_startTime").val())
});


$("#pkVideoSearch").on('click', function(e) {
	showVideoList(1);
});

function btnPkVideoPass(videoId,isHot)
{
	$.post(contextPath+"/superStar/pkVideo/pass",{videoId:videoId,isHot:isHot}, function(data) {
		if (data) {
			var result = eval('(' + data + ')')
			if(result.status=='true'){
				$('#'+videoId).remove();
				success(result.msg)
			}else{
				error(result.msg)
			}
		}
   })
}

function btnPkVideoFail(videoId)
{
	$('#videoAuditFail_videoId').val(videoId);
	$("#videoAuditFailModal").modal('show');
}

function videoAuditFailModalHide(){
	$('#videoAuditFailModal').modal('hide');
}

function saveVideoAuditFail()
{
	var videoId = $('#videoAuditFail_videoId').val();
	var msgCode = $("input[name='msgCode']:checked").val();
	if(msgCode == undefined || msgCode==''){
		msgCode = 0;
	}
	$.post(contextPath+"/superStar/pkVideo/fail",{videoId:videoId,msgCode:msgCode}, function(data) {
		if (data) {
			var result = eval('(' + data + ')')
			if(result.status=='true'){
				videoAuditFailModalHide();
				$('#'+videoId).remove();
				success(result.msg)
			}else{
				error(result.msg)
			}
		}
   })
}

function showVideoHot(videoId,isHot){
	$.post(contextPath+"/superStar/pkVideo/saveVideoHot",{videoId:videoId,isHot:isHot}, function(data) {
		if (data) {
			var result = eval('(' + data + ')')
			if(result.status=='true'){
				success(result.msg)
			}else{
				error(result.msg)
			}
		}
    })
}