package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 包名：com.itheima.health.service.impl
 *
 * @Auther: Ou
 * 日期: 2020/11/25/23:28
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    public OrderSettingDao orderSettingDao;

    @Override
    @Transactional
    public void add(List<OrderSetting> excelList) {
        //遍历集合
        if (null != excelList) {
            for (OrderSetting orderSetting : excelList) {
                //通过日期来查询
                OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                if (null != osInDB) {
                    //判断预约设置，可预约数要大于已预约数
                    if (osInDB.getReservations() > orderSetting.getNumber()) {
                        throw new MyException("更新后的最大预约数，不能小于已预约数");
                    }
                    orderSettingDao.updateNumber(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map<String, Integer>> getDataByMonth(String month) {
        month += "%";
        return orderSettingDao.getDataByMonth(month);
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        if (null != osInDB) {
            //判断预约设置，可预约数要大于已预约数
            if (osInDB.getReservations() > orderSetting.getNumber()) {
                throw new MyException("更新后的最大预约数，不能小于已预约数");
            }
            orderSettingDao.updateNumber(orderSetting);
        } else {
            orderSettingDao.add(orderSetting);
        }
    }
}
