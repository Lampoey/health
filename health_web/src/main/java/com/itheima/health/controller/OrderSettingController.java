package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 包名：com.itheima.health.controller
 *
 * @Auther: Ou
 * 日期: 2020/11/25/22:41
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;
    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);
    @PostMapping("/upload")
    //接收上传的文件
    public Result upload(MultipartFile excelFile){
        //使用工具类解析excel文件，使用List<String[]>接收
        try {
            List<String[]> excelArrList = POIUtils.readExcel(excelFile);
            final SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            //将List<String[]>转成List<OrderSetting>
            //通过流的方式和Map来转换
            List<OrderSetting> excelList= excelArrList.stream().map(excelArr->{
                OrderSetting os = new OrderSetting();
                //日期字符串
                String excelDateStr = excelArr[0];
                try {
                    os.setOrderDate(sdf.parse(excelDateStr));
                } catch (ParseException e) {}
                //最大预约数
               os.setNumber(Integer.valueOf(excelArr[1]));
                return os;
            }).collect(Collectors.toList());
            orderSettingService.add(excelList);
        } catch (Exception e) {
            log.error("导入预约数据失败",e);
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }
    /*
    * 日历展示
    * */
    @GetMapping("/getDataByMonth")
    public Result getDataByMonth(String month){
        List<Map<String,Integer>> data = orderSettingService.getDataByMonth(month);
        return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,data);
    }
    /**
     * 日历的预约设置
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}