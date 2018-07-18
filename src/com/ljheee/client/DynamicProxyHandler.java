package com.ljheee.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by lijianhua04 on 2018/7/13.
 */
public class DynamicProxyHandler implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        Socket s = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            s = new Socket();
            s.connect(new InetSocketAddress("127.0.0.1", 8088));
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());

            oos.writeUTF("com.ljheee.service.EchoServiceImpl");
            oos.writeUTF(method.getName());
            oos.writeObject(method.getParameterTypes());
            oos.writeObject(args);

            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (s != null)
                s.close();

            if (ois != null)
                ois.close();

            if (oos != null)
                oos.close();
        }

        return null;
    }
}
