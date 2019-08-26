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

  public Tidb() {
    this.name = "";
    this.name = "";
    this.exposed = false;
  }
}
