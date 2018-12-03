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
package org.crown.common.cons;

public interface Regex {

    /**
     * 正则表达式：验证用户名
     */
    String USERNAME = "^[a-zA-Z]\\w{5,17}$";
    /**
     * 正则表达式：验证密码 (必须字母与数字组合)
     */
    String PASSWORD = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,14}$";
    /**
     * 正则表达式：验证手机号
     */
    String PHONE = "^1[3,4,5,7,8]\\d{9}$";
    /**
     * 正则表达式：验证邮箱
     */
    String EMAIL = "^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 正则表达式：验证汉字
     */
    String CHINESE = "^[\u4e00-\u9fa5],*$";
    /**
     * 正则表达式：验证身份证
     */
    String ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    /**
     * 正则表达式：验证URL
     */
    String URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
    /**
     * 正则表达式：验证IP地址
     */
    String IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    /**
     * 正则表达式：验证数字
     */
    String NUMBER = "[0-9]*";
    /**
     * 正则表达式：验证图片格式
     */
    String IMAGE = "(?i)(jpg|jpeg|png|gif)$";
}
