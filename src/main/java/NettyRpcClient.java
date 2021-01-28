import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/25
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class NettyRpcClient implements RpcClient{
    private static final Bootstrap bootstrap;
    private ServiceDiscovery serviceDiscovery;
    public NettyRpcClient(LoadBalance loadBalance) {
        serviceDiscovery = new NacosServiceDiscovery(loadBalance);
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new JsonSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendMsg(RpcRequest rpcRequest) {
        try {
            InetSocketAddress socketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
            ChannelFuture future = bootstrap.connect(socketAddress.getHostName(), socketAddress.getPort()).sync();
            Channel channel = future.channel();
            if(channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if(future1.isSuccess()) {
                        System.out.println(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        System.out.println("发送消息时有错误发生: " + future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<String> key = AttributeKey.valueOf("rpcResponse");
                String rpcResponse = channel.attr(key).get();
                return rpcResponse;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
