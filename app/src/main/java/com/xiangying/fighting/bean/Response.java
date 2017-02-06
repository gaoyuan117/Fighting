/*
 * Response     2016/12/12 10:42
 * Copyright (c) 2016 Koterwong All right reserved
 */
package com.xiangying.fighting.bean;

/**
 * Created by Koterwong on 2016/12/12 10:42
 */
public class Response<T> {
  private int code ;
  private String message;

  private T t;

  public T getT() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }

  public boolean isOk() {
    return code ==1;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
