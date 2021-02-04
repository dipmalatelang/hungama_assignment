package com.example.movie.model;

public class ResourceModel<T> {
    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    public final Status status;
    public final String message;

    public final T data;

    public ResourceModel(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ResourceModel<T> success(T data) {
        return new ResourceModel<>(Status.SUCCESS, data, null);
    }

    public static <T> ResourceModel<T> error(String msg, T data) {
        return new ResourceModel<>(Status.ERROR, data, msg);
    }

    public static <T> ResourceModel<T> loading(T data) {
        return new ResourceModel<>(Status.LOADING, data, null);
    }
}