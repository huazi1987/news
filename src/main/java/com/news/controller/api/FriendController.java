package com.news.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.news.common.page.Pagination;
import com.news.common.util.StringUtil;
import com.news.model.Friend;
import com.news.model.User;
import com.news.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/api/friend")
public class FriendController {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private FriendService friendService;

	@Autowired
	private UserService userService;

	private final static int PAGE_SIZE = 10;

	/**
	 * 申请加好友
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	@ResponseBody
	public String publish(@RequestBody Map<String, Object> params) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		String userId = (String) params.get("userId");
		String phoneNum = (String) params.get("phoneNum");

		try {
			if (StringUtil.isEmpty(userId) || StringUtil.isEmpty(phoneNum)){
				result.put("status", false);
				result.put("msg","参数错误");
				return  result.toJSONString();
			}

			User user = userService.findUserByLoginName(phoneNum);
			if (null == user ){
				result.put("msg","用户不存在");
				return result.toJSONString();
			}
			if (user.getId() == Integer.parseInt(userId)){
				result.put("msg","不能添加自己为好友");
				return result.toJSONString();
			}

			Friend friend = new Friend();
			friend.setUserId(Integer.parseInt(userId));
			friend.setApplyUserId(user.getId());

			friendService.insert(friend);

			result.put("status", true);
			result.put("msg","发送邀请成功");
			return result.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg","系统错误");
			return result.toJSONString();
		}
	}


	/**
	 * 申请列表
	 * @param pageNo
	 * @param applyUserId
	 * @return
	 */
	@RequestMapping(value = "/apply/list", method = RequestMethod.GET)
	@ResponseBody
	public String listApplying(int pageNo, int applyUserId) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		try {
			Pagination pagination = new Pagination(pageNo, PAGE_SIZE);
			Page<Friend> page = friendService.queryApplyingList(pagination, applyUserId);
			result.put("data", page);
			return result.toJSONString();
		}catch (Exception e){
			e.printStackTrace();
			result.put("msg","系统错误");
			return result.toJSONString();
		}
	}

	/**
	 * 待审核列表
	 * @param pageNo
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/pend/list", method = RequestMethod.GET)
	@ResponseBody
	public String listPending(int pageNo, int userId) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		try {
			Pagination pagination = new Pagination(pageNo, PAGE_SIZE);
			Page<Friend> page = friendService.queryPendingList(pagination, userId);
			result.put("data", page);
			return result.toJSONString();
		}catch (Exception e){
			e.printStackTrace();
			result.put("msg","系统错误");
			return result.toJSONString();
		}
	}

	@RequestMapping(value = "/handle", method = RequestMethod.POST)
	@ResponseBody
	public String handle(@RequestBody Map<String, Integer> params) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		try {
			if (params.get("isAgree") <0 || params.get("isAgree") > 2){
				result.put("status", false);
				result.put("msg","参数错误");
				return  result.toJSONString();
			}
			friendService.updateFriend(params.get("userId"), params.get("isAgree"));
			result.put("status", true);
			result.put("data", null);
			return result.toJSONString();
		}catch (Exception e){
			e.printStackTrace();
			result.put("msg","系统错误");
			return result.toJSONString();
		}
	}

}
