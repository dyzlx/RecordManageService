package com.dyz.recordservice.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RCommentCreateVo {

    private Integer recordId;

    private String content;

    private Integer parentId;
}
