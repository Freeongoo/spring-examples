package hello.container;

import java.util.Objects;

public class QueryParams {
    private String sortBy;
    private OrderType orderType;
    private Integer limit;
    private Integer start;

    public QueryParams() {
    }

    private QueryParams(String sortBy, OrderType orderType, Integer limit, Integer start) {
        this.sortBy = sortBy;
        this.orderType = orderType;
        this.limit = limit;
        this.start = start;
    }

    public static QueryParams of(String sortBy, OrderType orderType, Integer limit, Integer start) {
        return new QueryParams(sortBy, orderType, limit, start);
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryParams that = (QueryParams) o;
        return Objects.equals(sortBy, that.sortBy) &&
                orderType == that.orderType &&
                Objects.equals(limit, that.limit) &&
                Objects.equals(start, that.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortBy, orderType, limit, start);
    }
}
