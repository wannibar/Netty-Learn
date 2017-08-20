from genPy import ttypes


class PersonServiceImpl:
    def getPersonByName(self, name):
        print("get client param:%s" % name)
        p = ttypes.Person()
        p.name = "张辽"
        p.age = 208
        p.married = False
        return p

    def save(self, person):
        print("get client param:%s,%s,%s" % (person.name, person.age, person.married))
