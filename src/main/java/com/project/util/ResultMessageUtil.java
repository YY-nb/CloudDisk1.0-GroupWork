package com.project.util;

import com.project.vo.ResultVo;

public class ResultMessageUtil {
    public static void setErrorByException(Exception e, ResultVo result){
        result.setMessage("failed");
        result.setError(e.getMessage());
    }
    public static void setSuccess(ResultVo result){
        result.setMessage("success");
        result.setError("");
    }
    public static void setSuccessByString(String message,ResultVo result){
        result.setMessage(message);
        result.setError("");
    }
    public static void setErrorByString(String error,ResultVo result){
        result.setMessage("failed");
        result.setError(error);
    }
}
