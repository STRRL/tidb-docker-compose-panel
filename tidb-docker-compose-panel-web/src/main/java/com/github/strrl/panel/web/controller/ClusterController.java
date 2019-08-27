package com.github.strrl.panel.web.controller;

import com.github.strrl.panel.core.ClusterManager;
import com.github.strrl.panel.core.model.Cluster;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
}
