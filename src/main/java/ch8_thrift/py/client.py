from genPy import PersonService
from genPy import ttypes
from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol

try:
    tSocket = TSocket.TSocket("127.0.0.1", 9999)
    tSocket.setTimeout(1000)
    transport = TTransport.TFramedTransport(tSocket)
    protocol = TCompactProtocol.TCompactProtocol(transport)
    client = PersonService.Client(protocol)

    transport.open()
    person = client.getPersonByName("王青松")
    print(person.name + ":" + str(person.age) + ":" + str(person.married))

    print("-" * 10)

    person = ttypes.Person()
    person.name = "赵云"
    person.age = 1908
    person.married = True
    client.save(person)

    transport.close()
except Thrift.TException as tx:
    print(tx.message)
