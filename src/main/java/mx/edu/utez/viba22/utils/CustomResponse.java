package mx.edu.utez.viba22.utils;

public class CustomResponse<T> {
    private T data;
    private String message;
    private boolean error;
    private int status;

    public CustomResponse(T data, String message, boolean error, int status) {
        this.data = data;
        this.message = message;
        this.error = error;
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}