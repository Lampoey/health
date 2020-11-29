package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 包名：com.itheima.health.service.impl
 *
 * @Auther: Ou
 * 日期: 2020/11/24/20:34
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //先添加套餐
        setmealDao.add(setmeal);
        //获取套餐的id
        Integer setmealId = setmeal.getId();
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
        }
    }

    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
       Page<Setmeal> page= setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<Setmeal>(page.getTotal(),page.getResult());
    }

    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(int id) {
        return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //更新套餐信息
        setmealDao.update(setmeal);
        //删除旧关系、
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        //添加新关系
        if (null != checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(),checkgroupId);
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) throws MyException {
        int count = setmealDao.findOrderCountBySetmealId(id);
        if (count>0) {
            throw new MyException("已经有订单使用了这个套餐，不能删除");
        }
        setmealDao.deleteSetmealCheckGroup(id);
        setmealDao.deleteById(id);
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findDetailById(int id) {
        return setmealDao.findDetailById(id);
    }
}
