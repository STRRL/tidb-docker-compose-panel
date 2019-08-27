package com.github.strrl.panel.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * @author strrl
 * @date 2019/8/24 21:23
 */
@Data
@AllArgsConstructor
public class Tidb implements Serializable {
  private static final long serialVersionUID = -8728558881095480893L;
  @Nonnull private String name;
  @Nonnull private String version;
  @Nonnull private Boolean exposed;
  @Nonnull private Integer exposeServerPort;
  @Nonnull private Integer exposeStatusPort;

  public Tidb() {
    this.name = "";
    this.version = "";
    this.exposed = false;
    this.exposeServerPort = 0;
    this.exposeStatusPort = 0;
  }
}
