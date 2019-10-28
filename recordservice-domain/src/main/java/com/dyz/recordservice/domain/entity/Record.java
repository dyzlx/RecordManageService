package com.dyz.recordservice.domain.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString(exclude = { "content" })
@EqualsAndHashCode(of = { "id", "userId", "title" })
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "record")
public class Record {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "create_time", nullable = false)
	private Date createTime;

	@Column(name = "user_id", nullable = false)
	private int userId;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "content", columnDefinition = "longtext", nullable = false)
	private String content;

	@Column(name = "title", length = 200, nullable = false)
	private String title;
}
