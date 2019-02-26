package com.liupeiqing.http.base.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * logback自定义日志颜色
 * @author liupeqing
 * @date 2019/2/25 10:55
 */
public class HighlightingCompositeConverterEx extends ForegroundCompositeConverterBase<ILoggingEvent> {


    @Override
    protected String getForegroundColorCode(ILoggingEvent iLoggingEvent) {
        Level level = iLoggingEvent.getLevel();
        switch (level.toInt()){
            case Level.ERROR_INT:
                return ANSIConstants.BOLD + ANSIConstants.RED_FG;
            case Level.WARN_INT:
                return ANSIConstants.RED_FG;  //红色
            case Level.INFO_INT:
                return ANSIConstants.BOLD + ANSIConstants.GREEN_FG;
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }
}
