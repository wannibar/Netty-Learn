// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Student.proto

package ch10;

public final class StudentProto {
  private StudentProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ch10_MyRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ch10_MyRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ch10_MyResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ch10_MyResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ch10_StudentResp_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ch10_StudentResp_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ch10_StudentRespList_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ch10_StudentRespList_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ch10_StudentReq_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ch10_StudentReq_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ch10_StreamReq_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ch10_StreamReq_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ch10_StreamResp_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ch10_StreamResp_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ch10_NOTUSE_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ch10_NOTUSE_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rStudent.proto\022\004ch10\"\035\n\tMyRequest\022\020\n\010us" +
      "ername\030\001 \001(\t\"\036\n\nMyResponse\022\020\n\010realname\030\001" +
      " \001(\t\"6\n\013StudentResp\022\014\n\004name\030\001 \001(\t\022\013\n\003age" +
      "\030\002 \001(\005\022\014\n\004city\030\003 \001(\t\"=\n\017StudentRespList\022" +
      "*\n\017studentRespList\030\001 \003(\0132\021.ch10.StudentR" +
      "esp\"\031\n\nStudentReq\022\013\n\003age\030\001 \001(\005\"\035\n\tStream" +
      "Req\022\020\n\010req_info\030\001 \001(\t\"\037\n\nStreamResp\022\021\n\tr" +
      "esp_info\030\001 \001(\t\"\023\n\006NOTUSE\022\t\n\001a\030\001 \001(\0052\207\002\n\016" +
      "StudentService\022<\n\025GetRealNameByUserName\022" +
      "\017.ch10.MyRequest\032\020.ch10.MyResponse\"\000\022;\n\020",
      "GetStudentsByAge\022\020.ch10.StudentReq\032\021.ch1" +
      "0.StudentResp\"\0000\001\022G\n\030GetStudentsWrapperB" +
      "yAges\022\020.ch10.StudentReq\032\025.ch10.StudentRe" +
      "spList\"\000(\001\0221\n\006BiTalk\022\017.ch10.StreamReq\032\020." +
      "ch10.StreamResp\"\000(\0010\001B\026\n\004ch10B\014StudentPr" +
      "otoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_ch10_MyRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ch10_MyRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ch10_MyRequest_descriptor,
        new java.lang.String[] { "Username", });
    internal_static_ch10_MyResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_ch10_MyResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ch10_MyResponse_descriptor,
        new java.lang.String[] { "Realname", });
    internal_static_ch10_StudentResp_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_ch10_StudentResp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ch10_StudentResp_descriptor,
        new java.lang.String[] { "Name", "Age", "City", });
    internal_static_ch10_StudentRespList_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_ch10_StudentRespList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ch10_StudentRespList_descriptor,
        new java.lang.String[] { "StudentRespList", });
    internal_static_ch10_StudentReq_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_ch10_StudentReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ch10_StudentReq_descriptor,
        new java.lang.String[] { "Age", });
    internal_static_ch10_StreamReq_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_ch10_StreamReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ch10_StreamReq_descriptor,
        new java.lang.String[] { "ReqInfo", });
    internal_static_ch10_StreamResp_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_ch10_StreamResp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ch10_StreamResp_descriptor,
        new java.lang.String[] { "RespInfo", });
    internal_static_ch10_NOTUSE_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_ch10_NOTUSE_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ch10_NOTUSE_descriptor,
        new java.lang.String[] { "A", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}