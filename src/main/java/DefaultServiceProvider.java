import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/25
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class DefaultServiceProvider implements ServerProvider {
    private final static Map<String,Object> serviceMap = new ConcurrentHashMap<>();
    private final static Set<String> registerService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized  <T> void addServiceProvider(T service) {
        String serviceName = service.getClass().getCanonicalName();
        if(registerService.contains(serviceName)) return;
        registerService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0) return;
        for (Class<?> anInterface : interfaces) {
            serviceMap.put(anInterface.getCanonicalName(),service);
        }
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        return  service;
    }
}
