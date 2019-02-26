package com.liupeiqing.http.core.reflect;

import com.liupeiqing.http.base.bean.HttpBeanFactory;
import com.liupeiqing.http.base.log.LoggerBuilder;
import com.liupeiqing.http.core.annotation.HttpAction;
import com.liupeiqing.http.core.annotation.Interceptor;
import com.liupeiqing.http.core.configuration.AbstractHttpConfiguration;
import com.liupeiqing.http.core.configuration.ApplicationConfiguration;
import com.liupeiqing.http.core.enums.StatusEnum;
import com.liupeiqing.http.core.exception.HttpException;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 利用反射，遍历包
 * @author liupeqing
 * @date 2019/2/25 17:04
 */
public class ClassScanner {

    private final static Logger LOGGER = LoggerBuilder.getLogger(ClassScanner.class);

    private static Map<String,Class<?>> actionMap = null;

    private static Map<Class<?>,Integer> interceptorMap = null;

    private static Set<Class<?>> classes = null;

    private static Set<Class<?>> cicada_classes = null;

    private static List<Class<?>> configurationList = null;

    /**
     *  get Configuration
     * @param packageName
     * @return
     * @throws Exception
     */
    public static List<Class<?>> getConfiguration(String packageName) throws Exception{
        if (configurationList == null){
            Set<Class<?>> clsList = getClasses(packageName);
            clsList.add(ApplicationConfiguration.class);

            if (clsList == null || clsList.isEmpty()){
                return configurationList;
            }
            configurationList = new ArrayList<>(16);

            for (Class<?> cls : clsList){
                if (cls.getSuperclass() != AbstractHttpConfiguration.class){
                    continue;
                }
                configurationList.add(cls);
            }
        }
        return configurationList;
    }

    /**
     * get @HttpAction
     * @param packageName
     * @return
     * @throws Exception
     */
    public static Map<String,Class<?>> getCicadaAction(String packageName) throws Exception{
        if (actionMap == null){
            Set<Class<?>> clsList = getClasses(packageName);
            if (clsList == null || clsList.isEmpty()){
                return actionMap;
            }

            actionMap = new HashMap<>(16);
            for (Class<?> cls : clsList){
                Annotation annotation = cls.getAnnotation(HttpAction.class);
                if (annotation == null ){
                    continue;
                }
                HttpAction httpAction = (HttpAction) annotation;
                actionMap.put(httpAction.value() == null ? cls.getName() : httpAction.value(),cls);
            }
        }
        return actionMap;
    }

    /**
     * get @Interceptor
     *
     * @param packageName
     * @return
     * @throws Exception
     */
    public static Map<Class<?>,Integer> getCicadaInterceptor(String packageName) throws Exception{
        if (interceptorMap == null){
            Set<Class<?>> clsList = getClasses(packageName);
            if (clsList == null || clsList.isEmpty()){
                return interceptorMap;
            }
            interceptorMap = new HashMap<>(16);
            for (Class<?> cls : clsList){
                Annotation annotation = cls.getAnnotation(Interceptor.class);
                if (annotation == null){
                    continue;
                }
                Interceptor interceptor = (Interceptor) annotation;
                interceptorMap.put(cls,interceptor.order());
            }
        }
        return interceptorMap;
    }

    /**
     * get All classes
     *
     * @param packageName
     * @return
     * @throws Exception
     */
    public static Set<Class<?>> getClasses(String packageName) throws Exception{
        if (classes == null){
            classes = new HashSet<>(32);
            baseScanner(packageName,classes);
        }
    return classes;
    }

    private static void baseScanner(String packageName,Set<Class<?>> set){
        boolean recursive = true;
        String packageDirName = packageName.replace('.','/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)){
                    String filePath = URLDecoder.decode(url.getFile(),"UTF-8");
                    findAndAddClassesInPackageByFile(packageName,filePath,recursive,set);
                }else if("jar".equals(protocol)){
                    JarFile jar;
                    try {
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()){
                            JarEntry entry  = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/'){
                                name = name.substring(1);
                            }
                            if (name.startsWith(packageDirName)){
                                int idx = name.lastIndexOf('/');
                                if ( (idx != -1) || recursive){
                                    if (name.endsWith(".class") && !entry.isDirectory()){
                                        String className = name.substring(packageDirName.length() + 1,name.length() - 6);
                                        try {
                                            set.add(Class.forName(packageName + "." + className));
                                        } catch (ClassNotFoundException e) {
                                            LOGGER.error("classNotFoundException",e);
                                        }
                                    }

                                }
                            }
                        }
                    }catch (IOException e){
                        LOGGER.error("IOException",e);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void findAndAddClassesInPackageByFile(String packageName,String packagePath,final boolean recursive,Set<Class<?>> classes){
        File dir = new File(packagePath);

        //dir.isDirectory() 是不是文件夹
        if (!dir.exists() || !dir.isDirectory()){
            return;
        }
        File[] files = dir.listFiles(file ->(recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
        for (File file : files){
            if (file.isDirectory()){
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),file.getAbsolutePath(),recursive,classes);
            }else{
                String className = file.getName().substring(0,file.getName().length() - 6);
                try{
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e) {
                    LOGGER.error("classNotFoundException",e);
                }
            }
        }

    }

    private static final String BASE_PACKAGE = "com.liupeiqing.http";

    /**
     * get custom route bean
     * @return
     * @throws Exception
     */
    public static Class<?> getCustomRouteBean() throws Exception{

        List<Class<?>> classList = new ArrayList<>();

        Set<Class<?>> classes = getCustomRouteBeanClasses(BASE_PACKAGE);

        for (Class<?> aClass : classes){
            if (aClass.getInterfaces().length == 0){
                continue;
            }
            if (aClass.getInterfaces()[0] != HttpBeanFactory.class){
                continue;
            }

            classList.add(aClass);
        }

        if (classList.size() > 2){
            throw new HttpException(StatusEnum.DUPLICATE_IOC);
        }

        if (classList.size() > 3){
            Iterator<Class<?>> iterator = classes.iterator();
            while (iterator.hasNext()){
                if (iterator.next() == HttpBeanFactory.class){
                    iterator.remove();
                }
            }
        }
        return classList.get(0);
    }

    private static Set<Class<?>> getCustomRouteBeanClasses(String packageName) throws Exception{
        if (cicada_classes == null){
            cicada_classes = new HashSet<>(32);
            baseScanner(packageName,cicada_classes);
        }
        return cicada_classes;
    }
}
