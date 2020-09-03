package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Data
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-09-02T08:13:50.931Z")




public class Data   {
  @JsonProperty("token")
  private String token = null;

  @JsonProperty("sing")
  private String sing = null;

  @JsonProperty("goodsIdList")
  @Valid
  private List<String> goodsIdList = new ArrayList<String>();

  public Data token(String token) {
    this.token = token;
    return this;
  }

  /**
   * token
   * @return token
  **/
  @ApiModelProperty(required = true, value = "token")
  @NotNull


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Data sing(String sing) {
    this.sing = sing;
    return this;
  }

  /**
   * 签名
   * @return sing
  **/
  @ApiModelProperty(required = true, value = "签名")
  @NotNull


  public String getSing() {
    return sing;
  }

  public void setSing(String sing) {
    this.sing = sing;
  }

  public Data goodsIdList(List<String> goodsIdList) {
    this.goodsIdList = goodsIdList;
    return this;
  }

  public Data addGoodsIdListItem(String goodsIdListItem) {
    this.goodsIdList.add(goodsIdListItem);
    return this;
  }

  /**
   * 
   * @return goodsIdList
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getGoodsIdList() {
    return goodsIdList;
  }

  public void setGoodsIdList(List<String> goodsIdList) {
    this.goodsIdList = goodsIdList;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Data data = (Data) o;
    return Objects.equals(this.token, data.token) &&
        Objects.equals(this.sing, data.sing) &&
        Objects.equals(this.goodsIdList, data.goodsIdList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, sing, goodsIdList);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Data {\n");
    
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    sing: ").append(toIndentedString(sing)).append("\n");
    sb.append("    goodsIdList: ").append(toIndentedString(goodsIdList)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

