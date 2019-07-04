package rpf.study.kafka.socket;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

import static org.jboss.netty.channel.Channels.*;
import static org.jboss.netty.channel.Channels.pipeline;
/**
 * 设置管道参数的初始化参数
 * */
public class WebSocketServerPipelineFactory implements ChannelPipelineFactory{

    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());  //编码器为http默认编码
        pipeline.addLast("aggregator", new HttpChunkAggregator(65536)); //消息聚合器，配置了传输消息的大小限制64*1024=64kb 
        pipeline.addLast("encoder", new HttpResponseEncoder()); //解码器为http默认解码器 
        pipeline.addLast("handler", new WebSocketServerHandler()); //管道的核心业务处理类 
        return pipeline;  
    }
    
}