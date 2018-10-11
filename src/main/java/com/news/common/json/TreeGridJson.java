package com.news.common.json;

import com.news.model.SysMenu;

import java.util.ArrayList;
import java.util.List;


public class TreeGridJson {
	
//	public static List<SysMenu> formatTreeGrid(List<SysMenu> list,String treeId) {
//
//		SysMenu node = new SysMenu();
//		List<SysMenu> treelist = new ArrayList<SysMenu>();// 拼凑好的json格式的数据
//		List<SysMenu> parentnodes = new ArrayList<SysMenu>();// parentnodes存放所有的父节点
//
//		if (list != null && list.size() > 0) {
//			//循环遍历所有节点
//			for (int j = 0; j < list.size(); j++) {
//				node = list.get(j);
//				if(node.getMenuPid().equals(treeId)){
//					parentnodes.add(node);
//					treelist.add(node);
//				}else{
//					getChildrenNodes(parentnodes, node);
//					parentnodes.add(node) ;
//				}
//			}
//		}
//		return treelist;
//
//	}

	/**
	 * 获得子节点
	 * @Description: 
	 * @author 王慧智
	 * @date 2014-4-30
	 * @param parentnodes
	 * @param node
	 */
	private static void getChildrenNodes(List<SysMenu> parentnodes, SysMenu node) {
		//循环遍历所有父节点和node进行匹配，确定父子关系
		for (int i = parentnodes.size() - 1; i >= 0; i--) {

			SysMenu pnode = parentnodes.get(i);
			if(pnode.getChildren()==null){
				pnode.setChildren(new ArrayList<SysMenu>());
			}
			//如果是父子关系，为父节点增加子节点，退出for循环
			if (pnode.getMenuId().equals(node.getMenuPid())) {
				pnode.setState("closed") ;//关闭二级树
				pnode.getChildren().add(node) ;
				return ;
			} else {
				//如果不是父子关系，删除父节点栈里当前的节点，
				//继续此次循环，直到确定父子关系或不存在退出for循环
				parentnodes.remove(i) ;
			}
		}
	}
}
