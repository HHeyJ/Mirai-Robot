package com.hyq.robot.query;

import lombok.*;

/**
 * @author nanke
 * @date 2020/4/4 下午9:04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BarPostQuery extends BaseQuery{

    /**
     * 帖子链接
     */
    private String postUrl;
    /**
     * 0:否 1:删除
     */
    private Integer delete;
}
