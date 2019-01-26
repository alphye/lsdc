package com.lishengzn.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ByteUtil.class);
    /**
     * int转byte[] 小 端
     *
     * @param num
     * @return
     */
    public static byte[] intToBytes_s(int num) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (0xff & (num >> 0));
        bytes[1] = (byte) (0xff & (num >> 8));
        bytes[2] = (byte) (0xff & (num >> 16));
        bytes[3] = (byte) (0xff & (num >> 24));
        return bytes;

    }

    /**
     * byte[]转int 小端
     *
     * @param bytes
     * @return
     */
    public static int bytesToInt_s(byte[] bytes) {
        int num = 0;
        int temp;
        temp = (0x000000ff & (bytes[0])) << 0;
        num = num | temp;

        temp = (0x000000ff & (bytes[1])) << 8;
        num = num | temp;

        temp = (0x000000ff & (bytes[2])) << 16;
        num = num | temp;

        temp = (0x000000ff & (bytes[3])) << 24;
        num = num | temp;

        return num;
    }

    /**
     * int转byte[] 大端
     *
     * @param a
     * @return
     */
    public static byte[] intToBytes(int a) {
        return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF) };
    }

    /**
     * byte[]转int 大端
     *
     * @param src
     * @return
     */
    public static int bytesToInt(byte[] src) {
        int value;
        value = (int) (((src[0] & 0xFF) << 24) | ((src[1] & 0xFF) << 16) | ((src[2] & 0xFF) << 8) | (src[3] & 0xFF));
        return value;

    }

    /**
     * int转byte[]的另一种实现方式，大端
     *
     * @param x
     * @return
     */
    public static byte[] int2Bytes(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        ByteOrder bo = ByteOrder.BIG_ENDIAN;
        buffer.order(bo);
        buffer.putInt(x);
        return buffer.array();
    }

    /**
     * byte[]转int 的另一种实现方式 大端
     *
     * @param src
     * @param src
     * @return
     */
    public static int bytes2Int(byte[] src) {
        ByteBuffer buffer = ByteBuffer.wrap(src);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getInt();
    }

    /**
     * short转byte[]的另一种实现方式，大端
     *
     * @param x
     * @return
     */
    public static byte[] short2Bytes(short x) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        ByteOrder bo = ByteOrder.BIG_ENDIAN;
        buffer.order(bo);
        buffer.putShort(x);
        return buffer.array();
    }

    /**byte[]转short 大端
     * @param src
     * @return
     */
    public static short bytes2Short(byte[] src) {
        ByteBuffer buffer = ByteBuffer.wrap(src);
        buffer.order(ByteOrder.BIG_ENDIAN);
        return buffer.getShort();
    }

    /**
     * double 转 byte[] 小端
     *
     * @param d
     * @return
     */
    public static byte[] doubleToBytes_l(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;

    }

    /**
     * byte[] 转double 小端
     *
     * @param arr
     * @return
     */
    public static double bytesToDouble_l(byte[] arr) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (arr[i] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }

    /**
     * double 转 byte[] 小端
     *
     * @param d
     * @return
     */
    public static byte[] doubleToBytes(double d) {
        long longbits = Double.doubleToLongBits(d);
        return longToBytes(longbits);
    }

    /**
     * byte[] 转double 小端
     *
     * @param arr
     * @return
     */
    public static double bytesToDouble(byte[] arr) {
        return Double.longBitsToDouble(bytesToLong(arr));
    }
    /**
     * double 转 byte[]
     *
     * @param d
     * @return
     */
    public static byte[] doubleToBytes(double d,ByteOrder byteOrder) {
        long longbits = Double.doubleToLongBits(d);
        return longToBytes(longbits,byteOrder);
    }

    /**
     * byte[] 转double
     *
     * @param arr
     * @return
     */
    public static double bytesToDouble(byte[] arr,ByteOrder byteOrder) {
        return Double.longBitsToDouble(bytesToLong(arr,byteOrder));
    }
    // byte 数组与 long 的相互转换 大端
    /**
     * long 转byte[] 小端
     *
     * @param x
     * @return
     */
    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(0, x);
        return buffer.array();
    }
    /**
     * long 转byte[]
     *
     * @param x
     * @return
     */
    public static byte[] longToBytes(long x,ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(byteOrder);
        buffer.putLong(0, x);
        return buffer.array();
    }

    /**
     * byte[] 转long 小端
     *
     * @param bytes
     * @return
     */
    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();// need flip
        return buffer.getLong();
    }
    /**
     * byte[] 转long
     *
     * @param bytes
     * @return
     */
    public static long bytesToLong(byte[] bytes,ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(byteOrder);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();// need flip
        return buffer.getLong();
    }
    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] hexToBytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        if (str.length() % 2 == 1) {
            // 奇数
            str = "0" + str;
        }

        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }

    /**
     * byte[] to hex string
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) { // 使用String的format方法进行转换
            buf.append(String.format("%02x", new Integer(b & 0xff)));
        }

        return buf.toString();
    }

    public static boolean byteToBoolean(byte byt){
        boolean b = (byt == 0x00) ? false : true;
        return b;
    }

    public static byte booleanToByte(boolean b){
        byte byt = (byte) (b ? 0x01 : 0x00);
        return byt;
    }

    public static boolean bytesToBoolean(byte[] bytes){
        if(bytes.length>1){
            throw new RuntimeException("boolean 长度应为1");
        }
        return byteToBoolean(bytes[0]);
    }

    public static byte[] booleanToBytes(boolean b){
        return new byte[]{booleanToByte(b)};
    }
}
