package ch12;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * Created by WQS on 2017/8/18.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class CharsetTest {
    public static void main(String[] args) throws IOException {
        String inputFile = "src/main/java/ch12/input.txt";
        String outputFile = "src/main/java/ch12/output.txt";

        RandomAccessFile in = new RandomAccessFile(inputFile, "r");
        RandomAccessFile out = new RandomAccessFile(outputFile, "rw");

        long len = new File(inputFile).length();

        FileChannel inCh = in.getChannel();
        FileChannel outCh = out.getChannel();


        MappedByteBuffer inMB = inCh.map(FileChannel.MapMode.READ_ONLY, 0, len);
        //MappedByteBuffer outMB = outCh.map(FileChannel.MapMode.READ_WRITE, 0,)

        Charset charset = Charset.forName("UTF-16BE");
        CharsetDecoder decoder = charset.newDecoder();  //字节转为字符,
        CharsetEncoder encoder = charset.newEncoder();

        // 把inMB里面的字节序列转为一系列字符,其中每个字符是inMB有效的字节序列解释在decoder对应的字符集的二进制值,而不是其在计算机底层存储的二进制值
        CharBuffer inCB = decoder.decode(inMB);
        showBytes(inCB);

        System.out.println("================");
        // inCB表示的是在之前decoder的字符集下面的对应的二进制值(每一个用字符表示),现在用encoder对应的字符集把每个字符编码为底层的二进制表示(存储)
        ByteBuffer outBB = encoder.encode(inCB);
        showBytes(outBB);

        outCh.write(outBB);

        in.close();
        out.close();


        System.out.println("====================");
        RandomAccessFile f = new RandomAccessFile(outputFile, "rw");
        byte[] fileContent = new byte[1024];
        int fcnt = f.read(fileContent);
        for(int i = 0; i < fcnt; ++i){
            System.out.printf("%x\n",fileContent[i]);
        }
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
