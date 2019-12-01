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
import javax.persistence.Table;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = { "id", "recordId", "commentId" })
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "record_comment")
public class RComment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "record_id", nullable = false)
    private int recordId;

    @Column(name = "comment_id", nullable = false)
    private int commentId;
}
