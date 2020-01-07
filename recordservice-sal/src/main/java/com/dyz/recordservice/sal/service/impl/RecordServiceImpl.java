package com.dyz.recordservice.sal.service.impl;

import com.dyz.recordservice.common.execption.IllegalParamException;
import com.dyz.recordservice.common.execption.NoDataException;
import com.dyz.recordservice.domain.entity.RFile;
import com.dyz.recordservice.domain.entity.Record;
import com.dyz.recordservice.domain.repository.RCommentRepository;
import com.dyz.recordservice.domain.repository.RFileRepository;
import com.dyz.recordservice.domain.repository.RecordRepository;
import com.dyz.recordservice.sal.access.LogicFileAccess;
import com.dyz.recordservice.sal.bo.RecordCreateBo;
import com.dyz.recordservice.sal.bo.RecordInfoBo;
import com.dyz.recordservice.sal.bo.RecordQueryBo;
import com.dyz.recordservice.sal.service.RecordService;
import com.dyz.recordservice.sal.translation.RecordModelTranslator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private RFileRepository rFileRepository;

    @Autowired
    private RCommentRepository rCommentRepository;

    @Autowired
    private LogicFileAccess logicFileAccess;

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public List<RecordInfoBo> queryRecordInfo(@NotNull RecordQueryBo queryBo) {
        log.info("begin to query record info, queryBo = {}", queryBo);
        if (Objects.isNull(queryBo)) {
            throw new IllegalParamException(0, "param is null");
        }
        List<Record> records = recordRepository.queryRecordsInfo(queryBo.getRecordId(), queryBo.getTitle(), queryBo.getUserId(),
                queryBo.getFromTime(), queryBo.getToTime());
        log.info("query records result = {}", records);
        List<RecordInfoBo> results = RecordModelTranslator.toBoList(records);
        if (CollectionUtils.isNotEmpty(results)) {
            results.forEach(x -> {
                log.info("query file ids by record id = {}", x.getRecordId());
                x.setFileIds(rFileRepository.queryByRecordId(x.getRecordId()).stream().map(RFile::getFileId)
                        .collect(Collectors.toList()));
                log.info("query record comments count bu record id = {}", x.getRecordId());
                int commentCount = rCommentRepository.countByRecordId(x.getRecordId());
                x.setCommentsCount(commentCount);
            });
        }
        log.info("end of query records, result = {}", results);
        return results;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
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
            List<Integer> pictureIds = logicFileAccess.uploadFiles(transferMultipartFiles(pictures), userId);
            log.info("pictures have saved, picture ids = {}", pictureIds);
            for (Integer id : pictureIds) {
                RFile recordFile = RFile.builder().fileId(id).recordId(record.getId()).build();
                rFileRepository.save(recordFile);
            }
        }
        log.info("end of create record");
        return record.getId();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
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
        List<RFile> recordFiles = rFileRepository.queryByRecordId(recordId);
        List<Integer> files = recordFiles.stream().map(RFile::getFileId).collect(Collectors.toList());
        log.info("delete record files, fileIds = {}", files);
        if (CollectionUtils.isNotEmpty(files)) {
            logicFileAccess.deleteLogicFiles(files, userId);
            log.info("delete relation of record and file");
            rFileRepository.deleteAll(recordFiles);
        }
        log.info("end of delete record");
    }

    /**
     * transfer MultipartFile
     *
     * @param files
     * @return
     */
    private MultipartFile[] transferMultipartFiles(MultipartFile[] files) {
        log.info("begin to transfer multipart files, count = {}", files.length);
        if (Objects.isNull(files)) {
            return null;
        }
        MultipartFile[] resultFiles = new MultipartFile[files.length];
        int fileIndex = 0;
        for (MultipartFile file : files) {
            DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("file", MediaType.ALL_VALUE,
                    true, file.getOriginalFilename());
            InputStream input = null;
            try {
                input = file.getInputStream();
                OutputStream os = fileItem.getOutputStream();
                IOUtils.copy(input, os);
                MultipartFile result = new CommonsMultipartFile(fileItem);
                resultFiles[fileIndex++] = result;
            } catch (IOException e) {
                log.error("transfer multipart file fail!", e);
            }
        }
        log.info("end of transfer multipart file");
        return resultFiles;
    }
}
