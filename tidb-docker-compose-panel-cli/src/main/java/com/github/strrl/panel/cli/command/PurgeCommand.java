package com.github.strrl.panel.cli.command;

import com.github.strrl.panel.cli.util.ClusterReportUtil;
import com.github.strrl.panel.core.ClusterManager;
import com.github.strrl.panel.core.model.Cluster;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * @author strrl
 * @date 2019/8/28 12:44
 */
@Slf4j
@ShellComponent
@AllArgsConstructor
public class PurgeCommand {
  private final ClusterManager clusterManager;

  @ShellMethod(value = "Purge a cluster.", group = "TiDB Cluster Commands")
  public String purge(
      @ShellOption(
              value = {"-n", "--cluster-name"},
              help = "Cluster name.")
          String clusterName) {
    Cluster cluster = this.clusterManager.purgeByName(clusterName);
    return String.format("This cluster will be purged:%n%s", ClusterReportUtil.report(cluster));
  }
}
