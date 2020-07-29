var isClose = true;
$(document).ready(function() {
	$("#sysMenuGrid").jqGrid({
		url : contextPath + '/sysMenu/list',
		datatype: "json",
		colModel:[
				{name:'menuId',hidden:true},
				{name:'menuName',label:'菜单名称',width:80},
				{name:'menuEnName',label:'菜单英文名称',width:80},
				{name:'menuUrl',label:'菜单路径',width:160},
				{name:'menuImg',label:'菜单图片',width:50,formatter:formatMenuIcon},
				{name:'menuType',label:'菜单类型',width:50,formatter:formatMenuType},
				{name:'rank',label:'排序',width:50},
				{name:'status',label:'状态',width:50,formatter:formatStatus},
				{name:'createTime',label:'创建时间',width:80}
		],
		viewrecords : true,
		rowNum:10,
		rowList:[10,30,50,70,100],
		pager : "#sysMenuGridPager",
		altRows: true,
		multiselect: true,
		multiboxonly: true,
		rownumbers : true,
//		treeGrid: true,//启用树型Grid功能
//	    treeGridModel: 'adjacency',
	    ExpandColumn : 'menuName',  
		toolbar:[true,"top"],
		jsonReader : {
			id: "menuId",
		    total: "totalPage",
		    records: "total",
		    repeatitems: false
		},
//		treeReader : {    
//            level_field: "level",    
//            parent_id_field: "menuPid",    
//            leaf_field: true,    
//            expanded_field: true    
//		},
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleGridPager(table);
				//styleGridCheckbox(table);
			}, 0);
		}
	});
	
	styleGridResize("#sysMenuGrid");
	
	$('#rank').ace_spinner({
		value:0,
		min:0,
		max:9999,
		step:1, 
		on_sides: true, 
		icon_up:'ace-icon fa fa-plus smaller-75', 
		icon_down:'ace-icon fa fa-minus smaller-75', 
		btn_up_class:'btn-success' , 
		btn_down_class:'btn-danger'
	});
	
	$('#menuEdit_rank').ace_spinner({
		value:0,
		min:0,
		max:9999,
		step:1, 
		on_sides: true, 
		icon_up:'ace-icon fa fa-plus smaller-75', 
		icon_down:'ace-icon fa fa-minus smaller-75', 
		btn_up_class:'btn-success' , 
		btn_down_class:'btn-danger'
	});
	
	var menuData = [];
	$.ajax({
	     url: contextPath + '/sysMenu/select',
	     dataType:"json",
	     async: false,
	     contentType:"application/json",
	     success:function(data){
	    	 menuData = data;
	     },
	     error:function(data){
	    	 
	     }
	 });
	
	$("#menuAdd_menuPid").selectpicker({
		data: menuData,
	    placeholder:'请选择',
		width:'60%'
	});
	
	$("#menuAdd_menuPid").val("");
	$("#menuAdd_menuPid").trigger('change');
});

function formatRepoSelection (repo) {
  return repo.full_name || repo.text;
}

$("#sysMenuAdd").on('click', function(e) {
	sysMenuAddfrmReset();
	$('#sysMenuAddModal').modal();
});

$("#sysMenuEdit").on('click', function(e) {
	var currId=$("#sysMenuGrid").jqGrid("getGridParam","selarrrow");
	if(currId.length == 1){
		var currRow = $("#sysMenuGrid").jqGrid("getRowData",currId);
		loadForm('#sysMenuEditfrm',currRow);
		$('#sysMenuEditModal').modal();
	}else{
		error("请选择一条记录");
	}
});

$("#sysMenuDelete").on('click', function(e) {
	var rows = $("#sysMenuGrid").jqGrid("getGridParam","selarrrow");
	if(rows.length > 0 ){
		confirm("是否删除该记录",function(result) {
			if(result){
				$.post(contextPath + '/sysMenu/delete', {id : rows.toString()}, function(result) {
					$("#sysMenuGrid").jqGrid().trigger('reloadGrid');
				}, 'json');
			}
			console.log(result+","+rows);
		})
	}else{
		error("请选择一条记录");
	}
});

