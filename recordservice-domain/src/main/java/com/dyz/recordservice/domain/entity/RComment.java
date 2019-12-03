package com.dyz.recordservice.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"recordId", "commentId"})
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "record_comment")
@IdClass(RCommentKey.class)
public class RComment {

    @Id
    @Column(name = "record_id", nullable = false)
    private int recordId;

    @Id
    @Column(name = "comment_id", nullable = false)
    private int commentId;

    @Column(name = "parent_id", nullable = false)
    private int parentId;
}

class RCommentKey implements Serializable {

    private int recordId;

    private int commentId;
}