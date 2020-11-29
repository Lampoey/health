package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 包名：com.itheima.health.controller
 *
 * @Auther: Ou
 * 日期: 2020/11/23/13:16
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.add(checkGroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    /*
    * 检查组的分页查询
    * */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }
    /*
    * 检查组查询
    * */
    @RequestMapping("/findById")
    public Result findById(int id){
      CheckGroup checkGroup =  checkGroupService.findById(id);
      return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }
    @RequestMapping("/findCheckItemIdByCheckGroupId")
    public Result findCheckItemIdByCheckGroupId(int id){
      List<Integer> checkItemIds = checkGroupService.findCheckItemIdByCheckGroupId(id);
      return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
    }
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        checkGroupService.update(checkGroup,checkitemIds);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    @GetMapping("/deleteById")
    public Result deleteById(int id){
        checkGroupService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
    @GetMapping("/findAll")
    public Result findAll(){
       List<CheckGroup> list = checkGroupService.findAll();
       return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }
}
