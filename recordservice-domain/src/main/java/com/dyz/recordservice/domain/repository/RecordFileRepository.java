package com.dyz.recordservice.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dyz.recordservice.domain.entity.Record;
import com.dyz.recordservice.domain.entity.RecordFile;

@Repository
public interface RecordFileRepository extends JpaRepository<RecordFile, Integer> {

	List<RecordFile> queryByRecordId(Integer recordId);

	List<RecordFile> queryByFileId(Integer fileId);

	Record queryById(Integer id);
}
