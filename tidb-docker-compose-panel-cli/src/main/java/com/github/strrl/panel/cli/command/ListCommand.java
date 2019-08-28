package com.github.strrl.panel.cli.command;

import com.github.strrl.panel.cli.util.ClusterReportUtil;
import com.github.strrl.panel.core.ClusterManager;
import com.github.strrl.panel.core.model.Cluster;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

/**
 * @author strrl
 * @date 2019/8/28 12:43
 */
@Slf4j
@ShellComponent
@AllArgsConstructor
public class ListCommand {
  private final ClusterManager clusterManager;

  @ShellMethod(
      key = {"ls", "list"},
      value = "Get all cluster from workspace.",
      group = "TiDB Cluster Commands")
  public String list() {
    StringBuilder sb = new StringBuilder();
    List<Cluster> clusters = this.clusterManager.getAllClusters();
    sb.append(String.format("Here are %s cluster(s) detected from workspace.%n", clusters.size()));
    for (Cluster cluster : clusters) {
      sb.append(ClusterReportUtil.report(cluster));
      sb.append("\n");
    }
    return sb.toString();
  }
}
