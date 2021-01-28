import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/22
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class SocketRpcClient implements RpcClient{
    private ServiceDiscovery serviceDiscovery;

    public SocketRpcClient(LoadBalance loadBalance) {
        serviceDiscovery = new NacosServiceDiscovery(loadBalance);
    }

    public Object sendMsg(RpcRequest rpcRequest){
        InetSocketAddress address = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        try (Socket socket = new Socket(address.getHostName(), address.getPort())) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
