package dev.jartificer.liquorstore.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDto<T> {

  private Integer page;
  private Integer pageSize;
  private List<T> data;
}
