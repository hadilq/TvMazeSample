package com.github.tvmazesample.viewmodel.response;

public class Response<T> {
    private T data;
    private Throwable error;
    private Status status;

    public Response(T data) {
        this.data = data;
        status = Status.SUCCESS;
    }

    public Response(Throwable error) {
        this.error = error;
        this.status = Status.ERROR;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    public Status getStatus() {
        return status;
    }
}
