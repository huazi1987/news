package com.news.common.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.news.common.util.CommonStatic;
import com.news.common.json.model.TreeNode;

public class TreeJson implements Serializable {

	/**
	 * @Description:
	 * @author 王慧智
	 * @date 2014-4-30
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 构建树形结构
	 * @Description: 
	 * @author wanghz
	 * @date 2015年2月2日
	 * @param list
	 * @return
	 */
	public static List<TreeNode> formatTree(List<TreeNode> list) {
		return formatTree(list, false,null);
	}
	
	/**
	 * 构建带复选框的树形结构
	 * @Description: 
	 * @author wanghz
	 * @date 2015年9月22日
	 * @param list
	 * @param chkList
	 * @return
	 */
	public static List<TreeNode> formatTree(List<TreeNode> list,List<TreeNode> chkList) {
		return formatTree(list, false,chkList);
	}

	/**
	 * 构建树形结构
	 * @Description: 
	 * @author wanghz
	 * @date 2015年2月2日
	 * @param list(遍历的数组)
	 * @param isShowRoot(是否显示根目录)
	 * @param chkTreeList(是否checked迭代数组)
	 * @return
	 */
	private static List<TreeNode> formatTree(List<TreeNode> list,boolean isShowRoot,List<TreeNode> chkTreeList) {

		List<TreeNode> treelist = new ArrayList<TreeNode>();// 拼凑好的json格式的数据

		if(isShowRoot){
			TreeNode root = new TreeNode();
			root.setId(CommonStatic.MENU_ROOT);
			root.setText(CommonStatic.MENU_NAME);
			treelist.add(root);
		}

		if (list != null && list.size() > 0) {
			for(TreeNode node1 : list){
				if(chkTreeList !=null && chkTreeList.size()>0){//处理复选框回显
					for (TreeNode treeNode : chkTreeList) {
						if(node1.getId().equals(treeNode.getId())){
							node1.setChecked(true);
							chkTreeList.remove(treeNode);
							break;
						}
					}
				}
				boolean mark = false;  
				for(TreeNode node2 : list){  
					if(node1.getPid()!=null && node1.getPid().equals(node2.getId())){  
						mark = true;  
						if(node2.getChildren() == null){
							node2.setChildren(new ArrayList<TreeNode>());
						}
						node2.getChildren().add(node1);   
						break;  
					}  
				}  
				if(!mark){
					treelist.add(node1);   
				}  
			}
		}
		return treelist;
	}
}


