package com.chanzor.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanzor.persistence.dao.DaoSupport;
import com.chanzor.entity.MenuInfo;
import com.chanzor.entity.PageInfo;
import com.chanzor.service.MenuService;
import com.chanzor.util.FormData;
import com.chanzor.util.RightsHelper;

@Service("menuService")
@SuppressWarnings("unchecked")
public class MenuServiceImpl implements MenuService {/*
	@Resource(name = "daoSupport")
	private DaoSupport daoSupport;

	public List<Map<String, Object>> listAllMenu(PageInfo page)
			throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList(
				"MenuMapper.ParentMenulistPage", page);
	}

	public List<MenuInfo> findAllMenu(PageInfo page) throws Exception {
		return (List<MenuInfo>) daoSupport.findForList(
				"MenuMapper.ParentMenulistPage", page);
	}

	public List<MenuInfo> showAllMenu(PageInfo page) throws Exception {
		return (List<MenuInfo>) daoSupport.findForList(
				"MenuMapper.showParentMenu", page);
	}

	*//**
	 * 获取两/三级菜单 getclass = 2 取二级菜单 否则取三级
	 * 
	 * @return
	 * @throws Exception
	 *//*
	public List<Map<String, Object>> getmenuList(String getclass)
			throws Exception {
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> menuListAll = (List<Map<String, Object>>) daoSupport
				.findForList("MenuInfoMapper.getmenuList", null);
		for (Map<String, Object> map : menuListAll) {
			if ("0".equals(map.get("PARENT_ID").toString())) { // 父级ID==0 取：一级菜单
				menuList.add(map);
			}
		}

		List<Map<String, Object>> subList = null;
		for (Map<String, Object> mapleft : menuList) {
			subList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : menuListAll) {
				if (!"0".equals(map.get("PARENT_ID").toString())) {
					if (mapleft.get("ID").toString()
							.equals(map.get("PARENT_ID").toString())) { // 对比父级ID
																		// =
																		// 一级菜单ID
																		// 取：二级菜单
						subList.add(map);
					}
				}
			}
			mapleft.put("subMenu", subList);
		}
		if ("2".equals(getclass)) { // 返回二级菜单
			return menuList;
		} else { // 返回三级菜单
			List<Map<String, Object>> subsonList = null;
			for (Map<String, Object> mapleft : menuList) {
				subsonList = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> subMenuList = (List<Map<String, Object>>) mapleft
						.get("subMenu");
				for (Map<String, Object> mapsub : subMenuList) {
					for (Map<String, Object> map : menuListAll) {
						if (!"0".equals(map.get("PARENT_ID").toString())
								|| !mapsub.get("ID").toString()
										.equals(map.get("ID").toString())) {
							if (mapsub.get("ID").toString()
									.equals(map.get("PARENT_ID").toString())) {
								subsonList.add(map);
							}
						}
					}
					mapsub.put("subSonList", subsonList);
				}
			}
			return menuList;
		}

	}

	public List<MenuInfo> findAllMenuList() throws Exception {
		List<MenuInfo> menuList = (List<MenuInfo>) daoSupport.findForList(
				"MenuMapper.showParentMenu", null);
		for (MenuInfo map : menuList) {
			String menuid = String.valueOf(map.getMenu_id());
			List<MenuInfo> subList = (List<MenuInfo>) daoSupport.findForList(
					"MenuMapper.listSubMenuByParentId", String.valueOf(menuid));
			map.setSubMenu(subList);
			for (MenuInfo menuInfo : subList) {
				String menuChildId = String.valueOf(menuInfo.getMenu_id());
				List<MenuInfo> subChildList = (List<MenuInfo>) daoSupport
						.findForList("MenuMapper.listSubMenuByParentId",
								String.valueOf(menuChildId));
				menuInfo.setSubMenu(subChildList);
			}
		}
		return menuList;
	}

	public List<Map<String, Object>> getMList() throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList(
				"MenuInfoMapper.getMList", null);
	}

	public List<MenuInfo> listAllSubMenu() throws Exception {
		return (List<MenuInfo>) daoSupport.findForList(
				"MenuMapper.listAllSubMenu", null);
	}

	public List<MenuInfo> listSubMenuByParentId(FormData formData)
			throws Exception {
		return (List<MenuInfo>) daoSupport.findForList(
				"MenuMapper.listSubMenuByParentId", formData);
	}

	public List<MenuInfo> findSubMenuByParentId(Integer menu_id)
			throws Exception {
		return (List<MenuInfo>) daoSupport.findForList(
				"MenuMapper.findSubMenuByParentId", menu_id);
	}

	public List<Map<String, Object>> findListSubMenuByParentId(Integer menu_id)
			throws Exception {
		return (List<Map<String, Object>>) daoSupport.findForList(
				"MenuMapper.listSubMenuByParentId", menu_id);
	}

	//
	public void saveMenu(MenuInfo menu) throws Exception {
		if (menu.getMenu_id() > 0) {
			daoSupport.update("MenuMapper.updateMenu", menu);
		} else {
			daoSupport.save("MenuMapper.insertMenu", menu);

		}

	}

	//
	public void deleteMenuById(Integer menuId) throws Exception {
		daoSupport.delete("MenuMapper.deleteMenuById", menuId);
	}

	public MenuInfo getMenuInfoById(Integer menu_id) throws Exception {
		return (MenuInfo) daoSupport.findForObject(
				"MenuMapper.getMenuInfoById", menu_id);
	}

	public Map<String, Object> getMenuById(FormData data) throws Exception {
		if (data.get("menu_id") != null
				&& !data.get("menu_id").toString().equals("")) {
			return (Map<String, Object>) daoSupport.findForObject(
					"MenuMapper.getMenuById", data);
		} else {
			return new HashMap<String, Object>();
		}
	}

	public String authMenuStr(String roleId, String userId) throws Exception {
		if (roleId == null || roleId.equals(""))
			return null;
		List<Map<String, Object>> menuUrls = (List<Map<String, Object>>) daoSupport
				.findForList("MenuInfoMapper.getAuthMenu", null);
		if (menuUrls == null || menuUrls.size() <= 0)
			return null;
		StringBuffer sb = new StringBuffer();
		String roleRights = (String) daoSupport.findForObject(
				"MenuInfoMapper.getRoleRightsByRoleid", roleId);
		String userRights = (String) daoSupport.findForObject(
				"MenuInfoMapper.getUserRightsByUserId", userId);
		if (userRights != null && !userRights.equals("")) {
			for (int i = 0; i < menuUrls.size(); i++) {
				Map<String, Object> row = menuUrls.get(i);
				int id = (Integer) row.get("ID");
				if (RightsHelper.testRights(roleRights, id)
						|| RightsHelper.testRights(userRights, id)) {
					sb.append(row.get("MENU_URL"));
				}
			}
		} else {
			for (int i = 0; i < menuUrls.size(); i++) {
				Map<String, Object> row = menuUrls.get(i);
				int id = (Integer) row.get("ID");
				if (RightsHelper.testRights(roleRights, id)) {
					sb.append(row.get("MENU_URL"));
				}
			}
		}
		return sb.toString();
	}
*/}
