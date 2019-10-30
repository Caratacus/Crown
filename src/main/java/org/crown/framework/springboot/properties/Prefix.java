package org.crown.framework.springboot.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Prefix {

    /**
     * 头像上传路径前缀
     */
    private String avatar = "avatar/";
    /**
     * 下载路径前缀
     */
    private String download = "download/";
    /**
     * 上传路径前缀
     */
    private String upload = "upload/";
}