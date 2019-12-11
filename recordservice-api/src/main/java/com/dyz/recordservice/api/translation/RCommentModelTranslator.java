package com.dyz.recordservice.api.translation;


import com.dyz.recordservice.api.model.RCommentCreateVo;
import com.dyz.recordservice.api.model.RCommentInfoVo;
import com.dyz.recordservice.common.constant.ServiceConstant;
import com.dyz.recordservice.common.execption.IllegalParamException;
import com.dyz.recordservice.common.util.DateHandler;
import com.dyz.recordservice.sal.bo.RCommentCreateBo;
import com.dyz.recordservice.sal.bo.RCommentInfoBo;
import com.dyz.recordservice.sal.bo.RCommentQueryBo;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RCommentModelTranslator {

    public static RCommentQueryBo toBo(Integer recordId, Integer publisherId, String fromTime, String toTime) {
        RCommentQueryBo queryBo = null;
        try {
            queryBo = RCommentQueryBo.builder()
                    .recordId(recordId)
                    .publisherId(publisherId)
                    .fromTime(Objects.isNull(fromTime) ? null
                            : DateUtils.parseDate(fromTime, ServiceConstant.DATE_FORMAT_SHORT))
                    .toTime(Objects.isNull(toTime) ? null
                            : DateUtils.parseDate(toTime, ServiceConstant.DATE_FORMAT_SHORT))
                    .build();
        } catch (ParseException e) {
            throw new IllegalParamException(0, "illegal param");
        }
        return queryBo;
    }

    public static RCommentCreateBo toBo(RCommentCreateVo createVo) {
        if (Objects.isNull(createVo)) {
            return null;
        }
        RCommentCreateBo createBo = RCommentCreateBo.builder()
                .recordId(createVo.getRecordId())
                .content(createVo.getContent())
                .parentId(createVo.getParentId())
                .build();
        return createBo;
    }

    public static RCommentInfoVo toVo(RCommentInfoBo infoBo) {
        if (Objects.isNull(infoBo)) {
            return null;
        }
        RCommentInfoVo infoVo = RCommentInfoVo.builder()
                .recordId(infoBo.getRecordId())
                .commentId(infoBo.getCommentId())
                .content(infoBo.getContent())
                .createTime(DateHandler.getDateString(infoBo.getCreateTime()))
                .parentId(infoBo.getParentId())
                .publisherId(infoBo.getPublisherId())
                .directChildCommentIds(infoBo.getDirectChildCommentIds())
                .build();
        return infoVo;
    }

    public static List<RCommentInfoVo> toVoList(List<RCommentInfoBo> infoBos) {
        if (Objects.isNull(infoBos)) {
            return null;
        }
        List<RCommentInfoVo> results = new ArrayList<>();
        infoBos.stream().forEach(x -> {
            results.add(toVo(x));
        });
        return results;
    }
}
