package com.renren.demo.netapi;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author peng.chen5@renren-inc.com 2018/3/5 11:07
 */
public class IODemo {

    public static void bio() {
        InputStream in = null;
        try {
            in = new FileInputStream("/Users/chenpeng/Downloads/mst.txt");

            byte[] buf = new byte[1024];
            int byteRead = in.read(buf);
            while (byteRead != -1) {
                System.out.println(new String(buf, "UTF-8"));
                byteRead = in.read(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void nio(){
        RandomAccessFile aFile = null;
        try{
            aFile = new RandomAccessFile("/Users/chenpeng/Downloads/mst.txt" , "r");
            FileChannel channel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);

            int byteRead = channel.read(buf);
            System.out.println(byteRead);

            while (byteRead != -1){
                buf.flip();
                while (buf.hasRemaining()){
                    System.out.print((char)buf.get());
                }
                buf.compact();
                byteRead = channel.read(buf);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void MappedByteBufferIO(){
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try{
            aFile = new RandomAccessFile("/Users/chenpeng/Downloads/mst.txt", "r");
            fc = aFile.getChannel();
            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY ,  0 ,aFile.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                if(aFile!=null){
                    aFile.close();
                }
                if(fc != null){
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        nio();
    }
}
