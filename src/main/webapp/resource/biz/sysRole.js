var isClose = true;
$(document).ready(function() {
	$("#sysRoleGrid").jqGrid({
		url : contextPath + '/sysRole/list',
		datatype: "json",
		colModel:[
				{name:'roleId',index:'roleId',hidden:true},
				{name:'roleName',index:'roleName',label:'角色名称',width:80},
				{name:'status',index:'status',label:'角色状态',width:80,formatter:formatStatus},
				{name:'createTime',index:'createTime',label:'创建日期',width:80}
		],
		viewrecords : true,
		rowNum:10,
		rowList:[10,30,50,70,100],
		pager : "#sysRoleGridPager",
		altRows: true,
		multiselect: false,
		multiboxonly: false,
		rownumbers : true,
		toolbar:[true,"top"],
		jsonReader : {
			id: "roleId",
		    total: "totalPage",
		    records: "total",
		    repeatitems: false
		},
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleGridPager(table);
				//styleGridCheckbox(table);
			}, 0);
		}
	});
	styleGridResize("#sysRoleGrid");
	
	var setting = {
		async: {
			enable: true,
			type:'GET',
			url: contextPath + '/sysMenu/select',
		},
		key: {
			name: "text",
			title: "text",
			url: ""
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid",
				rootPId: 0
			}
		}
	};
	
	$.fn.zTree.init($("#sysRoleAdd_menu"), setting);
});

$("#sysRoleAdd").on('click', function(e) {
	sysRoleAddfrmReset();
	$('#sysRoleAddModal').modal();
});

$("#sysRoleEdit").on('click', function(e) {
	var currId=$("#sysRoleGrid").jqGrid("getGridParam","selarrrow");
	if(currId.length == 1){
		var currRow = $("#sysRoleGrid").jqGrid("getRowData",currId);
		loadForm('#sysRoleEditfrm',currRow);
		$('#sysRoleEditModal').modal();
	}else{
		error("请选择一条记录");
	}
});

$("#sysRoleDelete").on('click', function(e) {
	var rows = $("#sysRoleGrid").jqGrid("getGridParam","selarrrow");
	if(rows.length > 0 ){
		confirm("是否删除该记录",function(result) {
			if(result){
				$.post(contextPath + '/sysRole/delete', {id : rows.toString()}, function(result) {
					$("#sysRoleGrid").jqGrid().trigger('reloadGrid');
				}, 'json');
			}
			console.log(result+","+rows);
		})
	}else{
		error("请选择一条记录");
	}
});

$("#sysRoleView").on('click', function(e) {
	var currId=$("#sysRoleGrid").jqGrid("getGridParam","selarrrow");
	if(currId.length == 1){
		var currRow = $("#sysRoleGrid").jqGrid("getRowData",currId);
		loadForm('#sysRoleViewfrm',currRow);
		$('#sysRoleViewModal').modal();
	}else{
		error("请选择一条记录");
	}
});

function sysRoleAddfrmBuild(){
	isClose = false;
	$("#sysRoleAddfrm").submit();
}

function sysRoleAddfrmReset(){
	$('#sysRoleAddfrm')[0].reset();
	$('#sysRoleAddfrm').validate().resetForm();
	$('.form-group').removeClass('has-error');
	$("form:eq(0) input:eq(0)").focus();
}

function sysRoleAddModalHide(){
	$('#sysRoleAddModal').modal('hide');
}

function sysRoleAddfrmSave(){
	isClose = true;
	$("#sysRoleAddfrm").submit();
}

var rules =  {
		roleId: {required: true},
		roleName: {required: true},
		status: {required: true},
		createTime: {required: true},
}
	
submitForm($('#sysRoleAddfrm'),rules,function (form) {
	$.post(contextPath + '/sysRole/add',$("#sysRoleAddfrm").serialize(),function(result){
		var result = eval('(' + result + ')');
		success(result.resultMsg);
		$("#sysRoleGrid").jqGrid().trigger('reloadGrid');
		if(isClose){
			sysRoleAddModalHide();
		}else{
			sysRoleAddfrmReset();
		}
	});
})

function sysRoleEditNext(e){
	var currRow = loadFormPager('#sysRoleGrid',e);
	loadForm('#sysRoleEditfrm',currRow);
}

function sysRoleEditPrev(e){
	var currRow = loadFormPager('#sysRoleGrid',e);
	loadForm('#sysRoleEditfrm',currRow);
}

function sysRoleEditModalHide(){
	$('#sysRoleEditModal').modal('hide');
}

function sysRoleEditfrmSave(){
	$("#sysRoleEditfrm").submit();
}

submitForm($('#sysRoleEditfrm'),rules,function (form) {
	$.post(contextPath + '/sysRole/edit',$("#sysRoleEditfrm").serialize(),function(result){
		var result = eval('(' + result + ')');
		success(result.resultMsg);
		$("#sysRoleGrid").jqGrid().trigger('reloadGrid');
		sysRoleEditModalHide();
	});
})

function sysRoleViewNext(e){
	var currRow = loadFormPager('#sysRoleGrid',e);
	loadForm('#sysRoleViewfrm',currRow);
}

function sysRoleViewPrev(e){
	var currRow = loadFormPager('#sysRoleGrid',e);
	loadForm('#sysRoleViewfrm',currRow);
}

function sysRoleViewModalHide(){
	$('#sysRoleViewModal').modal('hide');
}

function formatStatus(cellvalue, options, rowObject){
	switch (cellvalue) {
		case 0:return '<font color="red">禁用</font>';
		case 1:return '<font color="green">启用</font>';
	}
}