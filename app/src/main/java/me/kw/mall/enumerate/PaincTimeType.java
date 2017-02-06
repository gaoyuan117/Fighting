
package me.kw.mall.enumerate;

public enum PaincTimeType {
  ALL("all"), CURRDAY("currday"), AFTER("after");

  private String value;

  PaincTimeType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
