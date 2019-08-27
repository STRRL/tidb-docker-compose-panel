package com.github.strrl.panel.core;

import com.github.strrl.panel.core.model.Cluster;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Cluster manager can start up a new cluster or get clusters list.
 *
 * @author strrl
 * @date 2019/8/24 21:40
 */
public interface ClusterManager {

  /**
   * Create and startup new cluster.
   *
   * @param cluster Cluster description.
   * @return
   */
  @Nonnull
  Cluster startup(@Nonnull Cluster cluster);

  /**
   * Get all clusters created with this ClusterManager.
   *
   * @return
   */
  @Nonnull
  List<Cluster> getAllClusters();

  /**
   * Kill a cluster and delete config file;
   *
   * @param cluster
   * @return
   */
  @Nonnull
  Cluster purge(@Nonnull Cluster cluster);

  /**
   * Kill a cluster and delete config file by clusterName;
   *
   * @param clusterName
   * @return
   */
  @Nonnull
  Cluster purgeByName(@Nonnull String clusterName);
}
