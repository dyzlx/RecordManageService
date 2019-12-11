package com.dyz.recordservice.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RCommentInfoVo {

    private Integer recordId;

    private Integer commentId;

    private String content;

    private Integer publisherId;

    private String createTime;

    private Integer parentId;

    private List<Integer> directChildCommentIds;
}
