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
import org.crown.common.api.model.responses.SuccessResponses;
import org.crown.common.exception.CrownException;
import org.crown.common.kit.JacksonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
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
     * @param httpMethod
     * @param url
     * @param authorization
     * @param object
     * @param params
     * @return
     */
    private MockHttpServletRequestBuilder getMockMvcRequestBodyBuilders(HttpMethod httpMethod, String url, String authorization, Object object, MultiValueMap<String, String> params) {
        MockHttpServletRequestBuilder requestBuilder;
        switch (httpMethod) {
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
        return getMockMvcRequestBodyBuilders(HttpMethod.GET, url, authorization, null, params);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @return
     */
    public MockHttpServletRequestBuilder get(String url, String authorization) {
        return getMockMvcRequestBodyBuilders(HttpMethod.GET, url, authorization, null, null);
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
        return getMockMvcRequestBodyBuilders(HttpMethod.DELETE, url, authorization, null, params);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @return
     */
    public MockHttpServletRequestBuilder delete(String url, String authorization) {
        return getMockMvcRequestBodyBuilders(HttpMethod.DELETE, url, authorization, null, null);
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
        return getMockMvcRequestBodyBuilders(HttpMethod.POST, url, authorization, object, null);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @return
     */
    public MockHttpServletRequestBuilder post(String url, String authorization) {
        return getMockMvcRequestBodyBuilders(HttpMethod.POST, url, authorization, null, null);
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
        return getMockMvcRequestBodyBuilders(HttpMethod.PUT, url, authorization, object, null);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @return
     */
    public MockHttpServletRequestBuilder put(String url, String authorization) {
        return getMockMvcRequestBodyBuilders(HttpMethod.PUT, url, authorization, null, null);
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
        return getMockMvcRequestBodyBuilders(HttpMethod.PATCH, url, authorization, object, null);
    }

    /**
     * 获取MockMvcRequestBodyBuilders
     *
     * @param url
     * @param authorization
     * @return
     */
    public MockHttpServletRequestBuilder patch(String url, String authorization) {
        return getMockMvcRequestBodyBuilders(HttpMethod.PATCH, url, authorization, null, null);
    }

    /**
     * 获取MockMvc测试字符串返回
     *
     * @param mockMvc
     * @param mockHttpServletRequestBuilder
     * @return
     * @throws Exception
     */
    public void responseOk(MockMvc mockMvc, MockHttpServletRequestBuilder mockHttpServletRequestBuilder) throws Exception {
        mockMvc.perform(
                mockHttpServletRequestBuilder
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 获取MockMvc测试字符串返回
     *
     * @param mockMvc
     * @param mockHttpServletRequestBuilder
     * @return
     * @throws Exception
     */
    public String getResponseString(MockMvc mockMvc, MockHttpServletRequestBuilder mockHttpServletRequestBuilder) throws Exception {
        return mockMvc.perform(
                mockHttpServletRequestBuilder
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse().getContentAsString();
    }

    /**
     * 获取MockMvc测试实体
     *
     * @param mockMvc
     * @param mockHttpServletRequestBuilder
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T getResponseModel(MockMvc mockMvc, MockHttpServletRequestBuilder mockHttpServletRequestBuilder,
                                  TypeReference<SuccessResponses<T>> valueTypeRef) throws Exception {
        String responseString = getResponseString(mockMvc, mockHttpServletRequestBuilder);
        SuccessResponses<T> responses = JacksonUtils.readValue(responseString, valueTypeRef);
        return responses.getResult();
    }


    @SuppressWarnings("EmptyMethod")
    @Test
    public void test() {
    }

}
