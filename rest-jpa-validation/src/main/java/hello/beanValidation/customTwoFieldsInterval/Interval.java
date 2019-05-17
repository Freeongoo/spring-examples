package hello.beanValidation.customTwoFieldsInterval;

@ValidInterval(startField = "start", endField = "finish")
public class Interval {

    private Double start;
    private Double finish;

    public Interval(Double start, Double finish) {
        this.start = start;
        this.finish = finish;
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
}
