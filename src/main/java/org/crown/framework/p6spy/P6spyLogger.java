/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.framework.p6spy;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;

import lombok.extern.slf4j.Slf4j;

/**
 * P6spy日志实现
 * <p>
 * https://blog.csdn.net/z69183787/article/details/43453581
 * <p/>
 *
 * @author Caratacus
 * @see com.p6spy.engine.spy.appender.FormattedLogger
 */
@Slf4j
public class P6spyLogger extends FormattedLogger {

    @Override
    public void logException(Exception e) {
        log.info("", e);
    }

    @Override
    public void logText(String text) {
        log.info(text);
    }

    @Override
    public void logSQL(int connectionId, String now, long elapsed,
                       Category category, String prepared, String sql, String url) {
        final String msg = strategy.formatMessage(connectionId, now, elapsed,
                category.toString(), prepared, sql, url);

        if (StringUtils.isEmpty(msg)) {
            return;
        }
        if (Category.ERROR.equals(category)) {
            log.error(msg);
        } else if (Category.WARN.equals(category)) {
            log.warn(msg);
        } else if (Category.DEBUG.equals(category)) {
            log.debug(msg);
        } else {
            log.info(msg);
        }
    }

    @Override
    public boolean isCategoryEnabled(Category category) {
        if (Category.ERROR.equals(category)) {
            return log.isErrorEnabled();
        } else if (Category.WARN.equals(category)) {
            return log.isWarnEnabled();
        } else if (Category.DEBUG.equals(category)) {
            return log.isDebugEnabled();
        } else {
            return log.isInfoEnabled();
        }
    }
}
