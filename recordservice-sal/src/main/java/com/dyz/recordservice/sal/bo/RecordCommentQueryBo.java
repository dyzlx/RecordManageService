package com.dyz.recordservice.sal.bo;

import com.dyz.recordservice.common.constant.ServiceConstant;
import com.dyz.recordservice.common.execption.IllegalParamException;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

@Data
@Builder
public class RecordCommentQueryBo {

    private Integer recordId;

    private Integer commentId;

    private Integer publisherId;

    private Date fromTime;

    private Date toTime;

    public Date getFromTime() {
        if (Objects.isNull(fromTime)) {
            try {
                return DateUtils.parseDate(ServiceConstant.DEFAULT_FROM_DATE, ServiceConstant.DATE_FORMAT_SHORT);
            } catch (ParseException e) {
                throw new IllegalParamException(0, "Illega param fromTime");
            }
        }
        return fromTime;
    }

    public Date getToTime() {
        if (Objects.isNull(toTime)) {
            try {
                return DateUtils.parseDate(ServiceConstant.DEFAULT_TO_DATE, ServiceConstant.DATE_FORMAT_SHORT);
            } catch (ParseException e) {
                throw new IllegalParamException(0, "Illega param toTime");
            }
        }
        return toTime;
    }
}
