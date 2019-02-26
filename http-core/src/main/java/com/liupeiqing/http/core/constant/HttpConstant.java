package com.liupeiqing.http.core.constant;

/**
 * 静态变量
 * @author liupeqing
 * @date 2019/2/25 13:41
 */
public class HttpConstant {

    public static final String HTTP_PORT  = "http.port";
    public final static String ROOT_PATH  = "http.root.path";

    //内部类 类型
    public static final class ContentType{

        public final static String JSON = "application/json; charset=UTF-8";
        public final static String TEXT = "text/plain; charset=UTF-8";
        public final static String HTML = "text/html; charset=UTF-8";
        public final static String SET_COOKIE = "Set-Cookie";
        public final static String COOKIE = "Cookie";
    }

    /**
     * 系统参数
     */
    public final static class SystemProperties{

        public static final String LOGO = "\n" +
                "\t\t\t ▄████▄   ██▓ ▄████▄   ▄▄▄      ▓█████▄  ▄▄▄      \n" +
                "\t\t\t▒██▀ ▀█  ▓██▒▒██▀ ▀█  ▒████▄    ▒██▀ ██▌▒████▄    \n" +
                "\t\t\t▒▓█    ▄ ▒██▒▒▓█    ▄ ▒██  ▀█▄  ░██   █▌▒██  ▀█▄  \n" +
                "\t\t\t▒▓▓▄ ▄██▒░██░▒▓▓▄ ▄██▒░██▄▄▄▄██ ░▓█▄   ▌░██▄▄▄▄██ \n" +
                "\t\t\t▒ ▓███▀ ░░██░▒ ▓███▀ ░ ▓█   ▓██▒░▒████▓  ▓█   ▓██▒\n" +
                "\t\t\t░ ░▒ ▒  ░░▓  ░ ░▒ ▒  ░ ▒▒   ▓▒█░ ▒▒▓  ▒  ▒▒   ▓▒█░\n" +
                "\t\t\t  ░  ▒    ▒ ░  ░  ▒     ▒   ▒▒ ░ ░ ▒  ▒   ▒   ▒▒ ░\n" +
                "\t\t\t░         ▒ ░░          ░   ▒    ░ ░  ░   ░   ▒   \n" +
                "\t\t\t░ ░       ░  ░ ░            ░  ░   ░          ░  ░\n" +
                "\t\t\t░            ░                   ░                \n" +
                " \n";
        public final static String APPLICATION_PROPERTIES = "application.properties";
        public final static String APPLICATION_THREAD_MAIN_NAME = "☝( ◠‿◠ )☝";
        public final static String APPLICATION_THREAD_WORK_NAME = "(♛‿♛)";
        public final static String APPLICATION_THREAD_SHUTDOWN_NAME = "(〒︿〒)";

    }

}
