package com.luolong.opensource.java.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;

/**
 * <p></p>
 *
 * @author luolong
 * @date 2019/1/29
 */
@org.springframework.stereotype.Service
public class ServiceTest {
    @Resource
    BaseService2 zidingyiService;

    @Autowired
    @Qualifier("service")
    Service service;


}
