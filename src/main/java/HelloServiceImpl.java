/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/22
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String name) {
        System.out.println("hello "+ name);
        return "receive name=" + name;
    }
}
