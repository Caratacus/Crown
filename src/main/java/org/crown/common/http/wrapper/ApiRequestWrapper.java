package org.crown.common.http.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.crown.common.http.RequestKit;


/**
 * Request包装类
 *
 * @author Caratacus
 */
public class ApiRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 存储requestBody byte[]
     */
    private final byte[] body;

    public ApiRequestWrapper(HttpServletRequest request) {
        super(request);
        this.body = RequestKit.getByteBody(request);
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return bais.read();
            }
        };
    }

} 