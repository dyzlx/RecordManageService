package com.dyz.recordservice.sal.service.impl;


import com.dyz.recordservice.sal.access.CommentAccess;
import com.dyz.recordservice.sal.bo.RCommentCreateBo;
import com.dyz.recordservice.sal.bo.RCommentInfoBo;
import com.dyz.recordservice.sal.bo.RCommentQueryBo;
import com.dyz.recordservice.sal.service.RCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Service
public class RCommentServiceImpl implements RCommentService {

    @Autowired
    private CommentAccess commentAccess;

    @Override
    public List<RCommentInfoBo> queryRecordCommentInfo(@NotNull RCommentQueryBo queryBo) {
        log.info("begin to query record comments info, queryBo = {}", queryBo);
        return null;
    }

    @Override
    public Integer createRecordComment(@NotNull RCommentCreateBo createBo, @NotNull Integer userId) {
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
