package org.acme.dto;

public class LinkDTO {
  private String href;
  private String type;
  private String rel;

  public LinkDTO(){}

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRel() {
    return rel;
  }

  public void setRel(String rel) {
    this.rel = rel;
  }

  @Override
  public String toString() {
    return "LinkDTO : href = " + href + ", type = " + type + ", rel = " + rel ;
  }
}
