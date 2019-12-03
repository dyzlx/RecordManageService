package com.dyz.recordservice.sal.service;


import com.dyz.recordservice.sal.bo.RCommentCreateBo;
import com.dyz.recordservice.sal.bo.RCommentInfoBo;
import com.dyz.recordservice.sal.bo.RCommentQueryBo;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RCommentService {

    /**
     *
     * @param queryBo
     * @return
     */
    List<RCommentInfoBo> queryRecordCommentInfo(@NotNull RCommentQueryBo queryBo);

    /**
     *
     * @param userId
     * @return
     */
    Integer createRecordComment(@NotNull RCommentCreateBo createBo, @NotNull Integer userId);

    /**
     *
     * @param commentId
     * @param userId
     */
    void deleteRecordComment(@NotNull Integer commentId, @NotNull Integer userId);

    /**
     *
     * @param recordId
     * @param userId
     * @return
     */
    Integer getRecordCommentCount(@NotNull Integer recordId, @NotNull Integer userId);
}
