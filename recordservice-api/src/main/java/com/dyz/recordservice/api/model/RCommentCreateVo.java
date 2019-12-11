package com.dyz.recordservice.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RCommentCreateVo {

    private Integer recordId;

    private String content;

    private Integer parentId;
}
