package org.crown.framework.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 树形节点
 * </p>
 *
 * @author Caratacus
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TreeNode extends BaseModel {

    protected Integer parentId;

    protected List<TreeNode> childrens = new ArrayList<>();
}
