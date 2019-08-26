package com.github.strrl.panel.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.annotation.Nonnull;

/**
 * @author strrl
 * @date 2019/8/24 21:23
 */
@Data
@AllArgsConstructor
public class Tikv {
  @Nonnull private String name;
  @Nonnull private String version;
}
