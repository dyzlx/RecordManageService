package com.dyz.recordservice.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(of = { "id", "recordId", "fileId" })
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "record_file")
public class RecordFile {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "record_id", nullable = false)
	private int recordId;

	@Column(name = "file_id", nullable = false)
	private int fileId;
}
