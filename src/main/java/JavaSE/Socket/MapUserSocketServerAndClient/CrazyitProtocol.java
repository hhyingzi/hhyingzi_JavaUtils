package JavaSE.Socket.MapUserSocketServerAndClient;

/*
* 用户线程注册：∏∑user∏∑
* 普通消息格式：§γmessage§γ
* 私聊消息格式：★【receiveUser※message★【
* */
public interface CrazyitProtocol {
    // 定义协议字符串的长度
    int PROTOCOL_LEN = 2;
    // 下面是一些协议字符串，服务器和客户端交换的信息都应该在前、后添加这种特殊字符串。
    String MSG_ROUND = "§γ";  //消息体的前导符和结尾符
    String USER_ROUND = "∏∑";  //用户名前导符和结尾符
    String LOGIN_SUCCESS = "1";  //登录成功
    String NAME_REP = "-1"; //新建用户名和已有用户名重复，返回该代码
    String PRIVATE_ROUND = "★【";  //私聊消息的前导符和结尾符
    String SPLIT_SIGN = "※";  //私聊消息中的分隔符
}
