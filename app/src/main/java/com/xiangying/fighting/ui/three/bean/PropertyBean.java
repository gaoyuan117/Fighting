package com.xiangying.fighting.ui.three.bean;

/**
 * Created by xiaoniao on 2016/9/13.
 */
public class PropertyBean {
  private int code;
  private String message;
  private DataBean data;

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

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public static class DataBean {
    private String amount;
    private String shopAmount;
    private String count;
    private String bankCount;

    public String getAmount() {
      return amount;
    }

    public void setAmount(String amount) {
      this.amount = amount;
    }

    public String getShopAmount() {
      return shopAmount;
    }

    public void setShopAmount(String shopAmount) {
      this.shopAmount = shopAmount;
    }

    public String getCount() {
      return count;
    }

    public void setCount(String count) {
      this.count = count;
    }

    public String getBankCount() {
      return bankCount;
    }

    public void setBankCount(String bankCount) {
      this.bankCount = bankCount;
    }
  }
}
