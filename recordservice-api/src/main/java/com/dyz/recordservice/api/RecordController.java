package com.dyz.recordservice.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.dyz.recordservice.api.model.CommonPostResponse;
import com.dyz.recordservice.api.model.RecordInfoVo;
import com.dyz.recordservice.api.model.Result;
import com.dyz.recordservice.api.translation.RecordModelTranslator;
import com.dyz.recordservice.sal.bo.RecordQueryBo;
import com.dyz.recordservice.sal.service.RecordService;

@RestController
@RequestMapping(value = "records")
public class RecordController {

	@Autowired
	private RecordService recordService;

	@RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	public ResponseEntity<Result> queryRecord(
			@RequestParam(required = false) String title,
			@RequestParam(required = false) Integer userId, 
			@RequestParam(required = false) String fromDate,
			@RequestParam(required = false) String toDate) {
		RecordQueryBo queryBo = RecordModelTranslator.toBo(title, userId, fromDate, toDate);
		List<RecordInfoVo> result = RecordModelTranslator.toVoList(recordService.queryRecordInfo(queryBo));
		return ResponseEntity.status(HttpStatus.OK).body(Result.builder().content(result).build());
	}

	@RequestMapping(value = "", method = RequestMethod.POST,
			produces = { "application/json","application/xml" },
			consumes = { "multipart/form-data" })
	public ResponseEntity<Result> createRecord(
			@RequestParam MultipartFile[] pictures,
			@RequestParam(required = false) String title, 
			@RequestParam(required = false) String content,
			@RequestHeader Integer userId) {
		Integer id = recordService.createRecord(pictures, RecordModelTranslator.toBo(title, content), userId);
		return ResponseEntity.status(HttpStatus.OK)
				.body(Result.builder().content(new CommonPostResponse<Integer>(id)).build());
	}
	
	@RequestMapping(value = "{recordId}", method = RequestMethod.DELETE,
			produces = { "application/json","application/xml" })
	public ResponseEntity<Result> deleteRecord(@PathVariable Integer recordId, @RequestHeader Integer userId) {
		recordService.deleteRecord(recordId, userId);
		return ResponseEntity.status(HttpStatus.OK).body(Result.builder().build());
	}
}
