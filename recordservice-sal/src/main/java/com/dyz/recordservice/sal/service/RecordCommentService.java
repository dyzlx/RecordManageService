package com.dyz.recordservice.sal.service;


import com.dyz.recordservice.sal.bo.RecordCommentInfoBo;
import com.dyz.recordservice.sal.bo.RecordCommentQueryBo;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RecordCommentService {

    /**
     *
     * @param queryBo
     * @return
     */
    List<RecordCommentInfoBo> queryRecordCommentInfo(@NotNull RecordCommentQueryBo queryBo);

    /**
     *
     * @param content
     * @param recordId
     * @param userId
     * @return
     */
    Integer createRecordComment(@NotNull String content, @NotNull Integer recordId, @NotNull Integer userId);

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
