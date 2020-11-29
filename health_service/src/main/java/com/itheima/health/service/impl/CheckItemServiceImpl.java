package com.itheima.health.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 包名：com.itheima.service.impl
 *
 * @Auther: Ou
 * 日期: 2020/11/21/18:06
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断是否有查询条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //根据查询条件进行查询
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        //将查询到的总条数和当前页的结果集封装到pageresult中
        PageResult<CheckItem> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public void deleteById(int id){
        //判断检查组是否使用了
        int count = checkItemDao.findCountByCheckItemId(id);
        //如果count>0就被使用了
        if (count>0) {
            throw new MyException("该检查项被检查组使用了，不能删除");
        }
        //没有使用就删除
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
