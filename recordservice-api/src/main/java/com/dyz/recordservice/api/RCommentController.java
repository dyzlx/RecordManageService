package com.dyz.recordservice.api;

import com.dyz.recordservice.api.model.RCommentCreateVo;
import com.dyz.recordservice.api.model.RCommentInfoVo;
import com.dyz.recordservice.api.model.Result;
import com.dyz.recordservice.api.translation.RCommentModelTranslator;
import com.dyz.recordservice.sal.bo.RCommentQueryBo;
import com.dyz.recordservice.sal.service.RCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "record_comments")
public class RCommentController {

    @Autowired
    private RCommentService rCommentService;


    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<Result> queryRecordComments(
            @RequestParam(required = false) Integer recordId,
            @RequestParam(required = false) Integer publisherId,
            @RequestParam(required = false) String fromTime,
            @RequestParam(required = false) String toTime) {
        RCommentQueryBo queryBo = RCommentModelTranslator.toBo(recordId, publisherId, fromTime, toTime);
        List<RCommentInfoVo> result = RCommentModelTranslator.toVoList(rCommentService.queryRecordCommentInfo(queryBo));
        return ResponseEntity.status(HttpStatus.OK).body(Result.builder().content(result).build());
    }

    @RequestMapping(value = "", method = RequestMethod.POST,
            produces = {"application/json", "application/xml"},
            consumes = {"application/json", "application/xml"})
    public ResponseEntity<Result> createRecordComment(
            @Validated @RequestBody RCommentCreateVo createVo,
            @RequestHeader Integer userId) {
        Integer id = rCommentService.createRecordComment(RCommentModelTranslator.toBo(createVo), userId);
        return ResponseEntity.status(HttpStatus.OK).body(Result.builder().content(id).build());
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Result> deleteRecordComment(
            @RequestParam Integer recordId,
            @RequestParam Integer commentId,
            @RequestHeader Integer userId) {
        rCommentService.deleteRecordComment(recordId, commentId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(Result.builder().build());
    }

    @RequestMapping(value = "count", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<Result> queryRecordCommentCount(
            @RequestParam Integer recordId,
            @RequestHeader Integer userId) {
        Integer count = rCommentService.getRecordCommentCount(recordId, userId);
        return ResponseEntity.status(HttpStatus.OK).body(Result.builder().content(count).build());
    }
}
