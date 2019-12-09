package com.dyz.recordservice.sal.access;

import com.dyz.commentservice.client.CommentClient;
import com.dyz.commentservice.client.model.CommentCreateInfo;
import com.dyz.commentservice.client.model.CommentInfo;
import com.dyz.commentservice.client.model.CommentQueryInfo;
import com.dyz.recordservice.common.constant.ServiceConstant;
import com.dyz.recordservice.common.execption.IllegalParamException;
import com.dyz.recordservice.common.execption.RemoteServiceException;
import com.dyz.recordservice.common.util.DateHandler;
import com.dyz.recordservice.sal.bo.RCommentCreateBo;
import com.dyz.recordservice.sal.bo.RCommentInfoBo;
import com.dyz.recordservice.sal.bo.RCommentQueryBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CommentAccess {

    @Autowired
    private CommentClient commentClient;

    public List<RCommentInfoBo> queryComments(RCommentQueryBo queryBo) {
        log.info("trigger remote service to query comments");
        List<RCommentInfoBo> result = new ArrayList<>();
        CommentQueryInfo clientQueryInfo = CommentQueryInfo.builder()
                .targetResourceId(queryBo.getRecordId())
                .type("record")
                .publisherId(queryBo.getPublisherId())
                .fromTime(DateHandler.getDateString(queryBo.getFromTime()))
                .toTime(DateHandler.getDateString(queryBo.getToTime()))
                .build();
        List<CommentInfo> comments = commentClient.queryComment(clientQueryInfo).getContent();
        log.info("get data from remote service: {}", comments);
        comments.forEach(x -> {
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
        return result;
    }

    public Integer createComments(RCommentCreateBo createBo, Integer userId) {
        log.info("trigger remote serivce to create comment");
        CommentCreateInfo createInfo = CommentCreateInfo.builder()
                .content(createBo.getContent())
                .parentId(createBo.getParentId())
                .targetResourceId(createBo.getRecordId())
                .type("record").build();
        Integer commentId = commentClient.createComment(createInfo, userId).getContent();
        return commentId;
    }

    public void deleteComments(Integer commentId, Integer userId) {
        log.info("trigger remote service to delete comment");
        commentClient.deleteComment(commentId, userId);
    }
}
