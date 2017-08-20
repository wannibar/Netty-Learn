package ch11;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by WQS on 2017/8/17.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class NioTest8 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/java/ch11/ra", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte)'A');

        randomAccessFile.close();
    }
}
