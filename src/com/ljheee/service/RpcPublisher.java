package com.ljheee.service;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by lijianhua04 on 2018/7/13.
 *
 * 发布服务
 */
public class RpcPublisher {

    public static void main(String[] args) throws IOException {

        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        Socket clientSocket = null;
        ServerSocket serverSocket = new ServerSocket(8088);


        try {
            while(true){
                //等待接收 远程调用
                clientSocket = serverSocket.accept();

                ois = new ObjectInputStream(clientSocket.getInputStream());
                oos = new ObjectOutputStream(clientSocket.getOutputStream());

                String serviceName = ois.readUTF();
                String methodName = ois.readUTF();

                Class<?>[] parameterTypes = (Class<?>[]) ois.readObject();
                Object[] parameters = (Object[]) ois.readObject();


                Class<?> service = Class.forName(serviceName);
                Method method = service.getMethod(methodName, parameterTypes);
                oos.writeObject(method.invoke(service.newInstance(), parameters));
            }

        }catch (Exception e){

            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }



}
