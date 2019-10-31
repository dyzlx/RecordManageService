package com.dyz.recordservice.sal.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.dyz.recordservice.sal.bo.RecordCreateBo;
import com.dyz.recordservice.sal.bo.RecordInfoBo;
import com.dyz.recordservice.sal.bo.RecordQueryBo;

public interface RecordService {

	/**
	 * 
	 * @param queryBo
	 * @return
	 */
	List<RecordInfoBo> queryRecordInfo(@NotNull RecordQueryBo queryBo);
	
	/**
	 * 
	 * @param createBo
	 * @param userId
	 * @return
	 */
	Integer createRecord(MultipartFile[] pictures, @NotNull RecordCreateBo createBo, @NotNull Integer userId);
	
	/**
	 * 
	 * @param recordId
	 * @param userId
	 */
	void deleteRecord(@NotNull Integer recordId, @NotNull Integer userId);
}
