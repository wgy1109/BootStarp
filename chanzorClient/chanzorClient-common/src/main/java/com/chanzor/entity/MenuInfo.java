package com.chanzor.entity;

import java.io.Serializable;
import java.util.List;
/**
 * 
* <p>Title: MenuInfo.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: langyu</p>
* @author jian.zhang
* @date 2014年6月7日
* @version 1.0
 */

public class MenuInfo implements Serializable  {
	private static final long serialVersionUID = 5889386751260557020L;
	private int menu_id;
	private String menuName;
	private String menu_url;
	private int parentid;
	private MenuInfo parentMenu;
	private List<MenuInfo> subMenu;
	private boolean hasMenu = false;
	private Page page;
	
	


	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public MenuInfo getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(MenuInfo parentMenu) {
		this.parentMenu = parentMenu;
	}
	public List<MenuInfo> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<MenuInfo> subMenu) {
		this.subMenu = subMenu;
	}
	public boolean isHasMenu() {
		return hasMenu;
	}
	public void setHasMenu(boolean hasMenu) {
		this.hasMenu = hasMenu;
	}
	

}
