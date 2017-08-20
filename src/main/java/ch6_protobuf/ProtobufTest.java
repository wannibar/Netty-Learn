package ch6_protobuf;

import com.google.protobuf.InvalidProtocolBufferException;


public class ProtobufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("wqs").setAddress("大蜀山").setAge(999)
                .build();

        // 把对象转为为字节数组
        byte[] studentInBytes = student.toByteArray();
        // 从字节数组得到对象
        DataInfo.Student student1 = DataInfo.Student.parseFrom(studentInBytes);

        System.out.println(student1);
        System.out.println(student1.getAddress());

    }
}
