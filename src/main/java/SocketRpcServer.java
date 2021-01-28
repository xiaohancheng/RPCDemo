import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/22
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class SocketRpcServer extends AbstractRpcServer{
    private ExecutorService executorService;
    public SocketRpcServer(String host,int port){
        super(host,port);
         executorService = new ThreadPoolExecutor(5,10,60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket;
            while ((socket = serverSocket.accept()) != null){
                executorService.execute(new WorkerThread(serverProvider,socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
