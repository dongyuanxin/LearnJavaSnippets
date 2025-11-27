package oop.demo.buffer;

import java.nio.ByteBuffer;

public class BufferOpDemo {
    public static void main(String[] args) {
        // 通过wrap包装方法，快速初始化
        System.out.println(ByteBuffer.wrap("hello".getBytes()));

        // 正常初始化：allocate（堆上创建）+ put（写入数据）
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("初始时-->limit--->"+byteBuffer.limit());
        System.out.println("初始时-->position--->"+byteBuffer.position());
        System.out.println("初始时-->capacity--->"+byteBuffer.capacity());

        byteBuffer.put("hello".getBytes());
        byteBuffer.put((byte)'.');
        System.out.println("put完之后-->limit--->"+byteBuffer.limit());
        System.out.println("put完之后-->position--->"+byteBuffer.position());
        System.out.println("put完之后-->capacity--->"+byteBuffer.capacity());

        // position -> 0, limit -> 6 ：切换到读模式
        byteBuffer.flip();
        System.out.println("flip()方法之后-->limit--->"+byteBuffer.limit());
        System.out.println("flip()方法之后-->position--->"+byteBuffer.position());
        System.out.println("flip()方法之后-->capacity--->"+byteBuffer.capacity());
        while (byteBuffer.hasRemaining()) {
            byte b = byteBuffer.get();
            System.out.println((char)b + "(byte=" + b + ")");
        }
        // position -> 0，重新操作(rewind翻译过来是倒带的意思）
        byteBuffer.rewind();
        while (byteBuffer.hasRemaining()) {
            byte b = byteBuffer.get();
            System.out.println((char)b + "(byte=" + b + ")");
        }

        byteBuffer.position(1);
        byteBuffer.mark();
        System.out.println("mark()方法之后-->position--->" + byteBuffer.position());
        byteBuffer.get();
        System.out.println("mark()方法之后再读一个-->position--->" + byteBuffer.position());
        byteBuffer.reset();
        System.out.println("mark()方法之后再reset回去-->position--->" + byteBuffer.position());


        // position -> 0，limit -> 1024 ：切换到写模式
        byteBuffer.clear();
        System.out.println("clear()方法之后-->limit--->"+byteBuffer.limit());
        System.out.println("clear()方法之后-->position--->"+byteBuffer.position());
        System.out.println("clear()方法之后-->capacity--->"+byteBuffer.capacity());
    }
}
