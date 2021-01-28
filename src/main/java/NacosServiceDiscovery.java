import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/27
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class NacosServiceDiscovery implements ServiceDiscovery {
    private static final String SERVER_ADDR = "127.0.0.1:8848";
    private static NamingService NAMING_SERVICE;
    private LoadBalance loadBalance;
    static {
        try {
            NAMING_SERVICE = NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    public NacosServiceDiscovery(){
        this(new RandomLoadBalance());
    }
    public NacosServiceDiscovery(LoadBalance loadBalance){
        this.loadBalance = loadBalance;
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NAMING_SERVICE.registerInstance(serviceName,inetSocketAddress.getHostName(),inetSocketAddress.getPort());
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = NAMING_SERVICE.getAllInstances(serviceName);
            Instance instance = loadBalance.select(instances);
            return new InetSocketAddress(instance.getIp(),instance.getPort());
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }
}
