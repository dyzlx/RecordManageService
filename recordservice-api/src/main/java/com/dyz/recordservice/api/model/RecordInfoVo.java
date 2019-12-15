package com.dyz.recordservice.api.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecordInfoVo {

	private Integer recordId;

	private String title;
	
	private String content;
	
	private Integer userId;
	
	private String createTime;

    private Integer commentsCount;
	
	private List<Integer> fileIds;
}
