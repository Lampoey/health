package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 包名：com.itheima.health.controller
 *
 * @Auther: Ou
 * 日期: 2020/11/28/18:29
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    private SetmealService setmealService;
    @GetMapping("/getSetmeal")
    //查询所有套餐
    public Result getSetmeal(){
      List<Setmeal> list = setmealService.findAll();
      //拼接图片的全路径
        list.forEach(s->{
            s.setImg(QiNiuUtils.DOMAIN+s.getImg());
        });
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
    }

    /*
    * 查询套餐详情
    * */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id){
       Setmeal setmeal = setmealService.findDetailById(id);
       //拼接图片的全路径
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
       return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }
}
