package hello.beanValidation.customTwoFieldsValidation;

import java.util.Date;

@ValidDateInterval
public class Operation implements DateIntervalBean {

    private Date startTime;

    private Date finishTime;

    public Operation(Date startTime, Date finishTime) {
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
