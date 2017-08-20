package ch11;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by WQS on 2017/8/17.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class NioTest9 {
    public static void main(String[] args) throws Exception {
        RandomAccessFile f = new RandomAccessFile("src/main/java/ch11/ra", "rw");
        FileChannel fc = f.getChannel();

        FileLock fl = fc.lock(3, 2, true);

        System.out.println("Valid" + fl.isValid());
        System.out.println("Lock Type:" + fl.isShared());

        fl.release();

        f.close();
    }
}
