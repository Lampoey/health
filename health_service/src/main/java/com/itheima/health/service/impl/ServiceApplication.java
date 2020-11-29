package com.itheima.health.service.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 包名：com.itheima.service
 *
 * @Auther: Ou
 * 日期: 2020/11/21/16:41
 */
public class ServiceApplication {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:applicationContext-service.xml");
        System.in.read();
    }
}