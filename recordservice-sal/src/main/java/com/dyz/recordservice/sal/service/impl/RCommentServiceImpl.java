package com.dyz.recordservice.sal.service.impl;


import com.dyz.commentservice.client.model.CommentInfo;
import com.dyz.commentservice.client.model.CommentQueryInfo;
import com.dyz.recordservice.common.constant.ServiceConstant;
import com.dyz.recordservice.common.execption.IllegalParamException;
import com.dyz.recordservice.common.execption.NoDataException;
import com.dyz.recordservice.common.util.DateHandler;
import com.dyz.recordservice.domain.entity.RComment;
import com.dyz.recordservice.domain.repository.RCommentRepository;
import com.dyz.recordservice.sal.access.CommentAccess;
import com.dyz.recordservice.sal.bo.RCommentCreateBo;
import com.dyz.recordservice.sal.bo.RCommentInfoBo;
import com.dyz.recordservice.sal.bo.RCommentQueryBo;
import com.dyz.recordservice.sal.service.RCommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RCommentServiceImpl implements RCommentService {

    @Autowired
    private CommentAccess commentAccess;

    @Autowired
    private RCommentRepository rCommentRepository;

    @Override
    public List<RCommentInfoBo> queryRecordCommentInfo(@NotNull RCommentQueryBo queryBo) {
        log.info("begin to query record comments info, queryBo = {}", queryBo);
        if (Objects.isNull(queryBo)) {
            throw new IllegalParamException(0, "param is null");
        }
        List<RCommentInfoBo> commentInfos = commentAccess.queryComments(queryBo);
        log.info("set child comment for each comment");
        commentInfos.forEach(x -> {
            Integer parentId = x.getParentId();
            if (!Objects.equals(parentId, 0)) {
                List<RCommentInfoBo> targetComments = commentInfos.parallelStream()
                        .filter(y -> Objects.equals(y.getCommentId(), parentId)).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(targetComments)) {
                    log.error("can not find parent comment id = {} for comment {}", parentId, x);
                    throw new NoDataException(0, "parent comment not find");
                }
                RCommentInfoBo parentComment = targetComments.get(0);
                parentComment.getDirectChildCommentIds().add(x.getCommentId());
            }
        });
        log.info("end of query comment info, result = {}", commentInfos);
        return null;
    }

    @Override
    public Integer createRecordComment(@NotNull RCommentCreateBo createBo, @NotNull Integer userId) {
        log.info("begin to create record comment, createBo = {}, userId = {}", createBo, userId);
        if(!ObjectUtils.allNotNull(createBo.getContent(), createBo.getParentId(), createBo.getRecordId())){
            throw new IllegalParamException(0, "param is null");
        }
        Integer commentId = commentAccess.createComments(createBo, userId);
        RComment rComment = RComment.builder()
                .commentId(commentId)
                .parentId(createBo.getParentId())
                .recordId(createBo.getRecordId())
                .build();
        log.info("persist local data {}", rComment);
        rCommentRepository.save(rComment);
        log.info("end of create record comment, resourceId = {}", commentId);
        return commentId;
    }

    @Override
    public void deleteRecordComment(@NotNull Integer recordId, @NotNull Integer commentId, @NotNull Integer userId) {
        log.info("begin to delete record comment, recordId = {}, commentId = {}, userId = {}", recordId, commentId, userId);
        if(!ObjectUtils.allNotNull(recordId, commentId, userId)){
            throw new IllegalParamException(0, "param is null");
        }
        RComment rComment = rCommentRepository.queryByRecordIdAndCommentId(recordId, commentId);
        if(Objects.isNull(rComment)){
            log.error("no such record comment, recordId = {} commentId = {}", recordId, commentId);
            throw new NoDataException(0, "no such record comment");
        }
        commentAccess.deleteComments(commentId, userId);
        log.info("delete local data");
        rCommentRepository.delete(rComment);
        log.info("end of delete record comment");
    }

    @Override
    public Integer getRecordCommentCount(@NotNull Integer recordId, @NotNull Integer userId) {
        log.info("begin to query record comment count, recordId = {}, userId = {}", recordId, userId);
        if(!ObjectUtils.allNotNull(recordId, userId)){
            throw new IllegalParamException(0, "param is null");
        }
        List<RComment> rComments = rCommentRepository.queryByRecordId(recordId);
        int count = rComments.size();
        log.info("end of query record comment count = {}", count);
        return count;
    }
}
