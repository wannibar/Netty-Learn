# user thrift --gen java <thrift.file> to generate the code and move the code to right place

namespace java ch8_thift.gen
namespace py   ch8_thift.genPy

typedef i16 short
typedef i32 int
typedef string String
typedef bool boolean

# 默认都是optional
struct Person{
    1: String name,
    2: int age,
    3: boolean married,
}

exception DataException{
    1: String message,
    2: String callStack,
    3: String dataTime,
}

service PersonService{
    Person getPersonByName(1:required String name) throws (1:DataException dataEx),
    void save(1:required Person person) throws (1:DataException dataEx),
}

