package com.dyz.recordservice.sal.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;

import com.dyz.recordservice.domain.entity.Record;
import com.dyz.recordservice.sal.bo.RecordInfoBo;

public class RecordModelTranslator {

	public static RecordInfoBo toBo(Record record) {
		if (Objects.isNull(record)) {
			return null;
		}
		return RecordInfoBo.builder().content(record.getContent()).createTime(record.getCreateTime())
				.title(record.getTitle()).userId(record.getUserId()).recordId(record.getId()).build();
	}

	public static List<RecordInfoBo> toBoList(List<Record> records) {
		if (Objects.isNull(records)) {
			return null;
		}
		List<RecordInfoBo> results = new ArrayList<>();
		records.stream().forEach(x -> {
			results.add(toBo(x));
		});
		return results;
	}

}
