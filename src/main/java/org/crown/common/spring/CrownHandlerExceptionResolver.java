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
package org.crown.common.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.crown.framework.emuns.ErrorCodeEnum;
import org.crown.framework.exception.ApiException;
import org.crown.framework.utils.ResponseUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.common.base.Throwables;

import lombok.extern.slf4j.Slf4j;

/**
 * 全程异常处理
 * {@link org.springframework.web.servlet.HandlerExceptionResolver
 * {@link org.springframework.web.servlet.DispatcherServlet}.
 *
 * @author Caratacus
 * @see org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
 * @see #handleNoSuchRequestHandlingMethod
 * @see #handleHttpRequestMethodNotSupported
 * @see #handleHttpMediaTypeNotSupported
 * @see #handleMissingServletRequestParameter
 * @see #handleServletRequestBindingException
 * @see #handleTypeMismatch
 * @see #handleHttpMessageNotReadable
 * @see #handleHttpMessageNotWritable
 * @see #handleMethodArgumentNotValidException
 * @see #handleMissingServletRequestParameter
 * @see #handleMissingServletRequestPartException
 * @see #handleBindException
 * @see org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
 */
@Slf4j
public class CrownHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final ModelAndView MODEL_VIEW_INSTANCE = new ModelAndView();

    /**
     * Sets the {@linkplain #setOrder(int) order} to {@link #LOWEST_PRECEDENCE}.
     */
    public CrownHandlerExceptionResolver() {
        setOrder(Ordered.LOWEST_PRECEDENCE);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                              Exception ex) {
        try {

            if (ex instanceof ApiException) {
                handleApi((ApiException) ex, request, response);
            } else if (ex instanceof HttpRequestMethodNotSupportedException) {
                handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) ex, request, response);
            } else if (ex instanceof HttpMediaTypeNotSupportedException) {
                handleHttpMediaTypeNotSupported((HttpMediaTypeNotSupportedException) ex, request, response);
            } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
                handleHttpMediaTypeNotAcceptable((HttpMediaTypeNotAcceptableException) ex, request, response);
            } else if (ex instanceof MissingPathVariableException) {
                handleMissingPathVariable((MissingPathVariableException) ex, request, response);
            } else if (ex instanceof MissingServletRequestParameterException) {
                handleMissingServletRequestParameter((MissingServletRequestParameterException) ex, request, response);
            } else if (ex instanceof ServletRequestBindingException) {
                handleServletRequestBindingException((ServletRequestBindingException) ex, request, response);
            } else if (ex instanceof ConversionNotSupportedException) {
                handleConversionNotSupported((ConversionNotSupportedException) ex, request, response);
            } else if (ex instanceof TypeMismatchException) {
                handleTypeMismatch((TypeMismatchException) ex, request, response);
            } else if (ex instanceof HttpMessageNotReadableException) {
                handleHttpMessageNotReadable((HttpMessageNotReadableException) ex, request, response);
            } else if (ex instanceof HttpMessageNotWritableException) {
                handleHttpMessageNotWritable((HttpMessageNotWritableException) ex, request, response);
            } else if (ex instanceof MethodArgumentNotValidException) {
                handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex, request, response);
            } else if (ex instanceof MissingServletRequestPartException) {
                handleMissingServletRequestPartException((MissingServletRequestPartException) ex, request, response);
            } else if (ex instanceof BindException) {
                handleBindException((BindException) ex, request, response);
            } else if (ex instanceof NoHandlerFoundException) {
                handleNoHandlerFoundException((NoHandlerFoundException) ex, request, response);
            } else if (ex instanceof AsyncRequestTimeoutException) {
                handleAsyncRequestTimeoutException((AsyncRequestTimeoutException) ex, request, response);
            } else if (ex instanceof ConstraintViolationException) {
                handleConstraintViolationException((ConstraintViolationException) ex, request, response);
            } else if (ex instanceof UnauthorizedException) {
                handleUnauthorizedException((UnauthorizedException) ex, request, response);
            } else if (ex instanceof ShiroException) {
                handleShiroException((ShiroException) ex, request, response);
            } else {
                handleException(ex, request, response);
            }
        } catch (Exception handlerException) {
            if (log.isWarnEnabled()) {
                log.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
            }
        }
        if (response.getStatus() < HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            log.info("Info: doResolveInfo {}", ex.getMessage());
        } else {
            log.warn("Warn: doResolveException {}", Throwables.getStackTraceAsString(ex));
        }
        return MODEL_VIEW_INSTANCE;
    }

    /**
     * Handle the case where exception
     *
     * @param ex       the ApiException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleApi(ApiException ex,
                             HttpServletRequest request, HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ex.getErrorCode());

    }

    /**
     * Handle the case where no request handler method was found for the
     * particular HTTP request method.
     * <p>
     * The default implementation logs a warning, sends an HTTP 405 error, sets
     * the "Allow" header,
     * Alternatively, a fallback view could be chosen, or the
     * HttpRequestMethodNotSupportedException could be rethrown as-is.
     *
     * @param ex       the HttpRequestMethodNotSupportedException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                       HttpServletRequest request, HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.METHOD_NOT_ALLOWED, ex);
    }

    /**
     * The implementation logs a warning, sends an HTTP 401 error
     *
     * @param ex       the ShiroException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleShiroException(ShiroException ex,
                                        HttpServletRequest request, HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.UNAUTHORIZED, ex);
    }

    /**
     * The implementation logs a warning, sends an HTTP 403 error
     *
     * @param ex       the UnauthorizedException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleUnauthorizedException(UnauthorizedException ex,
                                               HttpServletRequest request, HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.FORBIDDEN, ex);
    }

    /**
     * Handle the case where no
     * {@linkplain org.springframework.http.converter.HttpMessageConverter
     * message converters} were found for the PUT or POSTed content.
     * <p>
     * The default implementation sends an HTTP 415 error, sets the "Accept"
     * header,  Alternatively, a
     * fallback view could be chosen, or the HttpMediaTypeNotSupportedException
     * could be rethrown as-is.
     *
     * @param ex       the HttpMediaTypeNotSupportedException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest request,
                                                   HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.UNSUPPORTED_MEDIA_TYPE, ex);
    }

    /**
     * Handle the case where no
     * {@linkplain org.springframework.http.converter.HttpMessageConverter
     * message converters} were found that were acceptable for the client
     * (expressed via the {@code Accept} header.
     * <p>
     * The default implementation sends an HTTP 406 error and returns an empty
     * {@code ModelAndView}. Alternatively, a fallback view could be chosen, or
     * the HttpMediaTypeNotAcceptableException could be rethrown as-is.
     *
     * @param ex       the HttpMediaTypeNotAcceptableException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request,
                                                    HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.NOT_ACCEPTABLE, ex);
    }

    /**
     * Handle the case when a declared path variable does not match any
     * extracted URI variable.
     * <p>
     * The default implementation sends an HTTP 500 error, and returns an empty
     * {@code ModelAndView}. Alternatively, a fallback view could be chosen, or
     * the MissingPathVariableException could be rethrown as-is.
     *
     * @param ex       the MissingPathVariableException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleMissingPathVariable(MissingPathVariableException ex, HttpServletRequest request,
                                             HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.INTERNAL_SERVER_ERROR, ex);
    }

    /**
     * Handle the case when a required parameter is missing.
     * <p>
     * The default implementation sends an HTTP 400 error, and returns an empty
     * {@code ModelAndView}. Alternatively, a fallback view could be chosen, or
     * the MissingServletRequestParameterException could be rethrown as-is.
     *
     * @param ex       the MissingServletRequestParameterException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                        HttpServletRequest request, HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.BAD_REQUEST, ex);
    }

    /**
     * Handle the case when an unrecoverable binding exception occurs - e.g.
     * required header, required cookie.
     * <p>
     * The default implementation sends an HTTP 400 error, and returns an empty
     * {@code ModelAndView}. Alternatively, a fallback view could be chosen, or
     * the exception could be rethrown as-is.
     *
     * @param ex       the exception to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleServletRequestBindingException(ServletRequestBindingException ex, HttpServletRequest request,
                                                        HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.BAD_REQUEST, ex);
    }

    /**
     * Handle the case when a {@link org.springframework.web.bind.WebDataBinder}
     * conversion cannot occur.
     * <p>
     * The default implementation sends an HTTP 500 error, and returns an empty
     * {@code ModelAndView}. Alternatively, a fallback view could be chosen, or
     * the TypeMismatchException could be rethrown as-is.
     *
     * @param ex       the ConversionNotSupportedException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleConversionNotSupported(ConversionNotSupportedException ex, HttpServletRequest request,
                                                HttpServletResponse response) {
        sendServerError(ex, request, response);
    }

    /**
     * Handle the case when a {@link org.springframework.web.bind.WebDataBinder}
     * conversion error occurs.
     * <p>
     * The default implementation sends an HTTP 400 error, and returns an empty
     * {@code ModelAndView}. Alternatively, a fallback view could be chosen, or
     * the TypeMismatchException could be rethrown as-is.
     *
     * @param ex       the TypeMismatchException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleTypeMismatch(TypeMismatchException ex, HttpServletRequest request, HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.BAD_REQUEST, ex);
    }

    /**
     * Handle the case where a
     * {@linkplain org.springframework.http.converter.HttpMessageConverter
     * message converter} cannot read from a HTTP request.
     * <p>
     * The default implementation sends an HTTP 400 error, and returns an empty
     * {@code ModelAndView}. Alternatively, a fallback view could be chosen, or
     * the HttpMediaTypeNotSupportedException could be rethrown as-is.
     *
     * @param ex       the HttpMessageNotReadableException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request,
                                                HttpServletResponse response) {
        if (ex.getCause() instanceof JsonParseException) {
            ResponseUtils.sendFail(request, response, ErrorCodeEnum.JSON_FORMAT_ERROR, ex);
        } else {
            ResponseUtils.sendFail(request, response, ErrorCodeEnum.BAD_REQUEST, ex);
        }
    }

    /**
     * Handle the case where a
     * {@linkplain org.springframework.http.converter.HttpMessageConverter
     * message converter} cannot write to a HTTP request.
     * <p>
     * The default implementation sends an HTTP 500 error, and returns an empty
     * {@code ModelAndView}. Alternatively, a fallback view could be chosen, or
     * the HttpMediaTypeNotSupportedException could be rethrown as-is.
     *
     * @param ex       the HttpMessageNotWritableException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpServletRequest request,
                                                HttpServletResponse response) {
        sendServerError(ex, request, response);
    }

    /**
     * Handle the case where an argument annotated with {@code @Valid} such as
     * an {@link RequestBody} or {@link RequestPart} argument fails validation.
     * An HTTP 400 error is sent back to the client.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request,
                                                         HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.BAD_REQUEST, ex);
    }

    /**
     * Handle the case where an {@linkplain RequestPart @RequestPart}, a
     * {@link MultipartFile}, or a {@code javax.servlet.http.Part} argument is
     * required but is missing. An HTTP 400 error is sent back to the client.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleMissingServletRequestPartException(MissingServletRequestPartException ex,
                                                            HttpServletRequest request, HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.BAD_REQUEST, ex);
    }

    /**
     * Handle the case where an {@linkplain ModelAttribute @ModelAttribute}
     * method argument has binding or validation errors and is not followed by
     * another method argument of type {@link BindingResult}. By default, an
     * HTTP 400 error is sent back to the client.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleBindException(BindException ex, HttpServletRequest request, HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.BAD_REQUEST, ex);
    }

    /**
     * Handle the case where no handler was found during the dispatch.
     * <p>
     * The default implementation sends an HTTP 404 error and returns an empty
     * {@code ModelAndView}. Alternatively, a fallback view could be chosen, or
     * the NoHandlerFoundException could be rethrown as-is.
     *
     * @param ex       the NoHandlerFoundException to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request,
                                                 HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.NOT_FOUND, ex);
    }

    /**
     * Handle the case where an async request timed out.
     * <p>
     * The default implementation sends an HTTP 503 error.
     *
     * @param ex       the {@link AsyncRequestTimeoutException }to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpServletRequest request,
                                                      HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.SERVICE_UNAVAILABLE, ex);
    }

    /**
     * Handle the case where an async request timed out.
     * <p>
     * The default implementation sends an HTTP 400 error.
     *
     * @param ex       the {@link ConstraintViolationException }to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request,
                                                      HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.BAD_REQUEST, ex);
    }

    /**
     * Handle the case where an other error.
     * <p>
     * The default implementation sends an HTTP 500 error.
     *
     * @param ex       the {@link Exception }to be handled
     * @param request  current HTTP request
     * @param response current HTTP response
     */
    protected void handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.INTERNAL_SERVER_ERROR, ex);
    }

    /**
     * Invoked to send a server error. Sets the status to 500 and also sets the
     * request attribute "javax.servlet.error.exception" to the Exception.
     */
    protected void sendServerError(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        ResponseUtils.sendFail(request, response, ErrorCodeEnum.INTERNAL_SERVER_ERROR, ex);
    }

}
