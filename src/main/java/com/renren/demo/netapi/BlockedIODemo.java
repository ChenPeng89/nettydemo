package com.renren.demo.netapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/4 下午2:53
 */
public class BlockedIODemo {

    public void singleThread()  {
        ServerSocket serverSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            serverSocket = new ServerSocket(8080);
            Socket client = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream() , true);
            String request , response;
            while((request = in.readLine()) != null){
                if("Done".equals(request)){
                    break;
                }
                response = proccessRequest(request);
                out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            out.close();
        }

    }

    public void multiThread(){


    }

    private String proccessRequest(String request){
        return request;
    }
}
