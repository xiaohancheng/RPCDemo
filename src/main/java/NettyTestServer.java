/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/22
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class NettyTestServer {
    public static void main(String[] args) {
        NettyRpcServer server = new NettyRpcServer("127.0.0.1",8989);
        HelloService helloService = new HelloServiceImpl();
        server.publishService(helloService,HelloService.class);
        server.start();
    }
}
