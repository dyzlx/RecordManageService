package com.dyz.recordservice.sal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dyz.filxeservice.client.LogicFileClient;
import com.dyz.recordservice.common.execption.IllegalParamException;
import com.dyz.recordservice.common.execption.NoDataException;
import com.dyz.recordservice.domain.entity.Record;
import com.dyz.recordservice.domain.entity.RecordFile;
import com.dyz.recordservice.domain.repository.RecordFileRepository;
import com.dyz.recordservice.domain.repository.RecordRepository;
import com.dyz.recordservice.sal.bo.RecordCreateBo;
import com.dyz.recordservice.sal.bo.RecordInfoBo;
import com.dyz.recordservice.sal.bo.RecordQueryBo;
import com.dyz.recordservice.sal.service.RecordService;
import com.dyz.recordservice.sal.translation.RecordModelTranslator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {

	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private RecordFileRepository recordFileRepository;

	@Autowired
	private LogicFileClient logicFileClient;

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public List<RecordInfoBo> queryRecordInfo(@NotNull RecordQueryBo queryBo) {
		log.info("begin to query record info, queryBo = {}", queryBo);
		if (Objects.isNull(queryBo)) {
			throw new IllegalParamException(0, "param is null");
		}
		List<Record> records = recordRepository.queryRecordInfo(queryBo.getTitle(), queryBo.getUserId(),
				queryBo.getFromDate(), queryBo.getToDate());
		log.info("query records result = {}", records);
		List<RecordInfoBo> results = RecordModelTranslator.toBoList(records);
		if (CollectionUtils.isNotEmpty(results)) {
			results.stream().forEach(x -> {
				log.info("query file ids by record id = {}", x.getRecordId());
				x.setFileIds(recordFileRepository.queryByRecordId(x.getRecordId()).stream().map(y -> y.getFileId())
						.collect(Collectors.toList()));
			});
		}
		log.info("end of query records, result = {}", results);
		return results;
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public Integer createRecord(MultipartFile[] pictures, @NotNull RecordCreateBo createBo, @NotNull Integer userId) {
		log.info("begin to create record, record title = {}, userId = {}", createBo.getTitle(), userId);
		if (!ObjectUtils.allNotNull(createBo, userId)) {
			throw new IllegalParamException(0, "param is null");
		}
		Record record = Record.builder().content(createBo.getContent()).createTime(new Date())
				.title(createBo.getTitle()).userId(userId).build();
		recordRepository.save(record);
		log.info("record {} has saved", record);
		if (Objects.nonNull(pictures) && pictures.length != 0) {
			log.info("record pictures count is {}, begin to save pictures", pictures.length);
			List<Integer> pictureIds = logicFileClient.uploadFiles(pictures, false, userId);
			log.info("pictures have saved, picture ids = {}", pictureIds);
			for (Integer id : pictureIds) {
				RecordFile recordFile = RecordFile.builder().fileId(id).recordId(record.getId()).build();
				recordFileRepository.save(recordFile);
			}
		}
	    log.info("end of create record");
		return record.getId();
	}

	@Override
	@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
	public void deleteRecord(@NotNull Integer recordId, @NotNull Integer userId) {
		log.info("begin to delete record, recordId = {}, userId = {}", recordId, userId);
		if (!ObjectUtils.allNotNull(recordId, userId)) {
			throw new IllegalParamException(0, "param is null");
		}
		Record record = recordRepository.queryByIdAndUserId(recordId, userId);
		if (Objects.isNull(record)) {
			log.error("no such record");
			throw new NoDataException(0, "no such record");
		}
		recordRepository.delete(record);
		log.info("record object is deleted, {}", record);
		List<Integer> files = recordFileRepository.queryByRecordId(recordId).stream().map(y -> y.getFileId())
				.collect(Collectors.toList());
		log.info("delete record files, fileIds = {}", files);
		if (CollectionUtils.isNotEmpty(files)) {
			logicFileClient.deleteLogicFiles(files, userId);
		}
		log.info("end of delete record");
	}
}
