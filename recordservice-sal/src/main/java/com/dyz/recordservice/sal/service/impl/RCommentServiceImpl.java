package com.dyz.recordservice.sal.service.impl;


import com.dyz.commentservice.client.model.CommentInfo;
import com.dyz.commentservice.client.model.CommentQueryInfo;
import com.dyz.recordservice.common.constant.ServiceConstant;
import com.dyz.recordservice.common.execption.IllegalParamException;
import com.dyz.recordservice.common.execption.NoDataException;
import com.dyz.recordservice.common.model.UserContext;
import com.dyz.recordservice.common.model.UserContextHolder;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        log.info("begin to query record comments info, queryBo = {}, user context = {}", queryBo, getUserContext());
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
        log.info("update local data");
        // sync method to update local comment data if necessary
        // TODO
        log.info("end of query comment info, result = {}", commentInfos);
        return commentInfos;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public Integer createRecordComment(RCommentCreateBo createBo) {
        log.info("begin to create record comment, createBo = {}, user context = {}", createBo, getUserId());
        if (!ObjectUtils.allNotNull(createBo.getContent(), createBo.getParentId(), createBo.getRecordId())) {
            throw new IllegalParamException(0, "param is null");
        }
        Integer commentId = commentAccess.createComments(createBo);
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
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void deleteRecordComment(Integer recordId, Integer commentId) {
        log.info("begin to delete record comment, recordId = {}, commentId = {}, user context = {}", recordId, commentId, getUserId());
        if (!ObjectUtils.allNotNull(recordId, commentId)) {
            throw new IllegalParamException(0, "param is null");
        }
        RComment rComment = rCommentRepository.queryByRecordIdAndCommentId(recordId, commentId);
        if (Objects.isNull(rComment)) {
            log.error("no such record comment, recordId = {} commentId = {}", recordId, commentId);
            throw new NoDataException(0, "no such record comment");
        }
        commentAccess.deleteComments(commentId);
        log.info("delete local data");
        rCommentRepository.delete(rComment);
        log.info("end of delete record comment");
    }

    @Override
    public Integer getRecordCommentCount(Integer recordId) {
        log.info("begin to query record comment count, recordId = {}, user context = {}", recordId, getUserContext());
        if (Objects.isNull(recordId)) {
            throw new IllegalParamException(0, "param is null");
        }
        int count = rCommentRepository.countByRecordId(recordId);
        log.info("end of query record comment count = {}", count);
        return count;
    }

    /**
     * get user id from user context
     *
     * @return
     */
    public Integer getUserId() {
        return getUserContext().getUserId();
    }

    /**
     * get user context from user context holder
     *
     * @return
     */
    public UserContext getUserContext() {
        return UserContextHolder.getUserContext();
    }
}
