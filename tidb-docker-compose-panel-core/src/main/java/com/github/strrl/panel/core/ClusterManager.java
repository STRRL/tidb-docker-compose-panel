package com.github.strrl.panel.core;

import com.github.strrl.panel.core.model.Cluster;

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
  Cluster startup(Cluster cluster);

  /**
   * Get all clusters created with this ClusterManager.
   *
   * @return
   */
  List<Cluster> getAllClusters();
}
