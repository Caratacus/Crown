package org.crown.cons;

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