$("#sysMenuView").on('click', function(e) {
	var currId=$("#sysMenuGrid").jqGrid("getGridParam","selarrrow");
	if(currId.length == 1){
		var currRow = $("#sysMenuGrid").jqGrid("getRowData",currId);
		loadForm('#sysMenuViewfrm',currRow);
		$('#sysMenuViewModal').modal();
	}else{
		error("请选择一条记录");
	}
});

function sysMenuAddfrmBuild(){
	isClose = false;
	$("#sysMenuAddfrm").submit();
}

function sysMenuAddfrmReset(){
	$('#sysMenuAddfrm')[0].reset();
	$('#sysMenuAddfrm').validate().resetForm();
	$('.form-group').removeClass('has-error');
	$("form:eq(0) input:eq(0)").focus();
}

function sysMenuAddModalHide(){
	$('#sysMenuAddModal').modal('hide');
}

function sysMenuAddfrmSave(){
	isClose = true;
	$("#sysMenuAddfrm").submit();
}

var rules =  {
		menuName: {required: true},
		menuImg: {required: true},
		menuType: {required: true},
		level: {required: true},
		rank: {required: true},
}
	
submitForm($('#sysMenuAddfrm'),rules,function (form) {
	// var select = $("#menuAdd_menuPid").val();
	// if(select && select.selected){
	// 	$('#menuPid').val(select.id);
	// }else{
	// 	$('#menuPid').val('');
	// }
	
	$.post(contextPath + '/sysMenu/add',$("#sysMenuAddfrm").serialize(),function(result){
		var result = eval('(' + result + ')');
		success(result.resultMsg);
		$("#sysMenuGrid").jqGrid().trigger('reloadGrid');
		if(isClose){
			sysMenuAddModalHide();
		}else{
			sysMenuAddfrmReset();
		}
	});
})

function sysMenuEditNext(e){
	var currRow = loadFormPager('#sysMenuGrid',e);
	loadForm('#sysMenuEditfrm',currRow);
}

function sysMenuEditPrev(e){
	var currRow = loadFormPager('#sysMenuGrid',e);
	loadForm('#sysMenuEditfrm',currRow);
}

function sysMenuEditModalHide(){
	$('#sysMenuEditModal').modal('hide');
}

function sysMenuEditfrmSave(){
	$("#sysMenuEditfrm").submit();
}

submitForm($('#sysMenuEditfrm'),rules,function (form) {
	$.post(contextPath + '/sysMenu/edit',$("#sysMenuEditfrm").serialize(),function(result){
		var result = eval('(' + result + ')');
		success(result.resultMsg);
		$("#sysMenuGrid").jqGrid().trigger('reloadGrid');
		sysMenuEditModalHide();
	});
})

function sysMenuViewNext(e){
	var currRow = loadFormPager('#sysMenuGrid',e);
	loadForm('#sysMenuViewfrm',currRow);
}

function sysMenuViewPrev(e){
	var currRow = loadFormPager('#sysMenuGrid',e);
	loadForm('#sysMenuViewfrm',currRow);
}

function sysMenuViewModalHide(){
	$('#sysMenuViewModal').modal('hide');
}

function formatMenuType(cellvalue, options, rowObject){
	switch (cellvalue) {
		case 1:return '菜单';
		case 2:return '功能';
	}
}

function formatStatus(cellvalue, options, rowObject){
	switch (cellvalue) {
		case 0:return '<font color="red">禁用</font>';
		case 1:return '<font color="green">启用</font>';
	}
}

function formatMenuIcon(cellvalue, options, rowObject){
	return "<i class='menu-icon fa "+cellvalue+" blue'>";
}