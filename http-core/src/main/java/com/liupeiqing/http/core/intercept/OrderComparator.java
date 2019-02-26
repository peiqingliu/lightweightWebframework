package com.liupeiqing.http.core.intercept;

import java.util.Comparator;

/**
 * 比较
 * @author liupeqing
 * @date 2019/2/26 11:30
 */
public class OrderComparator implements Comparator<CicadaInterceptor> {

    @Override
    public int compare(CicadaInterceptor o1, CicadaInterceptor o2) {
        if (o1.getOrder() <= o2.getOrder()){
            return 1;
        }
        return 0;
    }
}
