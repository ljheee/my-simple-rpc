package com.ljheee.service;

/**
 * Created by lijianhua04 on 2018/7/13.
 */
public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String request) {
        return "echo:"+request;
    }
}
