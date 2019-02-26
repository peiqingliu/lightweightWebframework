package com.liupeiqing.http.core.config;

import com.liupeiqing.http.core.enums.StatusEnum;
import com.liupeiqing.http.core.exception.HttpException;
import com.liupeiqing.http.core.utils.PathUtil;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * @author liupeqing
 * @date 2019/2/25 11:27
 */
public class AppConfig {

    /**
     * simple singleton Object
     */
    private static AppConfig config;

    private AppConfig(){}  //私有化构造器

    public static AppConfig getConfig(){
        if (config == null){
            config = new AppConfig();
        }
        return config;
    }

    private String rootPackageName;

    private String rootPath;

    private Integer port = 7317;

    public String getRootPackageName(){
        return rootPackageName;
    }

    public void setRootPackageName(Class<?> clazz){
        if (clazz.getPackage() == null) {
            throw new HttpException(StatusEnum.NULL_PACKAGE, "[" + clazz.getName() + ".java]:" + StatusEnum.NULL_PACKAGE.getMessage());
        }
        this.rootPackageName = clazz.getPackage().getName();
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * 检查路径
     * @param url
     * @param queryStringDecoder
     */
    public void checkRootPath(String url,QueryStringDecoder queryStringDecoder){
        if (!PathUtil.getRootPath(queryStringDecoder.path()).equals(this.getRootPath())){
            throw new HttpException(StatusEnum.REQUEST_ERROR,url);

        }
    }
}
