package com.dyz.recordservice.sal.service;


import com.dyz.recordservice.sal.bo.RCommentCreateBo;
import com.dyz.recordservice.sal.bo.RCommentInfoBo;
import com.dyz.recordservice.sal.bo.RCommentQueryBo;

import java.util.List;

public interface RCommentService {

    /**
     * @param queryBo
     * @return
     */
    List<RCommentInfoBo> queryRecordCommentInfo(RCommentQueryBo queryBo);

    /**
     * @param createBo
     * @return
     */
    Integer createRecordComment(RCommentCreateBo createBo);

    /**
     * @param recordId
     * @param commentId
     */
    void deleteRecordComment(Integer recordId, Integer commentId);

    /**
     * @param recordId
     * @return
     */
    Integer getRecordCommentCount(Integer recordId);
}
