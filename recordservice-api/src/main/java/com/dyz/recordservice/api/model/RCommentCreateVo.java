package com.dyz.recordservice.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RCommentCreateVo {

    @NotNull
    private Integer recordId;

    @NotBlank
    private String content;

    @NotNull
    private Integer parentId;
}
