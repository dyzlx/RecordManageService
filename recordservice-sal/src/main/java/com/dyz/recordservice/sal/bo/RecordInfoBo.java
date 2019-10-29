package com.dyz.recordservice.sal.bo;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecordInfoBo {
	
	private Integer recordId;

	private String title;
	
	private String content;
	
	private Integer userId;
	
	private Date createTime;
	
	private List<Integer> fileIds;
}
