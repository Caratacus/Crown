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
package org.crown.framework;

import java.util.Objects;

import org.crown.CrownApplication;
import org.crown.common.utils.JacksonUtils;
import org.crown.framework.exception.CrownException;
import org.crown.framework.responses.SuccessResponses;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * <p>
 * SuperRestController 测试类
 * </p>
 *
 * @author Caratacus
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CrownApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@Transactional
@Rollback
public class SuperRestControllerTest {

    /**
     * 获取MockMvc
     *
     * @param controllers
     * @return
     */
    public MockMvc getMockMvc(Object... controllers) {
        return MockMvcBuilders.standaloneSetup(controllers).build();
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param method
     * @param url
     * @param authorization
     * @param object
     * @param params
     * @return
     */
    private MockHttpServletRequestBuilder getMockMvcRequestBodyBuilder(HttpMethod method, String url, String authorization, Object object, MultiValueMap<String, String> params) {
        MockHttpServletRequestBuilder requestBuilder;
        switch (method) {
            case GET:
                requestBuilder = MockMvcRequestBuilders.get(url);
                if (Objects.nonNull(params)) {
                    requestBuilder.params(params);
                }
                break;
            case POST:
                requestBuilder = MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON);
                if (Objects.nonNull(object)) {
                    requestBuilder.content(JacksonUtils.toJson(object));
                }
                break;
            case PUT:
                requestBuilder = MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON);
                if (Objects.nonNull(object)) {
                    requestBuilder.content(JacksonUtils.toJson(object));
                }
                break;
            case PATCH:
                requestBuilder = MockMvcRequestBuilders.patch(url).contentType(MediaType.APPLICATION_JSON);
                if (Objects.nonNull(object)) {
                    requestBuilder.content(JacksonUtils.toJson(object));
                }
                break;
            case DELETE:
                requestBuilder = MockMvcRequestBuilders.delete(url);
                if (Objects.nonNull(params)) {
                    requestBuilder.params(params);
                }
                break;
            default:
                throw new CrownException("Error: This request method is not supported.");

        }
        if (!StringUtils.isEmpty(authorization)) {
            requestBuilder.header("Authorization", "Bearer " + authorization);
        }
        return requestBuilder;
    }


    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @param params
     * @return
     */
    public MockHttpServletRequestBuilder get(String url, String authorization, MultiValueMap<String, String> params) {
        return getMockMvcRequestBodyBuilder(HttpMethod.GET, url, authorization, null, params);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @return
     */
    public MockHttpServletRequestBuilder get(String url, String authorization) {
        return getMockMvcRequestBodyBuilder(HttpMethod.GET, url, authorization, null, null);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @param params
     * @return
     */
    public MockHttpServletRequestBuilder delete(String url, String authorization, MultiValueMap<String, String> params) {
        return getMockMvcRequestBodyBuilder(HttpMethod.DELETE, url, authorization, null, params);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @return
     */
    public MockHttpServletRequestBuilder delete(String url, String authorization) {
        return getMockMvcRequestBodyBuilder(HttpMethod.DELETE, url, authorization, null, null);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @param object
     * @return
     */
    public MockHttpServletRequestBuilder post(String url, String authorization, Object object) {
        return getMockMvcRequestBodyBuilder(HttpMethod.POST, url, authorization, object, null);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @return
     */
    public MockHttpServletRequestBuilder post(String url, String authorization) {
        return getMockMvcRequestBodyBuilder(HttpMethod.POST, url, authorization, null, null);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @param object
     * @return
     */
    public MockHttpServletRequestBuilder put(String url, String authorization, Object object) {
        return getMockMvcRequestBodyBuilder(HttpMethod.PUT, url, authorization, object, null);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @return
     */
    public MockHttpServletRequestBuilder put(String url, String authorization) {
        return getMockMvcRequestBodyBuilder(HttpMethod.PUT, url, authorization, null, null);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @param object
     * @return
     */
    public MockHttpServletRequestBuilder patch(String url, String authorization, Object object) {
        return getMockMvcRequestBodyBuilder(HttpMethod.PATCH, url, authorization, object, null);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @return
     */
    public MockHttpServletRequestBuilder patch(String url, String authorization) {
        return getMockMvcRequestBodyBuilder(HttpMethod.PATCH, url, authorization, null, null);
    }

    /**
     * 获取Mock测试请求结果是否为200
     *
     * @param mvc
     * @param builder
     * @return
     * @throws Exception
     */
    public ResultActions isOk(MockMvc mvc, MockHttpServletRequestBuilder builder) throws Exception {
        return getResultActions(mvc, builder).andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 获取Mock测试请求结果是否为204
     *
     * @param mvc
     * @param builder
     * @return
     * @throws Exception
     */
    public ResultActions isNoContent(MockMvc mvc, MockHttpServletRequestBuilder builder) throws Exception {
        return getResultActions(mvc, builder).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * 获取ResultActions
     *
     * @param mvc
     * @param builder
     * @return
     * @throws Exception
     */
    private ResultActions getResultActions(MockMvc mvc, MockHttpServletRequestBuilder builder) throws Exception {
        return mvc.perform(builder).andDo(MockMvcResultHandlers.print());
    }

    /**
     * 获取Mock测试请求结果是否为201
     *
     * @param mvc
     * @param builder
     * @return
     * @throws Exception
     */
    public ResultActions isCreated(MockMvc mvc, MockHttpServletRequestBuilder builder) throws Exception {
        return getResultActions(mvc, builder).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    /**
     * 获取MockMvc测试字符串返回
     *
     * @param mvc
     * @param builder
     * @return
     * @throws Exception
     */
    public MockHttpServletResponse getResponse(MockMvc mvc, MockHttpServletRequestBuilder builder) throws Exception {
        return isOk(mvc, builder).andReturn().getResponse();
    }

    /**
     * 获取MockMvc测试实体
     *
     * @param mvc
     * @param builder
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T getResult(MockMvc mvc, MockHttpServletRequestBuilder builder,
                           TypeReference<SuccessResponses<T>> reference) throws Exception {
        String responseString = getResponse(mvc, builder).getContentAsString();
        SuccessResponses<T> responses = JacksonUtils.readValue(responseString, reference);
        return responses.getResult();
    }


    @SuppressWarnings("EmptyMethod")
    @Test
    public void test() {
    }

}
