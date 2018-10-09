$('#rank').ace_spinner({
	value:1,
	min:1,
	max:9999,
	step:1, 
	on_sides: true, 
	icon_up:'ace-icon fa fa-plus smaller-75', 
	icon_down:'ace-icon fa fa-minus smaller-75', 
	btn_up_class:'btn-success', 
	btn_down_class:'btn-danger'
})

$("#sysUserAdd").on('click', function(e) {
	showModal('add','');
});

function sysUserEdit(rowid){
	showModal('edit',rowid);
}

function sysUserModalHide(){
	$('#sysUserModal').modal('hide');
}

function sysUserSave()
{
	$('#sysUserfrm').submit();
}

var rules =  {
	userName: {required: true},
	userLogin: {required: true},
	email: {email:true}
}

submitForm($('#sysUserfrm'),rules,function (form) {
	$.post(contextPath + '/sysUser/save',$("#sysUserfrm").serialize(),function(result){
		var result = eval('(' + result + ')');
		success(result.resultMsg);
		$("#sysUserGrid").trigger("reloadGrid");
		sysUserModalHide();
	});
})