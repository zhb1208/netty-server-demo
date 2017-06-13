package com.jason.utils;

import io.netty.buffer.ByteBuf;

/**
 * byte 处理工具类
 *
 * @author zhanghongbing
 * @version 15/6/4
 */
public class ByteUtils {

    /**
     * ByteBuf转换byte
     * @param datas
     * @return
     */
    public static byte[] read(ByteBuf datas) {
        byte[] bytes = new byte[datas.readableBytes()];
        datas.readBytes(bytes);
        return bytes;
    }


    /**
     *  合并两个byte数组
     * @param firstByte
     * @param secondByte
     * @return
     */
    public static byte[] byteMerger(byte[] firstByte, byte[] secondByte){
        byte[] mergerByte = new byte[firstByte.length+secondByte.length];
        System.arraycopy(firstByte, 0, mergerByte, 0, firstByte.length);
        System.arraycopy(secondByte, 0, mergerByte, firstByte.length, secondByte.length);
        return mergerByte;
    }
}
