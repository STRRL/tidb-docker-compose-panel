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
public class Grafana implements Serializable {
  private static final long serialVersionUID = -8921484546375307388L;
  @Nonnull public Integer exposePort;

  public Grafana() {
    this.exposePort = 0;
  }
}
