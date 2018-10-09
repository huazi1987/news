/**
 * 操作成功提示
 * @param msg
 * @param type
 */
function success(msg, type) {
	$.gritter.add({title: 'Success',text: msg,class_name: 'gritter-success'});
}

/**
 * 操作失败提示
 * @param msg
 */
function error(msg) {
	$.gritter.add({title: 'Error',text: msg,class_name: 'gritter-error'});
}

/**
 * 是否确认提示
 * @param msg
 * @param callback
 */
function confirm(title,msg,callback) {
	bootbox.confirm({
		message: msg,
		title: title,
		buttons: {
			confirm: {label: 'confirm',className: "btn-sm btn-primary"},
		  	cancel: {label: 'cancel',className: "btn-sm"}
		},
		callback: callback
	});
}

/**
 * 切换语言
 * @param themeName
 */
function changeLang(langName) {
	$.cookie('W_LANG', langName, { expires: 7, path: '/' });
	window.location.reload();
}

/**
 * 提交表单
 * @param form
 * @param rules
 * @param callback
 */
function submitForm(form,rules,callback) {
	form.validate({
		errorElement: 'div',
		errorClass: 'help-block',
		focusInvalid: false,
		rules: rules,
		highlight: function (e) {
			$(e).closest('.form-group').removeClass('has-info').addClass('has-error');
		},
		success: function (e) {
			$(e).closest('.form-group').removeClass('has-error').addClass('has-info');
			$(e).remove();
		},
		submitHandler: callback
	});
}

/**
 * 表单加载数据
 * @param target
 * @param data
 */
function loadForm(target, data){
	var form = $(target);
	for(var name in data){
		var val = data[name];
		if (!_checkField(name, val)){
			form.find('input[name="'+name+'"]').val(val);
			form.find('textarea[name="'+name+'"]').val(val);
			form.find('select[name="'+name+'"]').val(val);
		}
	}
	function _checkField(name, val){
		var cc = $(target).find('input[name="'+name+'"][type=radio], input[name="'+name+'"][type=checkbox]');
		if (cc.length){
			cc.attr('checked', false);
			cc.each(function(){
				if (val=='1'){
					cc.attr('checked', true);
				}
			});
			return true;
		}
		return false;
	}
}

/**
 * 编辑表格数据初始化，返回下一条待编辑内容
 * @param target
 * @param e
 * @returns
 */
function loadFormPager(target,e){
	var gridTaget = $(target);
	var rows = gridTaget.jqGrid("getGridParam","selarrrow");
	var ids = gridTaget.jqGrid("getDataIDs");
	var currId = rows[rows.length - 1];
	var i=0;
	while (ids[i])
	{
		if(ids[i]==currId){
			break;
		}
		i++;
	}
	
	if($(e).hasClass("btnEditNext")){
		$(".btnEditPrev").removeClass("disabled");
		i++;
		if(i+1==ids.length){
			$(".btnEditNext").addClass('disabled');
		}
	}else
	if($(e).hasClass("btnEditPrev")){
		$(".btnEditNext").removeClass("disabled");
		i--;
		if(i==0){
			$(".btnEditPrev").addClass('disabled');
		}
	}
	
	gridTaget.jqGrid('setSelection',ids[i]);
	gridTaget.jqGrid('setSelection',currId);
	
	return gridTaget.jqGrid("getRowData",ids[i]);
}

/**
 * sidebar-menu组件
 * @param $
 */
(function ($) {
    $.fn.sidebarMenu = function (options) {
        options = $.extend({}, $.fn.sidebarMenu.defaults, options || {});
        var target = $(this);
        target.addClass('nav');
        target.addClass('nav-list');
        if (options.data) {
            init(target, options.data);
        }
        else {
            if (!options.url) return;
            $.getJSON(options.url, options.param, function (data) {
                init(target, data);
            });
        }
        var url = window.location.pathname;
        //menu = target.find("[href='" + url + "']");
        //menu.parent().addClass('active');
        //menu.parent().parentsUntil('.nav-list', 'li').addClass('active').addClass('open');
        function init(target, data) {
            $.each(data, function (i, item) {
                var li = $('<li></li>');
                var a = $('<a></a>');
                var icon = $('<i></i>');
                icon.addClass('menu-icon');
                icon.addClass(item.iconCls);
                var text = $('<span></span>');
                text.addClass('menu-text').text($.cookie('W_LANG')=='en_US'?item.enText:item.text);
                
                a.append(icon);
                a.append(text);
                if (item.children&&item.children.length>0) {
                    a.attr('href', '#');
                    a.addClass('dropdown-toggle');
                    var arrow = $('<b></b>');
                    arrow.addClass('arrow').addClass('fa').addClass('fa-angle-down');
                    a.append(arrow);
                    li.append(a);
                    var menus = $('<ul></ul>');
                    menus.addClass('submenu');
                    init(menus, item.children);
                    li.append(menus);
                }
                else {
                    var href = 'javascript:addTabs({id:\'' + item.id + '\',title: \'' + ($.cookie('W_LANG')=='en_US'?item.enText:item.text) + '\',close: true,url: \'' + item.url + '\'});';
                    a.attr('href', href);
                    //if (video.istab)
                    //    a.attr('href', href);
                    //else {
                    //    a.attr('href', video.url);
                    //    a.attr('title', video.text);
                    //    a.attr('target', '_blank')
                    //}
                    li.append(a);
                }
                target.append(li);
            });
        }
    }

    $.fn.sidebarMenu.defaults = {
        url: null,
        param: null,
        data: null
    };
})(jQuery);

