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
package org.crown.service.impl;

import java.util.Objects;

import org.crown.common.framework.service.impl.BaseServiceImpl;
import org.crown.mapper.MenuMapper;
import org.crown.model.entity.Menu;
import org.crown.service.IMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author Caratacus
 * @since 2018-10-25
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    @Transactional
    public void removeMenu(Integer id) {
        if (parentIdNotNull(id)) {
            list(Wrappers.<Menu>query().eq(Menu.PARENT_ID, id)).forEach(e -> {
                if (parentIdNotNull(e.getParentId())) {
                    removeMenu(e.getId());
                }
            });
            removeById(id);
        }

    }

    /**
     * 父ID不为0并且不为空
     *
     * @param parentId
     * @return
     */
    private boolean parentIdNotNull(Integer parentId) {
        return Objects.nonNull(parentId) && parentId != 0;
    }
}
