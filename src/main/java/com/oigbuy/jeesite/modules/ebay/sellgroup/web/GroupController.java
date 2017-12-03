/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.oigbuy.jeesite.modules.ebay.sellgroup.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oigbuy.jeesite.common.config.Global;
import com.oigbuy.jeesite.common.persistence.Page;
import com.oigbuy.jeesite.common.repeaturl.RepeatUrlData;
import com.oigbuy.jeesite.common.utils.StringUtils;
import com.oigbuy.jeesite.common.web.BaseController;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.Group;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.GroupSeller;
import com.oigbuy.jeesite.modules.ebay.sellgroup.entity.GroupUser;
import com.oigbuy.jeesite.modules.ebay.sellgroup.service.GroupService;
import com.oigbuy.jeesite.modules.ebay.template.entity.Seller;
import com.oigbuy.jeesite.modules.ebay.template.service.SellerService;
import com.oigbuy.jeesite.modules.sys.entity.User;
import com.oigbuy.jeesite.modules.sys.service.SystemService;
import com.oigbuy.jeesite.modules.sys.utils.UserUtils;

/**
 * 销售组Controller
 * @author handong.wang
 * @version 2017-10-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sellgroup/group")
public class GroupController extends BaseController {

	@Autowired
	private GroupService groupService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public Group get(@RequestParam(required=false) String id) {
		Group entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = groupService.get(id);
			entity.setUserList(groupService.getUserList(id));
			entity.setSellerList(groupService.getSellerList(id));
		}
		if (entity == null){
			entity=new Group();
		}
		return entity;
	}
	
	@RequiresPermissions("sellgroup:group:view")
	@RequestMapping(value = {"list", ""})
	public String list(Group group, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Group> page = groupService.findPage(new Page<Group>(request, response), group); 
		model.addAttribute("page", page);
		return "ebay/sellgroup/groupList";
	}

	@RequiresPermissions("sellgroup:group:view")
	@RequestMapping(value = "form")
	public String form(Group group, Model model) {
		model.addAttribute("group", group);
		List<Seller> sellerList=  sellerService.findList(null);
		List<User> userList = this.systemService.findAllUser(UserUtils.getUser());
		model.addAttribute("sellerList", sellerList);
		model.addAttribute("userList", userList);
		return "ebay/sellgroup/groupForm";
	}
	@RepeatUrlData
	@RequiresPermissions("sellgroup:group:edit")
	@RequestMapping(value = "save")
	public String save(Group group, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, group)){
			return form(group, model);
		}
		if(group.getGroupId() != null){
			groupService.updateAll(group);
		}else{
			groupService.save(group);
		}
		if(group.getSellerList() != null && !group.getSellerList().isEmpty()){
			List<GroupSeller> groupSellers  = new ArrayList<GroupSeller>();
			for(Integer str : group.getSellerList()){
				GroupSeller seller = new GroupSeller();
				seller.setGroupId(group.getGroupId());
				seller.setSellerId(str);
				groupSellers.add(seller);
			}
			groupService.saveGroupSeller(groupSellers);
		}
		
		if(group.getUserList() != null && !group.getUserList().isEmpty()){
			List<GroupUser> groupUsers  = new ArrayList<GroupUser>();
			for(Integer str : group.getUserList()){
				GroupUser user = new GroupUser();
				user.setGroupId(group.getGroupId());
				user.setUserId(str);
				groupUsers.add(user);
			}
			groupService.saveGroupUser(groupUsers);
		}
		
		addMessage(redirectAttributes, "保存销售组成功");
		return "redirect:"+Global.getAdminPath()+"/sellgroup/group/?repage";
	}
	
	@RequiresPermissions("sellgroup:group:edit")
	@RequestMapping(value = "delete")
	public String delete(Group group, RedirectAttributes redirectAttributes) {
		if(group.getGroupId() != null){
			groupService.deleteAll(group.getGroupId());
		}
		addMessage(redirectAttributes, "删除销售组成功");
		return "redirect:"+Global.getAdminPath()+"/sellgroup/group/?repage";
	}

}