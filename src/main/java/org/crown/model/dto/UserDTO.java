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
package org.crown.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.crown.enums.StatusEnum;
import org.crown.framework.model.convert.Convert;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户DTO
 * </p>
 *
 * @author Caratacus
 */
@ApiModel
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends Convert {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "主键")
    private Integer id;
    @ApiModelProperty(notes = "登陆名")
    private String loginName;
    @ApiModelProperty(notes = "昵称")
    private String nickname;
    @ApiModelProperty(notes = "邮箱")
    private String email;
    @ApiModelProperty(notes = "手机")
    private String phone;
    @ApiModelProperty(notes = "IP地址")
    private String ip;
    @ApiModelProperty(notes = "状态:0：禁用 1：正常")
    private StatusEnum status;
    @ApiModelProperty(notes = "创建时间")
    private LocalDateTime createTime;
    @ApiModelProperty(notes = "修改时间")
    private LocalDateTime updateTime;
    @ApiModelProperty(notes = "用户角色ID")
    private List<Integer> roleIds;
}
