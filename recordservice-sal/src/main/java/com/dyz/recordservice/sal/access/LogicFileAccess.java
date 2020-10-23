package com.dyz.recordservice.sal.access;

import com.dyz.filxeservice.client.LogicFileClient;
import com.dyz.filxeservice.client.model.LogicFileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Component
public class LogicFileAccess {

    @Autowired
    private LogicFileClient logicFileClient;

    public List<Integer> uploadFiles(MultipartFile[] files) {
        log.info("trigger remote service to upload file");
        List<Integer> pictureIds = logicFileClient.uploadFiles(files).getContent();
        return pictureIds;
    }

    public void deleteLogicFiles(List<Integer> fileIds) {
        log.info("trigger remote service to delete files");
        logicFileClient.deleteLogicFiles(fileIds);
    }

    public List<LogicFileInfo> queryLogicFileById(Integer logicFileId) {
        log.info("trigger remote service to query logic files");
        return logicFileClient.queryLogicFileByLogicFileId(logicFileId).getContent();
    }
}
