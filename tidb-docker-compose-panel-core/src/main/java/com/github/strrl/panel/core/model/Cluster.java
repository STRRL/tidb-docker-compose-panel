package com.github.strrl.panel.core.model;

import lombok.Data;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author strrl
 * @date 2019/8/24 21:07
 */
@Data
public class Cluster {
  @Nonnull private String name;
  @Nonnull private List<Pd> pds;
  @Nonnull private List<Tidb> tidbs;
  @Nonnull private List<Tikv> tikvs;
}
