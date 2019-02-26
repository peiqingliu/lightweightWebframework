package com.liupeiqing.http.core.init;

import com.liupeiqing.http.core.handle.HttpDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * ChannelInitializer的主要目的是为程序员提供了一个简单的工具，用于在某个Channel注册到EventLoop后，对这个Channel执行一些初始化操作。
 * @author liupeqing
 * @date 2019/2/25 20:06
 */
public class CicadaInitializer extends ChannelInitializer<Channel> {

    private final HttpDispatcher  httpDispatcher = new HttpDispatcher();
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new HttpRequestDecoder())
                .addLast(new HttpResponseDecoder())
                .addLast(new ChunkedWriteHandler())
                .addLast(httpDispatcher)
                .addLast("logging",new LoggingHandler(LogLevel.INFO));

    }
}
