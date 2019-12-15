package com.dyz.recordservice.api.translation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.time.DateUtils;

import com.dyz.recordservice.api.model.RecordInfoVo;
import com.dyz.recordservice.common.constant.ServiceConstant;
import com.dyz.recordservice.common.execption.IllegalParamException;
import com.dyz.recordservice.common.util.DateHandler;
import com.dyz.recordservice.sal.bo.RecordCreateBo;
import com.dyz.recordservice.sal.bo.RecordInfoBo;
import com.dyz.recordservice.sal.bo.RecordQueryBo;

public class RecordModelTranslator {

	public static RecordQueryBo toBo(String title, Integer userId, String fromTime, String toTime) {
		RecordQueryBo queryBo = null;
		try {
			queryBo = RecordQueryBo.builder().title(title).userId(userId)
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

	public static RecordCreateBo toBo(String title, String content) {
		return RecordCreateBo.builder().title(title).content(content).build();
	}

	public static RecordInfoVo toVo(RecordInfoBo bo) {
		if (Objects.isNull(bo)) {
			return null;
		}
		return RecordInfoVo.builder().title(bo.getTitle()).content(bo.getContent()).userId(bo.getUserId())
				.recordId(bo.getRecordId()).createTime(DateHandler.getDateString(bo.getCreateTime()))
				.fileIds(bo.getFileIds())
                .commentsCount(bo.getCommentsCount()).build();
	}

	public static List<RecordInfoVo> toVoList(List<RecordInfoBo> boList) {
		if (Objects.isNull(boList)) {
			return null;
		}
		List<RecordInfoVo> result = new ArrayList<>();
		boList.stream().forEach(x -> {
			result.add(toVo(x));
		});
		return result;
	}
}
