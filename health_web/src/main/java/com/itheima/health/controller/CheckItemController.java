package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 包名：com.itheima.health.controller
 *
 * @Auther: Ou
 * 日期: 2020/11/21/18:00
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckItem> list = checkItemService.findAll();
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS, list);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //分页查询
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    //删除检查项
    @GetMapping("/deleteById")
    public Result deleteById(int id) {
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    /*
    *
    * 回响数据，通过id查询
    * */
    @GetMapping("/findById")
    public Result findById(int id){
       CheckItem checkItem = checkItemService.findById(id);
       return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }
    /*
    * 表单项的修改
    * */
    @PostMapping("update")
    public Result update(@RequestBody CheckItem checkItem,Integer[] checkitemIds){
        checkItemService.update(checkItem);
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
}
