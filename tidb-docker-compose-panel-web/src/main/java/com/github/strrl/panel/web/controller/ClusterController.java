package com.github.strrl.panel.web.controller;

import com.github.strrl.panel.core.ClusterManager;
import com.github.strrl.panel.core.model.Cluster;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author strrl
 * @date 2019/8/27 15:05
 */
@Slf4j
@RestController
@RequestMapping("cluster")
@AllArgsConstructor
public class ClusterController {
  private final ClusterManager clusterManager;

  @PostMapping
  public Mono<Cluster> startup(@RequestBody Cluster cluster) {
    return Mono.fromFuture(
        CompletableFuture.supplyAsync(() -> this.clusterManager.startup(cluster)));
  }

  @GetMapping
  public Mono<List<Cluster>> getAll() {
    return Mono.fromFuture(CompletableFuture.supplyAsync(this.clusterManager::getAllClusters));
  }

  @DeleteMapping
  public Mono<Cluster> purge(@RequestBody Cluster cluster) {
    return Mono.fromFuture(CompletableFuture.supplyAsync(() -> this.clusterManager.purge(cluster)));
  }

  @DeleteMapping("/{clusterName}")
  public Mono<Cluster> purge(@PathVariable("clusterName") String clusterName) {
    return Mono.fromFuture(
        CompletableFuture.supplyAsync(() -> this.clusterManager.purgeByName(clusterName)));
  }
}
