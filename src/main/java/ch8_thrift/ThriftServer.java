package ch8_thrift;

import ch8_thrift.gen.PersonService;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

public class ThriftServer {
    public static void main(String[] args) throws TTransportException {

        TNonblockingServerSocket socket = new TNonblockingServerSocket(9999);
        THsHaServer.Args serverArgs = new THsHaServer.Args(socket)
                .maxWorkerThreads(10)
                .minWorkerThreads(4);
        PersonService.Processor<PersonServiceImpl> processor =
                new PersonService.Processor<>(new PersonServiceImpl());

        /**
         * 协议层用到(传输格式:二进制或者压缩格式,JSON,Debug...)
         */
        serverArgs.protocolFactory(new TCompactProtocol.Factory());
        /**
         * 传输方式:
         * TSocket(阻塞式)
         * TFramedTransport(非阻塞里面用以Frame为单位传输)
         * TFileTransport.....
         */
        serverArgs.transportFactory(new TFramedTransport.Factory());

        serverArgs.processorFactory(new TProcessorFactory(processor));

        TServer server = new THsHaServer(serverArgs);                   // 服务模型
        System.out.println("Thrift Server Started!");
        server.serve();
    }
}
