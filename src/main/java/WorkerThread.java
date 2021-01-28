import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/22
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class WorkerThread extends Thread{
    private ServerProvider serverProvider;
    private Socket socket;
    public WorkerThread(ServerProvider serverProvider, Socket socket) {
        this.serverProvider = serverProvider;
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object service = serverProvider.getService(rpcRequest.getInterfaceName());
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamTypes());
            Object obj = method.invoke(service,rpcRequest.getParamters());
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
