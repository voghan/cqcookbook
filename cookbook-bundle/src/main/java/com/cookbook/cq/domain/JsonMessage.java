package com.cookbook.cq.domain;

import java.util.Map;

/**
 * This object is used to model the request back to the client.
 * 
 * Generics is used to handle the data packet.
 */
public class JsonMessage<T> extends Base {
	private static final long serialVersionUID = 8606380281319193905L;
	private int status;
    private String message;
    private Map<?, ?> paramaters;
    private T data;

    public JsonMessage(int status, Map<?, ?> params) {
        this.status = status;
        this.paramaters = params;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<?, ?> getParamaters() {
        return paramaters;
    }

    public void setParamaters(Map<?, ?> paramaters) {
        this.paramaters = paramaters;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
