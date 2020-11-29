package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 包名：com.itheima.health.service.impl
 *
 * @Auther: Ou
 * 日期: 2020/11/23/14:43
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组id
        Integer checkGroupId = checkGroup.getId();
        //遍历checkitemIds
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断是否有查询条件
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<CheckGroup> page= checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(int id) {

        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdByCheckGroupId(id);
    }

    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组信息
        checkGroupDao.update(checkGroup);
        //清楚旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());

        //判断检查项的id集合是否为空
        if (checkitemIds != null) {
            //遍历
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) throws MyException{
        //判断套餐和检查组有没有关联
       int count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if (count>0) {
            throw new MyException("该检查组被套餐使用，不能删除");
        }
        /*
        * 删除旧关系
        * */
        checkGroupDao.deleteCheckGroupCheckItem(id);

        checkGroupDao.deleteById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
