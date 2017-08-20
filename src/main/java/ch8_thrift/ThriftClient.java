package ch8_thrift;

import ch8_thrift.gen.Person;
import ch8_thrift.gen.PersonService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.*;

public class ThriftClient {
    public static void main(String[] args) throws TTransportException {

        // 客户端的协议(Protocol)和传输(Transport)必须和服务器一致
        TTransport tTransport = new TFramedTransport(new TSocket("127.0.0.1", 9999));
        TProtocol tProtocol = new TCompactProtocol(tTransport);
        PersonService.Client client = new PersonService.Client(tProtocol);
        tTransport.open();

        try {
            Person p = client.getPersonByName("wqs");
            System.out.println(p.getName() + ":" + p.getAge() + ":" + p.isMarried());

            System.out.println("==================");

            p = new Person();
            p.setAge(99);
            p.setName("gouxu");
            p.setMarried(true);
            client.save(p);
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            tTransport.close();
        }
    }
}
