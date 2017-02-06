
package me.kw.mall.enumerate;

public enum CourseType {
  DOC("doc"), VIDEO("video");

  private String value;

  CourseType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
