package com.liupeiqing.http.core.config;

import com.liupeiqing.http.core.HttpServer;
import com.liupeiqing.http.core.bean.CicadaBeanManager;
import com.liupeiqing.http.core.configuration.AbstractHttpConfiguration;
import com.liupeiqing.http.core.configuration.ApplicationConfiguration;
import com.liupeiqing.http.core.configuration.ConfigurationHolder;
import com.liupeiqing.http.core.constant.HttpConstant;
import com.liupeiqing.http.core.exception.HttpException;
import com.liupeiqing.http.core.reflect.ClassScanner;
import com.liupeiqing.http.core.thread.ThreadLocalHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static com.liupeiqing.http.core.configuration.ConfigurationHolder.getConfiguration;

/**
 * @author liupeqing
 * @date 2019/2/26 11:15
 */
public final class CicadaSetting {

    public static void setting(Class<?> clazz,String rootPath) throws Exception{
        logo();
        //Initialize the application configuration
        initConfiguration(clazz);

        //Set application configuration
        setAppConfig(rootPath);


        //init route bean factory
        CicadaBeanManager.getInstance().init(rootPath);
    }

    private static void logo() {
        System.out.println(HttpConstant.SystemProperties.LOGO);
        Thread.currentThread().setName(HttpConstant.SystemProperties.APPLICATION_THREAD_MAIN_NAME) ;
    }
    /**
     * Initialize the application configuration
     *
     * @param clazz
     * @throws Exception
     */
    private static void initConfiguration(Class<?> clazz) throws Exception {
        ThreadLocalHolder.setLocalTime(System.currentTimeMillis());
        AppConfig.getConfig().setRootPackageName(clazz);

        List<Class<?>> configuration = ClassScanner.getConfiguration(AppConfig.getConfig().getRootPackageName());
        for (Class<?> aClass : configuration) {
            AbstractHttpConfiguration conf = (AbstractHttpConfiguration) aClass.newInstance();

            // First read
            InputStream stream ;
            String systemProperty = System.getProperty(conf.getPropertiesName());
            if (systemProperty != null) {
                stream = new FileInputStream(new File(systemProperty));
            } else {
                stream = HttpServer.class.getClassLoader().getResourceAsStream(conf.getPropertiesName());
            }

            Properties properties = new Properties();
            properties.load(stream);
            conf.setProperties(properties);

            // add configuration cache
            ConfigurationHolder.addConfiguration(aClass.getName(), conf);

        }
    }

    /**
     * Set application configuration
     *
     * @param rootPath
     */
    private static void setAppConfig(String rootPath) {
        ApplicationConfiguration applicationConfiguration = (ApplicationConfiguration) getConfiguration(ApplicationConfiguration.class);

        if (rootPath == null) {
            rootPath = applicationConfiguration.get(HttpConstant.ROOT_PATH);
        }
        String port = applicationConfiguration.get(HttpConstant.HTTP_PORT);

        if (rootPath == null) {
            throw new HttpException("No [cicada.root.path] exists ");
        }
        if (port == null) {
            throw new HttpException("No [cicada.port] exists ");
        }
        AppConfig.getConfig().setRootPath(rootPath);
        AppConfig.getConfig().setPort(Integer.parseInt(port));
    }
}
