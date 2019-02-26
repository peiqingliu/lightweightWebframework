package com.liupeiqing.http.core.bootstrap;

import com.liupeiqing.http.base.log.LoggerBuilder;
import com.liupeiqing.http.core.bean.CicadaBeanManager;
import com.liupeiqing.http.core.config.AppConfig;
import com.liupeiqing.http.core.configuration.ApplicationConfiguration;
import com.liupeiqing.http.core.constant.HttpConstant;
import com.liupeiqing.http.core.context.HttpContext;
import com.liupeiqing.http.core.init.CicadaInitializer;
import com.liupeiqing.http.core.thread.ThreadLocalHolder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;

import static com.liupeiqing.http.core.configuration.ConfigurationHolder.getConfiguration;
import static com.liupeiqing.http.core.constant.HttpConstant.SystemProperties.APPLICATION_THREAD_SHUTDOWN_NAME;
import static com.liupeiqing.http.core.constant.HttpConstant.SystemProperties.APPLICATION_THREAD_WORK_NAME;

/**
 * @author liupeqing
 * @date 2019/2/25 19:21
 */
public class NettyBootStrap {

    private final static Logger LOGGER = LoggerBuilder.getLogger(NettyBootStrap.class);

    private static AppConfig appConfig = AppConfig.getConfig();

    /**
     * 第一个经常被叫做‘boss’，用来接收进来的连接。
     */
    private static EventLoopGroup boss = new NioEventLoopGroup(1,new DefaultThreadFactory("boss"));

    /**
     *  第二个经常被叫做‘worker’，用来处理已经被接收的连接，
     */
    private static EventLoopGroup work = new NioEventLoopGroup(0,new DefaultThreadFactory(APPLICATION_THREAD_WORK_NAME));

    private static Channel channel;

    /**
     * Start netty Server
     * @throws Exception
     */
    public static void startCicada() throws Exception{

        // start
        startServer();

        // register shutdown hook
        shutDownServer();

        // synchronized channel
        joinServer();

    }

    private static void joinServer() throws Exception {
        channel.closeFuture().sync();
    }

    /**
     * start netty server
     * 开启netty server
     * @throws InterruptedException
     */
    private static void startServer() throws InterruptedException{

        //ServerBootstrap是监听服务端端口的启动器
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 注册线程池
        bootstrap.group(boss,work);
        //指定NIO的模式.NioServerSocketChannel对应TCP, NioDatagramChannel对应UDP
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new CicadaInitializer());
        ChannelFuture future = bootstrap.bind(AppConfig.getConfig().getPort()).sync();
        if (future.isSuccess()){
            appLog();
        }
        channel = future.channel();

    }

    private static void appLog() {
        Long start = ThreadLocalHolder.getLocalTime();
        ApplicationConfiguration applicationConfiguration = (ApplicationConfiguration) getConfiguration(ApplicationConfiguration.class);
        long end = System.currentTimeMillis();
        LOGGER.info("Cicada started on port: {}.cost {}ms", applicationConfiguration.get(HttpConstant.HTTP_PORT), end - start);
        LOGGER.info(">> access http://{}:{}{} <<","127.0.0.1",appConfig.getPort(),appConfig.getRootPath());
    }

    private static void shutDownServer(){
        ShutDownThread shutDownThread = new ShutDownThread();
        shutDownThread.setName(APPLICATION_THREAD_SHUTDOWN_NAME);
        Runtime.getRuntime().addShutdownHook(shutDownThread);
    }

    /**
     * 新建一个线程去处理关闭
     */
    private static class ShutDownThread extends Thread{
        @Override
        public void run() {
            LOGGER.info("Cicada server stop...");
            HttpContext.removeContext();
            CicadaBeanManager.getInstance().releaseBean();
            boss.shutdownGracefully();
            work.shutdownGracefully();
            LOGGER.info("Cicada server has been successfully stopped.");
        }
    }
}
