package com.liupeiqing.http.core.handle;

import com.alibaba.fastjson.JSON;
import com.liupeiqing.http.base.log.LoggerBuilder;
import com.liupeiqing.http.core.action.param.Param;
import com.liupeiqing.http.core.action.param.ParamMap;
import com.liupeiqing.http.core.action.req.CicadaHttpRequest;
import com.liupeiqing.http.core.action.req.CicadaRequest;
import com.liupeiqing.http.core.action.res.CicadaHttpResponse;
import com.liupeiqing.http.core.action.res.CicadaResponse;
import com.liupeiqing.http.core.action.res.WorkRes;
import com.liupeiqing.http.core.config.AppConfig;
import com.liupeiqing.http.core.constant.HttpConstant;
import com.liupeiqing.http.core.context.HttpContext;
import com.liupeiqing.http.core.exception.HttpException;
import com.liupeiqing.http.core.intercept.InterceptProcess;
import com.liupeiqing.http.core.route.RouteProcess;
import com.liupeiqing.http.core.route.RouterScanner;

import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author liupeqing
 * @date 2019/2/25 20:10
 */
@ChannelHandler.Sharable
public class HttpDispatcher extends SimpleChannelInboundHandler<DefaultHttpRequest> {

    private static final Logger LOGGER = LoggerBuilder.getLogger(HttpDispatcher.class);

    private final AppConfig appConfig = AppConfig.getConfig();
    private final InterceptProcess interceptProcess = InterceptProcess.getInterceptProcess();
    private final RouterScanner routerScanner = RouterScanner.getInstance();
    private final RouteProcess routeProcess = new RouteProcess();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultHttpRequest httpRequest) throws Exception {
        CicadaRequest cicadaRequest = CicadaHttpRequest.init(httpRequest);
        CicadaResponse cicadaResponse = CicadaHttpResponse.init();

        // set current thread request and response
        HttpContext.setContext(new HttpContext(cicadaRequest,cicadaResponse));
        try{
            String uri = cicadaRequest.getUrl();
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(URLDecoder.decode(httpRequest.uri(), "utf-8"));
            appConfig.checkRootPath(uri,queryStringDecoder);
            // route Action
            //Class<?> actionClazz = routeAction(queryStringDecoder, appConfig);
            //build paramMap
            Param paramMap  = buildParamMap(queryStringDecoder);

            //load interceptors
            interceptProcess.loadInterceptors();

            //interceptor before
            boolean access = interceptProcess.processBefore(paramMap );
            if (!access){
                return;
            }

            // execute Method

            Method method = routerScanner.routeMethod(queryStringDecoder);
            routeProcess.invoke(method,queryStringDecoder);

            //WorkAction action = (WorkAction) actionClazz.newInstance();
            //action.execute(CicadaContext.getContext(), paramMap);


            // interceptor after
            interceptProcess.processAfter(paramMap);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // Response
            responseContent(ctx);
            // remove cicada thread context
            HttpContext.removeContext();
        }

    }
    /**
     * Response
     *
     * @param ctx
     */
    private void responseContent(ChannelHandlerContext ctx) {

        CicadaResponse cicadaResponse = HttpContext.getResponse();
        String context = cicadaResponse.getHttpContent() ;

        ByteBuf buf = Unpooled.wrappedBuffer(context.getBytes(StandardCharsets.UTF_8));
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        buildHeader(response);
        ctx.writeAndFlush(response);
    }

    /**
     * build Header
     *
     * @param response
     */
    private void buildHeader(DefaultFullHttpResponse response) {
        CicadaResponse cicadaResponse = HttpContext.getResponse();

        HttpHeaders headers = response.headers();
        headers.setInt(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.set(HttpHeaderNames.CONTENT_TYPE, cicadaResponse.getContentType());

        List<Cookie> cookies = cicadaResponse.cookies();
        for (Cookie cookie : cookies) {
            headers.add(HttpConstant.ContentType.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.LAX.encode(cookie));
        }

    }


    /**
     * build paramMap
     *
     * @param queryStringDecoder
     * @return
     */
    private Param buildParamMap(QueryStringDecoder queryStringDecoder) {
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        Param paramMap = new ParamMap();
        for (Map.Entry<String, List<String>> stringListEntry : parameters.entrySet()) {
            String key = stringListEntry.getKey();
            List<String> value = stringListEntry.getValue();
            paramMap.put(key, value.get(0));
        }
        return paramMap;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (HttpException.isResetByPeer(cause.getMessage())){
            return;
        }
        LOGGER.error(cause.getMessage(), cause);
        WorkRes workRes = new WorkRes();
        workRes.setCode(String.valueOf(HttpResponseStatus.NOT_FOUND.code()));
        workRes.setMessage(cause.getMessage());
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND, Unpooled.copiedBuffer(JSON.toJSONString(workRes), CharsetUtil.UTF_8));
        buildHeader(response);
        ctx.writeAndFlush(response);
    }
}
