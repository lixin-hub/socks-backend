package com.lx;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author LIXIN
 * @description 全局响应对象
 * @date 2022/11/11 21:26
 */
@Data
public class Result<T> {
    IPage<T> page;//可能为空
    private int code=200;
    private T data;
    private Object message;
    private boolean status=true;

    public static <T> ResultBuilder<T> builder(Class<T> clazz) {
        return new ResultBuilder<>();
    }

    public static <T> ResultBuilder<T> builder() {
        return new ResultBuilder<>();
    }

    public static class ResultBuilder<T> {
        Result<T> result;

        public ResultBuilder() {
            result = new Result<>();
        }
        public ResultBuilder<T> status(boolean status){
            result.setStatus(status);
            return this;
        }
        public ResultBuilder<T> code(int code) {
            result.setCode(code);
            return this;
        }
        public ResultBuilder<T> ok() {
            result.setCode(200);
            return this;
        }

        public ResultBuilder<T> data(T data) {
            result.setData(data);
            return this;
        }

        public ResultBuilder<T> message(Object message) {
            result.setMessage(message);
            return this;
        }

        public ResultBuilder<T> page(IPage<T> page) {
            result.setPage(page);
            return this;
        }


        public Result<T> build() {
            if (result.message==null){
                result.setMessage(new Date());
            }
            return result;
        }
    }
}
