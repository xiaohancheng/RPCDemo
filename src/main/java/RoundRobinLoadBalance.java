import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/28
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class RoundRobinLoadBalance implements LoadBalance{
    private static int index = 0;
    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(index ++ % instances.size());
    }
}
