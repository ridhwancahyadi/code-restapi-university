package org.test.dto;

public class ApiResponseWithData<T> {
    private boolean status;
    private String message;
    private T data;

    public ApiResponseWithData() {
    }

    public ApiResponseWithData(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean setStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

}
