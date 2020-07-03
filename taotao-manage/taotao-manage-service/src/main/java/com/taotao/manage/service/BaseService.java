package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by ning_ on 2020/6/18.
 */
public class BaseService<T extends BasePojo> {
    @Autowired
    public Mapper<T> mapper;
    /**
     * 参数为null,查询所有
     *
     * @return
     */

    public List<T> queryAll() {
        return this.mapper.select(null);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public T queryById(Long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /**
     * 根据条件查询单个对象,返回多个会报错,调用queryList
     *
     * @param record
     * @return
     */
    public T queryOne(T record) {
        return this.mapper.selectOne(record);
    }

    /**
     * 根据条件查询多个对象
     *
     * @param record
     * @return
     */
    public List<T> queryListByWhere(T record) {
        return this.mapper.select(record);
    }

    /**
     * 根据条件分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param record
     * @return
     */
    public PageInfo<T> queryPageListByWhere(Integer pageNum, Integer pageSize, T record) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = this.mapper.select(record);
        return new PageInfo<T>(list);
    }

    /**
     * 分页查询(含排序)
     * @param orderByClause
     * @param pageNum
     * @param pageSize
     * @param record
     * @return
     */
    public PageInfo<T> queryPageListByWhere(String orderByClause,Integer pageNum, Integer pageSize, T record) {

        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(record.getClass());
        example.setOrderByClause(orderByClause);
        List<T> list = this.mapper.selectByExample(example);
        return new PageInfo<T>(list);
    }

    /**
     * 新增单个对象
     *
     * @param record
     * @return
     */
    public Boolean save(T record) {
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.mapper.insertSelective(record) == 1;
    }

    /**
     * 根据主键更新对象
     *
     * @param record
     * @return
     */
    public Boolean updateByPrimaryKey(T record) {
        record.setUpdated(new Date());
        return this.mapper.updateByPrimaryKeySelective(record) == 1;
    }

    /**
     * 根据主键删除对象
     *
     * @param id
     * @return
     */
    public Boolean deleteByPrimaryKey(Object id) {
        return this.mapper.deleteByPrimaryKey(id) == 1;
    }

    /**
     * 根据条件批量删除一个List的对象(大概率是根据id)
     *
     * @param property
     * @param ids
     * @return
     */
    public Integer deleteByIds(Class clazz,String property, List<Object> ids) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, ids);
        return this.mapper.deleteByExample(example);
    }

    /**
     * 根据条件删除对象(一个或者多个)
     *
     * @param record
     * @return
     */
    public Integer deleteByWhere(T record) {
        return this.mapper.delete(record);
    }

}
