package com.taotao.manage.service;

import com.taotao.manage.mapper.ContentCategoryMapper;
import com.taotao.manage.pojo.ContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ning_ on 2020/6/28.
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory> {
    @Autowired
    private ContentCategoryMapper mapper;
}
