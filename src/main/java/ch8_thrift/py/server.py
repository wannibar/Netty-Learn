from genPy import PersonService
from genPy import ttypes
from thrift import Thrift
from thrift.transport import TSocket
from thrift.server import TServer
from thrift.server import TNonblockingServer

from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
from PersonServiceImpl import PersonServiceImpl

try:
    personServiceHandler = PersonServiceImpl()
    processor = PersonService.Processor(personServiceHandler)

    serverSocket = TSocket.TServerSocket(host="127.0.0.1", port=9999)
    tansportFactory = TTransport.TFramedTransportFactory()
    protocolFactory = TCompactProtocol.TCompactProtocolFactory()

    server = TServer.TThreadPoolServer(processor,serverSocket, tansportFactory, protocolFactory)
    server.serve()

except Thrift.TException as tx:
    print(tx.message)
