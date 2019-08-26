package com.github.strrl.panel.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author strrl
 * @date 2019/8/24 21:07
 */
@Data
@AllArgsConstructor
public class Cluster {
  @Nonnull private String name;
  @Nonnull private List<Pd> pds;
  @Nonnull private List<Tidb> tidbs;
  @Nonnull private List<Tikv> tikvs;

  public Cluster(@Nonnull String name) {
    this.name = name;
    this.pds = Collections.emptyList();
    this.tidbs = Collections.emptyList();
    this.tikvs = Collections.emptyList();
  }
}
