import java.net.InetSocketAddress;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/27
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public interface ServiceDiscovery {
    void register(String serviceName, InetSocketAddress inetSocketAddress);
    InetSocketAddress lookupService(String serviceName);
}
