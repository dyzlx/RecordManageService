package com.dyz.recordservice.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.dyz.recordservice.domain.entity.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

	Record queryById(Integer id);

	Record queryByIdAndUserId(Integer id, Integer userId);
	
	@Query(value = "select * from record where if(?1 is NULL,1=1,id=?1)"
            + " and if(?2 is NULL,1=1,title like %?2%)"
			+ " and if(?3 is NULL,1=1,user_id=?3)"
			+ " and create_time between ?4 and ?5", nativeQuery = true)
	List<Record> queryRecordInfo(Integer recordId, String title, Integer userId, Date fromDate, Date toDate);
}
