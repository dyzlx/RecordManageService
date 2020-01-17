package com.dyz.recordservice.sal.access;

import com.dyz.filxeservice.client.LogicFileClient;
import com.dyz.filxeservice.client.model.ClientUserContext;
import com.dyz.filxeservice.client.model.ClientUserContextHolder;
import com.dyz.recordservice.common.model.UserContext;
import com.dyz.recordservice.common.model.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class LogicFileAccess {

    @Autowired
    private LogicFileClient logicFileClient;

    public List<Integer> uploadFiles(MultipartFile[] files) {
        log.info("trigger remote service to upload file");
        setClientUserContext();
        List<Integer> pictureIds = logicFileClient.uploadFiles(files).getContent();
        return pictureIds;
    }

    public void deleteLogicFiles(List<Integer> fileIds) {
        setClientUserContext();
        log.info("trigger remote service to delete files");
        logicFileClient.deleteLogicFiles(fileIds);
    }

    private void setClientUserContext() {
        ClientUserContextHolder.setUserContext(transferUserContext(getUserContext()));
    }

    private ClientUserContext transferUserContext(UserContext userContext) {
        if(Objects.isNull(userContext)) {
            return null;
        }
        return ClientUserContext.builder()
                .authToken(userContext.getAuthToken())
                .correlationId(userContext.getCorrelationId())
                .userId(userContext.getUserId())
                .userRoles(userContext.getUserRoles()).build();
    }

    private UserContext getUserContext() {
        return UserContextHolder.getUserContext();
    }
}
