package com.lx.common.base;

public class Global {
    public static Object TRUE=true;
    public static Object FALSE=false;
    public static Result<Object> OK=Result.builder().code(200).message(Global.TRUE).build();
    public static Result<Object> FAIL =Result.builder().code(500).message(Global.FALSE).build();
}
