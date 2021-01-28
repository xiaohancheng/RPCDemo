/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/22
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class NettyTestClient {
    public static void main(String[] args) {
        HelloService helloService = new RpcClientProxy(new NettyRpcClient(new RandomLoadBalance())).getProxy(HelloService.class);
        String result = helloService.sayHello("小明");
        System.out.println(result);
    }
}
