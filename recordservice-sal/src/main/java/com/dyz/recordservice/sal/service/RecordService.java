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
     *
     * @param queryBo
     * @return
     */
    List<RecordInfoBo> queryRecordInfo(RecordQueryBo queryBo);

    /**
     * create record
     *
     * @param createBo
     * @return
     */
    Integer createRecord(MultipartFile[] pictures, RecordCreateBo createBo);

    /**
     * delete record by record id
     *
     * @param recordId
     */
    void deleteRecord(Integer recordId);

}
