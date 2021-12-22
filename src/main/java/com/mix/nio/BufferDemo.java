package com.mix.nio;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class BufferDemo {

    public void demo(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String msg = "mmmmmssssssggggggg";
        //包装一个byte[]数组获得一个Buffer，实际类型是HeapByteBuffer
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(msg.getBytes());
        //创建堆外内存块DirectByteBuffer
        ByteBuffer byteBuffer3 = ByteBuffer.allocateDirect(1024);
    }

    public static void main(String[] args) throws Exception {
        String msg = "java技术爱好者，起飞！";
        //创建一个固定大小的buffer(返回的是HeapByteBuffer)
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] bytes = msg.getBytes();
        //写入数据到Buffer中
        byteBuffer.put(bytes);
        //切换成读模式，关键一步
        byteBuffer.flip();
        //创建一个临时数组，用于存储获取到的数据
        byte[] tempByte = new byte[bytes.length];
        int i = 0;
        //如果还有数据，就循环。循环判断条件
        while (byteBuffer.hasRemaining()) {
            //获取byteBuffer中的数据
            byte b = byteBuffer.get();
            //放到临时数组中
            tempByte[i] = b;
            i++;
        }
        //打印结果
        System.out.println(new String(tempByte));//java技术爱好者，起飞！
    }

    public void fileRead() throws Exception{
        //获取文件输入流
        File file = new File("1.txt");
        FileInputStream inputStream = new FileInputStream(file);
        //从文件输入流获取通道
        FileChannel inputStreamChannel = inputStream.getChannel();
        //获取文件输出流
        FileOutputStream outputStream = new FileOutputStream(new File("2.txt"));
        //从文件输出流获取通道
        FileChannel outputStreamChannel = outputStream.getChannel();
        //创建一个byteBuffer，小文件所以就直接一次读取，不分多次循环了
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
        //把输入流通道的数据读取到缓冲区
        inputStreamChannel.read(byteBuffer);
        //切换成读模式
        byteBuffer.flip();
        //把数据从缓冲区写入到输出流通道
        outputStreamChannel.write(byteBuffer);
        //关闭通道
        outputStream.close();
        inputStream.close();
        outputStreamChannel.close();
        inputStreamChannel.close();

    }

    public void gatheringRead() throws Exception {
        //获取文件输入流
        File file = new File("1.txt");
        FileInputStream inputStream = new FileInputStream(file);
        //从文件输入流获取通道
        FileChannel inputStreamChannel = inputStream.getChannel();
        //获取文件输出流
        FileOutputStream outputStream = new FileOutputStream(new File("2.txt"));
        //从文件输出流获取通道
        FileChannel outputStreamChannel = outputStream.getChannel();
        //创建三个缓冲区，分别都是5
        ByteBuffer byteBuffer1 = ByteBuffer.allocate(5);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(5);
        ByteBuffer byteBuffer3 = ByteBuffer.allocate(5);
        //创建一个缓冲区数组
        ByteBuffer[] buffers = new ByteBuffer[]{byteBuffer1, byteBuffer2, byteBuffer3};
        //循环写入到buffers缓冲区数组中，分散读取
        long read;
        long sumLength = 0;
        while ((read = inputStreamChannel.read(buffers)) != -1) {
            sumLength += read;
            Arrays.stream(buffers)
                    .map(buffer -> "posstion=" + buffer.position() + ",limit=" + buffer.limit())
                    .forEach(System.out::println);
            //切换模式
            Arrays.stream(buffers).forEach(Buffer::flip);
            //聚合写入到文件输出通道
            outputStreamChannel.write(buffers);
            //清空缓冲区
            Arrays.stream(buffers).forEach(Buffer::clear);
        }
        System.out.println("总长度:" + sumLength);
        //关闭通道
        outputStream.close();
        inputStream.close();
        outputStreamChannel.close();
        inputStreamChannel.close();

    }
}
