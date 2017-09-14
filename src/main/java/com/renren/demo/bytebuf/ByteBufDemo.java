package com.renren.demo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @author : peng.chen5@renren-inc.com
 * @Time : 2017/9/6 上午10:22
 */
public class ByteBufDemo {

    /**
     * sliced后的bytebuf和原bytebuf的数据是共享的
     */
    public static void sliceDemo(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!" , utf8);
        ByteBuf sliced1 = buf.slice();
        System.out.println(sliced1.toString(utf8));
        ByteBuf sliced = buf.slice(0 , 15);
        System.out.println(sliced.toString(utf8));
        buf.setByte(0 , (byte)'J');
        System.out.println(buf.getByte(0) == sliced.getByte(0));
    }

    /**
     * copy后的bytebuf是重新分配的新的bytebuf
     */
    public static void copyDemo(){
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!" , utf8);
        ByteBuf copy = buf.copy(0 , 15);
        System.out.println(copy.toString(utf8));
        buf.setByte(0 , 'J');
        System.out.println(buf.getByte(0) == copy.getByte(0));
    }


    public static void main(String[] args) {
//        sliceDemo();
//        copyDemo();
    }
}
