package hello.beanValidation.customTwoFieldsInterval;

@ValidInterval(startField = "start", endField = "finish")
@ValidInterval(startField = "from", endField = "to", isMayBeEqual = false)
public class Interval {

    private Double start;
    private Double finish;

    private Integer from;
    private Integer to;

    public Interval(Double start, Double finish) {
        this.start = start;
        this.finish = finish;
    }

    public Interval(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }

    public Interval(Double start, Double finish, Integer from, Integer to) {
        this.start = start;
        this.finish = finish;
        this.from = from;
        this.to = to;
    }

    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    public Double getFinish() {
        return finish;
    }

    public void setFinish(Double finish) {
        this.finish = finish;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }
}
