import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/25
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static ServerProvider serverProvider = new DefaultServiceProvider();

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        String interfaceName = rpcRequest.getInterfaceName();
        Object service = serverProvider.getService(interfaceName);
        Method method = service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamTypes());
        Object result = method.invoke(service,rpcRequest.getParamters());
        ChannelFuture future = channelHandlerContext.writeAndFlush(result);
        future.addListener(ChannelFutureListener.CLOSE);
    }
}
