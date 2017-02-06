
package service.api;

import com.google.gson.annotations.SerializedName;

public class ArticlesPageResult extends BaseEntity {

  @SerializedName("data")
  public ArticleItem[] Data;

}
