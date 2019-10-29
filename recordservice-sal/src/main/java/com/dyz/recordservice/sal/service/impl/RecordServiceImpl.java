package com.dyz.recordservice.sal.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dyz.filxeservice.client.LogicFileClient;
import com.dyz.recordservice.common.execption.IllegalParamException;
import com.dyz.recordservice.common.execption.NoDataException;
import com.dyz.recordservice.domain.entity.Record;
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
	private LogicFileClient logicfileClient;

	@Override
	public List<RecordInfoBo> queryRecordInfo(RecordQueryBo queryBo) {
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
	public Integer createRecord(RecordCreateBo createBo, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRecord(Integer recordId, Integer userId) {
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
		log.info("record object is deleted");
		List<Integer> files = recordFileRepository.queryByRecordId(recordId).stream().map(y -> y.getFileId())
				.collect(Collectors.toList());
		log.info("delete record files, fileIds = {}", files);
		if (CollectionUtils.isNotEmpty(files)) {
			files.stream().forEach(x -> {
				log.info("tigger logic file client to delete record file");
				logicfileClient.deleteLogicFile(x);
			});
		}
		log.info("end of delete record");
	}
}
