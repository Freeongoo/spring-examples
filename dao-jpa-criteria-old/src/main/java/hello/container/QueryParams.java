package hello.container;

import java.util.Objects;

public class QueryParams {
    private String sortBy;
    private OrderType orderType;
    private Integer limit;

    public QueryParams() {
    }

    private QueryParams(String sortBy, OrderType orderType, Integer limit) {
        this.sortBy = sortBy;
        this.orderType = orderType;
        this.limit = limit;
    }

    public static QueryParams of(String sortBy, OrderType orderType, Integer limit) {
        return new QueryParams(sortBy, orderType, limit);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryParams that = (QueryParams) o;
        return Objects.equals(sortBy, that.sortBy) &&
                orderType == that.orderType &&
                Objects.equals(limit, that.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortBy, orderType, limit);
    }
}
