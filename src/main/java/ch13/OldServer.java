package ch13;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by WQS on 2017/9/1.
 * Mail: 1027738387@qq.com
 * Github: https://github.com/wannibar
 */
public class OldServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(9999));

        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());

            try {
                byte[] buf = new byte[4096];
                while (true) {
                    int readCnt = in.read(buf, 0, buf.length);
                    if(readCnt == -1)   // EOF of stream
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
