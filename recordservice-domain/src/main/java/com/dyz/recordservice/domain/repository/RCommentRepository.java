package com.dyz.recordservice.domain.repository;

import com.dyz.recordservice.domain.entity.RComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RCommentRepository extends JpaRepository<RComment, Integer> {

    List<RComment> queryByRecordId(Integer recordId);

    RComment queryByCommentId(Integer commentId);
}
