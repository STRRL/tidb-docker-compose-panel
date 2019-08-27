package com.github.strrl.panel.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * @author strrl
 * @date 2019/8/27 20:45
 */
@Data
@AllArgsConstructor
public class Prometheus implements Serializable {
  private static final long serialVersionUID = -4230214481941717209L;
  @Nonnull private Integer exposePort;

  public Prometheus() {
    this.exposePort = 0;
  }
}
