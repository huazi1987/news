package com.news.controller.cms;

import com.news.common.base.BaseController;
import com.news.common.json.DataGridTool;
import com.news.common.page.Pagination;
import com.news.common.util.StringUtil;
import com.news.model.User;
import com.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cms/user")
public class UserCMSController extends BaseController{

	@Autowired
	private UserService userService;


	@RequestMapping(value = "/toList", method = RequestMethod.GET)
	public String toList(HttpServletRequest request) {
		return "user/userList";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public String query(HttpServletRequest request, int page,String sidx,String sord) throws Exception{
		Map<String, String> orderBy = new HashMap<>();
		if(!StringUtil.isEmpty(sidx) && !StringUtil.isEmpty(sord)) {
			orderBy.put(sidx, sord);
		}

		Page<User> result = userService.queryUserList(new Pagination(page, rows));

		String jsonList = jacksonMapper.writeValueAsString(result.getContent());
		return DataGridTool.formatJGridPage(result.getTotalPages(),result.getTotalElements(),jsonList);
	}

}
