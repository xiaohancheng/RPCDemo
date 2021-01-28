/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/22
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class TestServer {
    public static void main(String[] args) {
        SocketRpcServer socketRpcServer = new SocketRpcServer("127.0.0.1",8980);
        HelloService helloService = new HelloServiceImpl();
        socketRpcServer.publishService(helloService,HelloService.class);
        socketRpcServer.start();
    }
}
