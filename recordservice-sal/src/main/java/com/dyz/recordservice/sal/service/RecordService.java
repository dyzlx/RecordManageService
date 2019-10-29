package com.dyz.recordservice.sal.service;

import java.util.List;

import com.dyz.recordservice.sal.bo.RecordCreateBo;
import com.dyz.recordservice.sal.bo.RecordInfoBo;
import com.dyz.recordservice.sal.bo.RecordQueryBo;

public interface RecordService {

	/**
	 * 
	 * @param queryBo
	 * @return
	 */
	List<RecordInfoBo> queryRecordInfo(RecordQueryBo queryBo);
	
	/**
	 * 
	 * @param createBo
	 * @param userId
	 * @return
	 */
	Integer createRecord(RecordCreateBo createBo, Integer userId);
	
	/**
	 * 
	 * @param recordId
	 * @param userId
	 */
	void deleteRecord(Integer recordId, Integer userId);
}
