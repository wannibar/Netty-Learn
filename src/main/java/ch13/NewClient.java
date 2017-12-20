package ch13;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class NewClient {
    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(true);
        socketChannel.connect(new InetSocketAddress("localhost", 8888));

        // the file should be a very large file .. >= 100Mb
        String fileName = "src/main/java/ch13/bigData.zip";
        //fileName = "G:\\study\\Java\\Framework\\Netty\\video\\1_学习的要义.mp4";

        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();


        long reqCnt = fileChannel.size();
        long curCnt = 0;
        while (true) {
            // windows平台最多只能传送8M字节
            long transCnt = fileChannel.transferTo(curCnt, 4096 * 4096, socketChannel);
            System.out.println(transCnt);
            if (transCnt == 0)
                break;
            curCnt += transCnt;
            if (curCnt >= reqCnt)
                break;
        }


        System.out.println("发送：" + curCnt + " bytes 耗时" + (System.currentTimeMillis() - startTime));
        fileChannel.close();
    }
}
