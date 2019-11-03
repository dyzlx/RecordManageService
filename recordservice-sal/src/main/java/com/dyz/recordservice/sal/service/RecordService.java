package com.dyz.recordservice.sal.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import com.dyz.recordservice.sal.bo.RecordCreateBo;
import com.dyz.recordservice.sal.bo.RecordInfoBo;
import com.dyz.recordservice.sal.bo.RecordQueryBo;

public interface RecordService {

	/**
	 * query record by query object
	 * @param queryBo
	 * @return
	 */
	List<RecordInfoBo> queryRecordInfo(@NotNull RecordQueryBo queryBo);
	
	/**
	 * create record
	 * @param createBo
	 * @param userId
	 * @return
	 */
	Integer createRecord(MultipartFile[] pictures, @NotNull RecordCreateBo createBo, @NotNull Integer userId);
	
	/**
	 * delete record by record id
	 * @param recordId
	 * @param userId
	 */
	void deleteRecord(@NotNull Integer recordId, @NotNull Integer userId);
	
	/**
	 * download record pictures by record id
	 * @param recordId
	 * @param userId
	 * @param response
	 */
	void downloadRecordPictures(@NotNull Integer recordId, @NotNull Integer userId, @NotNull HttpServletResponse response);
}
