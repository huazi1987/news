package com.news.cms.sys.menu.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.news.common.base.BaseModel;
import com.news.common.json.serializer.JsonDateSerializer;
import com.ushareit.news.common.base.BaseModel;
import com.ushareit.news.common.json.serializer.JsonDateSerializer;

/**
 * @Description: 系统菜单
 * @author 王慧智
 * @date 2016-04-11
 */
public class SysMenu extends BaseModel<SysMenu> {

	private static final long serialVersionUID = 1L;
	
	private String menuId;
	private String menuName;
	private String menuEnName;
	private String menuUrl;
	private String menuPath;
	private String menuImg;
	private String menuPid;
	private Integer menuType;
	private Integer level;
	private Integer rank;
	private Integer status;
	private Date createTime;
	
	//逻辑变量
	private List<SysMenu> children;	//所有子节点
	private String State;			//节点开启状态(open|closed)
	
	public SysMenu() {
	}

	/**
	 * @Description: 菜单ID
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @Description: 菜单ID
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	/**
	 * @Description: 菜单名称
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @Description: 菜单名称
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	/**
	 * @Description: 菜单路径
	 */
	public String getMenuUrl() {
		return menuUrl;
	}

	/**
	 * @Description: 菜单路径
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	/**
	 * @Description: 节点路径
	 */
	public String getMenuPath() {
		return menuPath;
	}

	/**
	 * @Description: 节点路径
	 */
	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}
	/**
	 * @Description: 菜单图片
	 */
	public String getMenuImg() {
		return menuImg;
	}

	/**
	 * @Description: 菜单图片
	 */
	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}
	/**
	 * @Description: 上级ID
	 */
	public String getMenuPid() {
		return menuPid;
	}

	/**
	 * @Description: 上级ID
	 */
	public void setMenuPid(String menuPid) {
		this.menuPid = menuPid;
	}
	/**
	 * @Description: 层级
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @Description: 层级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * @Description: 排序
	 */
	public Integer getRank() {
		return rank;
	}

	/**
	 * @Description: 排序
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	/**
	 * @Description: 状态(1启用，0禁用)
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @Description: 状态(1启用，0禁用)
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @Description: 创建时间
	 */
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @Description: 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public String getMenuEnName() {
		return menuEnName;
	}

	public void setMenuEnName(String menuEnName) {
		this.menuEnName = menuEnName;
	}
}
