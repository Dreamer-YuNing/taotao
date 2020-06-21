package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by ning_ on 2020/6/21.
 *
 */
@Service
public class PropertiesService {
    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;
    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
}
