package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 包名：com.itheima.health.controller
 *
 * @Auther: Ou
 * 日期: 2020/11/24/17:27
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;
    private static final Logger log = LoggerFactory.getLogger(SetmealController.class);
    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        //获取文件名
        String originalFilename = imgFile.getOriginalFilename();
        //截取文件名的后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //产生唯一的文件名
        String fileName= UUID.randomUUID()+suffix;
        //使用工具类上传
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),fileName);
            Map<String,String> resultMap = new HashMap<String, String>();
            resultMap.put("imgName",fileName);
            resultMap.put("domain",QiNiuUtils.DOMAIN);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS,resultMap);
        } catch (IOException e) {
            log.error("上传文件失败",e);
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
    //添加套餐
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setmealService.add(setmeal,checkgroupIds);
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }
    //套餐的分页查询
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }
    //查询套餐
    @GetMapping("/findById")
    public Result findById(int id){
        Setmeal setmeal = setmealService.findById(id);
        //解决图片的全路径问题，所以封装到Map
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("setmeal",setmeal);
        resultMap.put("domain",QiNiuUtils.DOMAIN);
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,resultMap);
    }
    @GetMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(int id){
        List<Integer> checkgroupIds = setmealService.findCheckgroupIdsBySetmealId(id);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroupIds);
    }
    //修改套餐
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setmealService.update(setmeal,checkgroupIds);
        return new Result(true,"更新套餐成功");
    }
    //删除套餐
    @GetMapping("/deleteById")
    public Result deleteById(int id){
        setmealService.deleteById(id);
        return new Result(true,"删除套餐成功");
    }
}
