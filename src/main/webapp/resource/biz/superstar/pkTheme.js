$('.selectpicker').selectpicker({
    'selectedText': 'cat',
    'width':'75%'
})

$('#pkTheme_startTime').datetimepicker({
	format: 'yyyy-mm-dd 00:00:00',//hh:ii:ss
	minView: "month",
	autoclose:true,
	todayBtn: true,
	startDate: new Date()
})
$('#pkTheme_endTime').datetimepicker({
	format: 'yyyy-mm-dd 23:00:00',
	minView: "month",
	autoclose:true,
	todayBtn: true,
}).on("click",function(){
    $("#pkTheme_endTime").datetimepicker("setStartDate",$("#pkTheme_startTime").val())
});

function formatThemeLogo(cellValue, options, rowObject) {
	if(rowObject.themeLogo!=undefined && rowObject.themeLogo!=''){
		return '<div class="ace-thumbnails"><a href="'+weshowCDN+rowObject.themeLogo+'" data-rel="colorbox"><img width="100" height="100" src="'+weshowCDN+rowObject.themeLogo+'"/></a></div>'
	}
	return '';
}

function formatThemeImg(cellValue, options, rowObject) {
	if(rowObject.themeImage!=undefined && rowObject.themeImage!=''){
		return '<div class="ace-thumbnails"><a href="'+weshowCDN+rowObject.themeImage+'" data-rel="colorbox"><img width="100" height="100" src="'+weshowCDN+rowObject.themeImage+'"/></a></div>'
	}
	return '';
}

function formatThemeBonus(cellValue, options, rowObject) {
	return rowObject.bonusCount / 100;
}

$("#pkThemeAdd").on('click', function(e) {
	showModal('add','');
});

function pkThemeEdit(rowid){
	showModal('edit',rowid);
}

function pkThemeModalHide(){
	$('#pkThemeModal').modal('hide');
}

function pkThemefrmSave()
{
	$('#pkThemefrm').submit();
}

var pkThemeRules =  {
	themeName: {required: true},
	themeIntro: {required: true},
	themeLogo: {required: true},
	bonusCount: {required: true},
	maxLevel: {required: true},
	startTime: {required: true},
	endTime: {required: true},
	templetId: {required: true},
	themeImage: {required: true}
}

submitForm($('#pkThemefrm'),pkThemeRules,function (form) {
	var formData = new FormData($("#pkThemefrm")[0]);
	$.ajax({  
        url: contextPath + '/superStar/pkTheme/add',
        type: 'POST',  
        data: formData,
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false,  
        success: function (data) {
        	var result = eval('(' + data + ')')
	    	if (result.status == 'true') {
	    		pkThemeModalHide();
	    		$("#pkThemeGrid").trigger('reloadGrid');
	    	} else if (result.status == 'false') {
	    		error(result.msg);
	    	} 
        } 
   });
})

function modifyOpenStatus(rowid, status) {
	var rowData = $("#pkThemeGrid").getRowData(rowid);
	$.get(contextPath+"/superStar/pkTheme/modifyOpenStatus",{themeId:rowData.themeId,status:status}, function(data) {
        if (data && data == true) {
        	$("#pkThemeGrid").setRowData(rowid,{openStatus:status,openStatusFormat:status,operat:status});
        }
    })
}

function pkThemeUploadDemo(rowid){
	
	var rowData = $("#pkThemeGrid").getRowData(rowid);
	$('#pkThemeUploadDemo_themeId').val(rowData.themeId);
	
	$('#pkThemeUploadDemo_imageDiv').html('<input id="pkThemeUploadDemo_image" name="themeimage" type="file" class="file"/>');
	initUploadDemoImage();
	
	$('#pkThemeUploadDemo_videoDiv').html('<input id="pkThemeUploadDemo_video" name="themevideo" type="file" class="file"/>')
	initUploadDemoVideo();
	
	$('#pkThemeUploadDemoModal').modal('show');
}

function pkThemeUploadDemoModalHide(){
	$('#pkThemeUploadDemoModal').modal('hide');
}

function pkThemeUploadDemofrmSave()
{
	$('#pkThemeUploadDemofrm').submit();
}

submitForm($('#pkThemeUploadDemofrm'),{},function (form) {
	var formData = new FormData($("#pkThemeUploadDemofrm")[0]);
	$.ajax({  
        url: contextPath + '/superStar/pkTheme/uploadDemoSave',
        type: 'POST',  
        data: formData,
        async: false,  
        cache: false,  
        contentType: false,  
        processData: false,  
        success: function (data) {
        	var result = eval('(' + data + ')')
	    	if (result.status == 'true') {
	    		pkThemeUploadDemoModalHide();
	    	} else if (result.status == 'false') {
	    		error(result.msg);
	    	} 
        } 
   });
})

function showPkThemeScoreModal(rowid){
	var rowData = $("#pkThemeGrid").getRowData(rowid);
	$('#pkThemeScore_value').val(rowData.score);
	$('#pkThemeScoreModalSave').attr('onclick','savePkThemeScore("'+rowid+'")');
	$("#pkThemeScoreModal").modal('show');
}

function pkThemeScoreModalHide(){
	$("#pkThemeScoreModal").modal('hide');
}

function savePkThemeScore(rowid){
	var rowData = $("#pkThemeGrid").getRowData(rowid);
	var score = $('#pkThemeScore_value').val();
	$.post(contextPath+"/superStar/pkTheme/updateScore",{themeId:rowData.themeId,score:score}, function(data) {
		if (data) {
		    var result = eval('(' + data + ')')
		    if(result.status=='true'){
		    	$("#pkThemeGrid").setRowData(rowid,{score:score});
		    	pkThemeScoreModalHide();
		    }else{
		    	error(result.msg);
		    }
		}
	})
}