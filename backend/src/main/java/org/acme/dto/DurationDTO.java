package org.acme.dto;

public class DurationDTO {
  private int total;
  private int walking;

  public DurationDTO(){}

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getWalking() {
    return walking;
  }

  public void setWalking(int walking) {
    this.walking = walking;
  }

  @Override
  public String toString() {
    return "DurationDTO : total = " + total + ", walking = " + walking;
  }
}
