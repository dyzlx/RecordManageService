package com.dyz.recordservice.sal.access;

import com.dyz.commentservice.client.CommentClient;
import com.dyz.commentservice.client.model.CommentCreateInfo;
import com.dyz.commentservice.client.model.CommentInfo;
import com.dyz.commentservice.client.model.CommentQueryInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CommentAccess {

    @Autowired
    private CommentClient commentClient;

    public List<CommentInfo> queryComments(CommentQueryInfo queryInfo) {
        log.info("trigger remote service to query comments");
        List<CommentInfo> comments = commentClient.queryComment(queryInfo).getContent();
        return comments;
    }

    public Integer createComments(CommentCreateInfo createInfo, Integer userId) {
        log.info("trigger remote serivce to create comment");
        Integer commentId = commentClient.createComment(createInfo, userId).getContent();
        return commentId;
    }

    public void deleteComments(Integer commentId, Integer userId) {
        log.info("trigger remote service to delete comment");
        commentClient.deleteComment(commentId, userId);
    }
}
