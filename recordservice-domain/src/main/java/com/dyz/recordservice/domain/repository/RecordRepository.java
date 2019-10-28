package com.dyz.recordservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.dyz.recordservice.domain.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

	Record queryById(Integer id);

	Record queryByIdAndUserId(Integer id, Integer userId);
}
