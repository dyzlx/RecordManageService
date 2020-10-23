package com.dyz.recordservice.sal.bo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString(exclude = {"content"})
public class RecordCreateBo {

	private String title;
	
	private String content;

	private List<Integer> pictureIds;
}
