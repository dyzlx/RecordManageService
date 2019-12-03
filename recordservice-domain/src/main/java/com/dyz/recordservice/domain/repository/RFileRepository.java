package com.dyz.recordservice.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dyz.recordservice.domain.entity.Record;
import com.dyz.recordservice.domain.entity.RFile;

@Repository
public interface RFileRepository extends JpaRepository<RFile, Integer> {

	List<RFile> queryByRecordId(Integer recordId);

	RFile queryByFileId(Integer fileId);

	Record queryById(Integer id);
}
