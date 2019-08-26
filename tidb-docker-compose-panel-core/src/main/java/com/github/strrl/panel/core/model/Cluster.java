package com.github.strrl.panel.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author strrl
 * @date 2019/8/24 21:07
 */
@Data
@AllArgsConstructor
@SuppressWarnings("squid:S1948")
public class Cluster implements Serializable {
  private static final long serialVersionUID = 9148157850274112349L;
  @Nonnull private String name;
  @Nonnull private List<Pd> pds;
  @Nonnull private List<Tidb> tidbs;
  @Nonnull private List<Tikv> tikvs;

  public Cluster() {
    this.name = "";
    this.pds = Collections.emptyList();
    this.tidbs = Collections.emptyList();
    this.tikvs = Collections.emptyList();
  }

  public Cluster(@Nonnull String name) {
    this.name = name;
    this.pds = Collections.emptyList();
    this.tidbs = Collections.emptyList();
    this.tikvs = Collections.emptyList();
  }
}
