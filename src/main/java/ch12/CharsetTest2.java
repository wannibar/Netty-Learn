package ch12;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by WQS on 2017/8/18.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class CharsetTest2 {
    public static void main(String[] args) throws IOException {

        ByteBuffer inMB = ByteBuffer.wrap("\uD83D\uDE04A严".getBytes("UTF-8"));
        showBytes(inMB);
        System.out.println("=============");

        String charsetName = "ISO-8859-1";
        Charset charset = Charset.forName(charsetName);
        CharsetDecoder decoder = charset.newDecoder();  //字节转为字符,
        CharsetEncoder encoder = charset.newEncoder();

        // 把inMB里面的字节序列转为一系列字符,其中每个字符是inMB有效的字节序列解释在decoder对应的字符集的二进制值,而不是其在计算机底层存储的二进制值
        CharBuffer inCB = decoder.decode(inMB);
        showBytes(inCB);

        System.out.println("================");
        // inCB表示的是在之前decoder的字符集下面的对应的二进制值(每一个用字符表示),现在用encoder对应的字符集把每个字符编码为底层的二进制表示(存储)
        ByteBuffer outBB = encoder.encode(inCB);
        showBytes(outBB);

        outBB.flip();
        System.out.println(new String(outBB.array(), "UTF-8"));
    }

    private static void showBytes(ByteBuffer outBB) {
        System.out.println("show ..:" + outBB.position() + ":" + outBB.limit() + ":" + outBB.capacity());
        while (outBB.hasRemaining()) {
            System.out.printf("%x \n", (int) outBB.get());
        }
        outBB.flip();
    }

    private static void showBytes(CharBuffer inCB) {
        System.out.println("show ..:" + inCB.position() + ":" + inCB.limit());
        while (inCB.hasRemaining()) {
            System.out.printf("%x \n", (int) inCB.get());
        }
        inCB.flip();
    }
}
