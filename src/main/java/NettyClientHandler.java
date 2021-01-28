import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/**
 * @author xiaohancheng
 * @company 广州市人心网络科技有限公司
 * @Description:
 * @date 2021/1/25
 * @Copyright (c) 2021, xiaohancheng@xinli001.com All Rights Reserved.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        AttributeKey<String> key = AttributeKey.valueOf("rpcResponse");
        channelHandlerContext.channel().attr(key).set(msg);
        channelHandlerContext.channel().close();
    }
}
