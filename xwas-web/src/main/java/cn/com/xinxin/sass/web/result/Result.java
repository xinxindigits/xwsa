package cn.com.xinxin.sass.web.result;

/**
 * Created by dengyunhui on 2018/5/8
 **/
public class Result<T> {
    private T data;
    private String errorMsg;
    private boolean isError;

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setData(data);
        result.setError(false);
        result.setErrorMsg(null);
        return result;
    }

    public static <T> Result<T> fail(T data, String errorMsg){
        Result<T> result = new Result<>();
        result.setData(data);
        result.setError(true);
        result.setErrorMsg(errorMsg);
        return result;
    }


    public static <T> Result<T> result(T data, boolean isError, String errorMsg){
        Result<T> result = new Result<>();
        result.setData(data);
        result.setError(isError);
        result.setErrorMsg(errorMsg);
        return result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
