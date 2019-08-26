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
public class Pd implements Serializable {
  private static final long serialVersionUID = -895671942340391989L;
  @Nonnull private String name;
  @Nonnull private String version;

  public Pd() {
    this.name = "";
    this.version = "";
  }
}
