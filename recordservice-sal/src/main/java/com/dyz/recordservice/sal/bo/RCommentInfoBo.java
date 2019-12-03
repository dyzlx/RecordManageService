package com.dyz.recordservice.sal.bo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class RCommentInfoBo {

    private Integer recordId;

    private Integer commentId;

    private String content;

    private Integer publisherId;

    private Date createTime;

    private Integer parentId;

    private List<Integer> directChildCommentIds;
}
