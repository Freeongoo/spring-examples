package hello.controller;

public final class Route {

    private Route() {} // cannot create instance :)

    public static final String EMPLOYEE_ROUTE = "/employees";

    public static final String POST_ROUTE = "/posts";

    public static final String COMMENT_ROUTE = "/posts/{postId}/comments";
}
