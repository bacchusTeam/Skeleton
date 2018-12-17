package sample.mybatis.domain;

import lombok.Data;

@Data
public class Greeting {

  private long id;
  private String content;
  private City city;
  private Hotel hotel;
}
