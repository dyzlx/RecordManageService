package com.dyz.recordservice.sal.service.impl;


import com.dyz.commentservice.client.model.CommentInfo;
import com.dyz.commentservice.client.model.CommentQueryInfo;
import com.dyz.recordservice.common.constant.ServiceConstant;
import com.dyz.recordservice.common.execption.IllegalParamException;
import com.dyz.recordservice.common.util.DateHandler;
import com.dyz.recordservice.sal.access.CommentAccess;
import com.dyz.recordservice.sal.bo.RCommentCreateBo;
import com.dyz.recordservice.sal.bo.RCommentInfoBo;
import com.dyz.recordservice.sal.bo.RCommentQueryBo;
import com.dyz.recordservice.sal.service.RCommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class RCommentServiceImpl implements RCommentService {

    @Autowired
    private CommentAccess commentAccess;

    @Override
    public List<RCommentInfoBo> queryRecordCommentInfo(@NotNull RCommentQueryBo queryBo) {
        log.info("begin to query record comments info, queryBo = {}", queryBo);
        if (Objects.isNull(queryBo)) {
            throw new IllegalParamException(0, "param is null");
        }
        List<RCommentInfoBo> result = new ArrayList<>();
        CommentQueryInfo clientQueryInfo = CommentQueryInfo.builder()
                .targetResourceId(queryBo.getRecordId())
                .type("record")
                .publisherId(queryBo.getPublisherId())
                .fromTime(DateHandler.getDateString(queryBo.getFromTime()))
                .toTime(DateHandler.getDateString(queryBo.getToTime()))
                .build();
        List<CommentInfo> commentInfos = commentAccess.queryComments(clientQueryInfo);
        commentInfos.forEach(x->{
            try {
                RCommentInfoBo rCommentInfoBo = RCommentInfoBo.builder()
                        .recordId(x.getTargetResourceId())
                        .commentId(x.getCommentId())
                        .content(x.getContent())
                        .createTime(DateUtils.parseDate(x.getCreateTime(), ServiceConstant.DATE_FORMAT_SHORT))
                        .publisherId(x.getPublisherId())
                        .parentId(x.getParentId()).build();
                result.add(rCommentInfoBo);
            } catch (ParseException e) {
                throw new IllegalParamException(0, "illegal param");
            }
        });
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