var addTabs = function (options) {
    id = "tab_" + options.id;
    $(".active").removeClass("active");
    if (!$("#" + id)[0]) {
    	mainHeight = $(window).height() * 0.9;
        //创建新TAB的title
        title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + options.title;
        //是否允许关闭
        if (options.close) {
            title += ' <i class="glyphicon glyphicon-remove" tabclose="' + id + '"></i>';
        }
        title += '</a></li>';
        //是否指定TAB内容
        if (options.content) {
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
        } else {//没有内容，使用IFRAME打开链接
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe src="' + contextPath + '/' + options.url + '" width="100%" height="' + mainHeight +
                    '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="true"></iframe></div>';
        }
        //加入TABS
        $(".nav-tabs").append(title);
        $(".tab-content").append(content);
    }
    //激活TAB
    $("#tab_" + id).addClass('active');
    $("#" + id).addClass("active");
};

//跨域，在父窗口添加菜单
var addTabMenu = function (options) {
    id = "tab_" + options.id;
    if (!$("#" + id,parent.document)[0]) {
    	mainHeight = $(window).height() * 0.9;
        //创建新TAB的title
        title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + options.title;
        title += ' <i class="glyphicon glyphicon-remove" tabclose="' + id + '"></i>';
        title += '</a></li>';
        //是否指定TAB内容
        if (options.content) {
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
        } else {//没有内容，使用IFRAME打开链接
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe src="' + options.url + '" width="100%" height="' + mainHeight +
                    '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="true"></iframe></div>';
        }
        //加入TABS
        $(".nav-tabs", parent.document).append(title);
        $(".tab-content", parent.document).append(content);
    }
};
var addTabVideo = function (options) {
    id = "tab_" + options.id;
    if (!$("#" + id,parent.document)[0]) {
    	mainHeight = $(window).height() * 0.9;
        //创建新TAB的title
        title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + options.title;
        title += ' <i class="glyphicon glyphicon-remove" tabclose="' + id + '"></i>';
        title += '</a></li>';
        //是否指定TAB内容
        if (options.content) {
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
        } else {//没有内容，使用IFRAME打开链接
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><video src="' + options.url + '" width="640" height="390" controls autoplay="true"></video></div>';
        }
        //加入TABS
        $(".nav-tabs", parent.document).append(title);
        $(".tab-content", parent.document).append(content);
    }
};


var closeTab = function (id) {
    //如果关闭的是当前激活的TAB，激活他的前一个TAB
    if ($("li.active").attr('id') == "tab_" + id) {
        $("#tab_" + id).prev().addClass('active');
        $("#" + id).prev().addClass('active');
    }
    //关闭TAB
    $("#tab_" + id).remove();
    $("#" + id).remove();
};
$(function () {
    //mainHeight = $(document.body).height() - 45;
	mainHeight = $(window).height();
    $('.main-left,.main-right').height(mainHeight);
    $("[addtabs]").click(function () {
        addTabs({ id: $(this).attr("id"), title: $(this).attr('title'), close: true });
    });

    $(".nav-tabs").on("click", "[tabclose]", function (e) {
        id = $(this).attr("tabclose");
        closeTab(id);
    });
});

/**
 * Grid分页样式
 * @param table
 */
function styleGridPager(table) {
	var replacement = 
	{
		'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
		'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
		'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
		'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
	};
	$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
		var icon = $(this);
		var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
		if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
	})
}

/**
 * Grid复选框样式
 * @param table
 */
function styleGridCheckbox(table) {
	$(table).find('input:checkbox').addClass('ace')
		.wrap('<label />')
		.after('<span class="lbl align-top" />')
		$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
		.find('input.cbox[type=checkbox]').addClass('ace')
		.wrap('<label />').after('<span class="lbl align-top" />');
}

/**
 * Grid宽高样式
 * @param table
 */
function styleGridResize(table) {
    $(window).on('resize.jqGrid', function () {
		$(table).jqGrid('setGridWidth', $(".page-content").width()+1);
		$(table).jqGrid('setGridHeight', $(document.body).height()+30);
//		$(table).jqGrid('setGridHeight', $(".page-content").height()+30);
    })
	$(window).triggerHandler('resize.jqGrid');
}

function gridImageOnClick(){
	var colorbox_params = {
		rel: 'colorbox',
		reposition:true,
		scalePhotos:true,
		scrolling:false,
		current:'{current} of {total}',
		maxWidth:'100%',
		maxHeight:'80%',
		onOpen:function(){
		},
		onClosed:function(){
		},
		onComplete:function(){
			$.colorbox.resize();
		}
	};
	$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
}

function formatTitle(cellValue, options, rowObject) {
    return cellValue.substring(0, 50) + "...";
};

function formatLink(cellValue, options, rowObject) {
    return "<a href='" + cellValue + "'>" + cellValue.substring(0, 25) + "..." + "</a>";
};

function formatIcon(cellValue, options, rowObject) {
	return '<img style="width: 16px;height: 16px" src=' +  contextPath +'/'+ rowObject.icon + '/>&nbsp;'+cellValue
};

var substringMatcher = function(strs) {
	return function findMatches(q, cb) {
		var matches, substringRegex;
		matches = [];
		substrRegex = new RegExp(q, 'i');
		$.each(strs, function(i, str) {
			if (substrRegex.test(str)) {
				matches.push({ value: str });
			}
		});
		cb(matches);
	}
}