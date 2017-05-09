package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Xin on 5/9/2017.
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryMangeController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategroy(HttpSession session, String categroy, @RequestParam(value ="parentId",defaultValue = "0") int parentId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(null == user) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        //检查是否是管理员用户
        if(iUserService.checkAdminRole(user).isSuccess()) {
            //create the Category
            return iCategoryService.addCategory(categroy,parentId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, String categoryName, Integer categoryId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(null == user) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }

        //检查是否是管理员用户
        if(iUserService.checkAdminRole(user).isSuccess()) {
            //更新Category Name
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId ){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(null == user) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }

        //检查是否是管理员用户
        if(iUserService.checkAdminRole(user).isSuccess()) {
            //获取同一个级别的category,不获取深层的category 只获取同一级别
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategroyAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId ){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(null == user) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }

        //检查是否是管理员用户
        if(iUserService.checkAdminRole(user).isSuccess()) {
            //查询当前节点的id和递归子节点的id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
}
