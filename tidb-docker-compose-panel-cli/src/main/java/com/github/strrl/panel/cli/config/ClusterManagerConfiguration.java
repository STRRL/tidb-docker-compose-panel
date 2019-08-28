package com.github.strrl.panel.cli.config;

import com.github.strrl.panel.core.ClusterManager;
import com.github.strrl.panel.core.ClusterManagerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

/**
 * @author strrl
 * @date 2019/8/28 12:55
 */
@Configuration
public class ClusterManagerConfiguration {

  private static final String WORKSPACE = "workspace";

  @Bean
  public ClusterManager clusterManager() {
    return new ClusterManagerImpl(Paths.get(WORKSPACE));
  }
}
