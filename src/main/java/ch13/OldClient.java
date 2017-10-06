package ch13;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class OldClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 9999));

        String fileName = "src/main/java/ch13/bigData.zip";

        InputStream in = new FileInputStream(fileName);

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        byte[] buf = new byte[4096];
        long total = 0;

        long startTime = System.currentTimeMillis();
        long readCnt = 0;
        while ((readCnt = in.read(buf, 0, buf.length)) > 0) {
            total += readCnt;
            out.write(buf, 0, (int) readCnt);
        }

        System.out.println("发送：" + total + " bytes 耗时" + (System.currentTimeMillis() - startTime));

        in.close();
        out.close();
        socket.close();
    }
}
