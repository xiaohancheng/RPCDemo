import java.net.InetSocketAddress;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/28
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public abstract class AbstractRpcServer implements RpcServer{
    protected String host;
    protected int port;
    protected ServerProvider serverProvider;
    protected ServiceDiscovery serviceDiscovery;
    public AbstractRpcServer(String host,int port){
        this.host = host;
        this.port = port;
        serverProvider = new DefaultServiceProvider();
        serviceDiscovery = new NacosServiceDiscovery();
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {
        serverProvider.addServiceProvider(service);
        serviceDiscovery.register(serviceClass.getCanonicalName(),new InetSocketAddress(host,port));
    }

}
