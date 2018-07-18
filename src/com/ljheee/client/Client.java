package com.ljheee.client;

import com.ljheee.service.EchoService;

import java.lang.reflect.Proxy;

/**
 * Created by lijianhua04 on 2018/7/13.
 *
 *
 * https://zhuanlan.zhihu.com/p/26216380
 */
public class Client {
    public static void main(String[] args) {

        EchoService echo = (EchoService) Proxy.newProxyInstance(EchoService.class.getClassLoader(),
                new Class<?>[]{EchoService.class}, new DynamicProxyHandler());

        for (int i = 0; i < 3; i++) {
            System.out.println(echo.echo(String.valueOf(i)));
        }
    }



}
