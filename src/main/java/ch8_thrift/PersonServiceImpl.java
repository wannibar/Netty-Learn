package ch8_thrift;

import ch8_thrift.gen.DataException;
import ch8_thrift.gen.Person;
import ch8_thrift.gen.PersonService;
import org.apache.thrift.TException;

public class PersonServiceImpl implements PersonService.Iface {
    @Override
    public Person getPersonByName(String name) throws DataException, TException {
        System.out.println("Get Client Param name:" + name);
        Person p = new Person();
        p.setName(name);
        p.setAge(20);
        p.setMarried(false);
        return p;
    }

    @Override
    public void save(Person person) throws DataException, TException {
        System.out.println("Get Client Param person:"
                + person + ":" + person.getName()
                + ":" + person.getAge()
                + ":" + person.isMarried());
    }
}
