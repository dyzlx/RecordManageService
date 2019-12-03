package com.dyz.recordservice.sal.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RCommentCreateBo {

    private Integer recordId;

    private String content;

    private Integer parentId;

}
