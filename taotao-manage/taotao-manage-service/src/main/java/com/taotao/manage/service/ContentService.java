package com.taotao.manage.service;

import com.taotao.manage.mapper.ContentMapper;
import com.taotao.manage.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ning_ on 2020/6/28.
 */
@Service
public class ContentService extends BaseService<Content>{
    @Autowired
    private ContentMapper mapper;
}
