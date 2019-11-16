package com.junction.vk.domain.response;

public class ApiResponseId extends ApiResponse {
    private final long listId;

    public ApiResponseId(ApiStatus status, long listId) {
        super(status);
        this.listId = listId;
    }

    public long getListId() {
        return listId;
    }

    public static ApiResponseId of(ApiStatus status, long listId) {
        return new ApiResponseId(status, listId);
    }

    @Override
    public String toString() {
        return "ApiResponseId{" +
                "listId=" + listId +
                '}';
    }
}
