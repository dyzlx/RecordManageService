package com.dyz.recordservice.sal.service.impl;


import com.dyz.recordservice.sal.access.CommentAccess;
import com.dyz.recordservice.sal.bo.RecordCommentInfoBo;
import com.dyz.recordservice.sal.bo.RecordCommentQueryBo;
import com.dyz.recordservice.sal.service.RecordCommentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RecordCommentServiceImpl implements RecordCommentService {

    @Autowired
    private CommentAccess commentAccess;

    @Override
    public List<RecordCommentInfoBo> queryRecordCommentInfo(@NotNull RecordCommentQueryBo queryBo) {
        return null;
    }

    @Override
    public Integer createRecordComment(@NotNull String content, @NotNull Integer recordId, @NotNull Integer userId) {
        return null;
    }

    @Override
    public void deleteRecordComment(@NotNull Integer commentId, @NotNull Integer userId) {

    }

    @Override
    public Integer getRecordCommentCount(@NotNull Integer recordId, @NotNull Integer userId) {
        return null;
    }
}
