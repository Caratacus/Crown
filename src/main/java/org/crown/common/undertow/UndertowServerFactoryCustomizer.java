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
package org.crown.common.undertow;

import java.io.IOException;

import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.xnio.OptionMap;
import org.xnio.Options;
import org.xnio.Xnio;
import org.xnio.XnioWorker;

import io.undertow.connector.ByteBufferPool;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

/**
 * 设置Undertow服务器 XnioWorker Buffers
 *
 * @author Caratacus
 * @see <a>https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-programmatic-embedded-container-customization</a>
 */
public class UndertowServerFactoryCustomizer implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {

    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        UndertowDeploymentInfoCustomizer undertowDeploymentInfoCustomizer = deploymentInfo -> {
            WebSocketDeploymentInfo info = (WebSocketDeploymentInfo) deploymentInfo.getServletContextAttributes().get(WebSocketDeploymentInfo.ATTRIBUTE_NAME);
            XnioWorker worker = getXnioWorker();
            ByteBufferPool buffers = new DefaultByteBufferPool(Boolean.getBoolean("io.undertow.websockets.direct-buffers"), 1024, 100, 12);
            info.setWorker(worker);
            info.setBuffers(buffers);
        };
        factory.addDeploymentInfoCustomizers(undertowDeploymentInfoCustomizer);
    }

    /**
     * 获取XnioWorker
     *
     * @return
     */
    private XnioWorker getXnioWorker() {
        XnioWorker worker = null;
        try {
            worker = Xnio.getInstance().createWorker(OptionMap.create(Options.THREAD_DAEMON, true));
        } catch (IOException ignored) {
        }
        return worker;
    }

}
