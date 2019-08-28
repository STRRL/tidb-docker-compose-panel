package com.github.strrl.panel.cli.command;

import com.github.strrl.panel.cli.util.ClusterReportUtil;
import com.github.strrl.panel.core.ClusterManager;
import com.github.strrl.panel.core.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author strrl
 * @date 2019/8/28 12:43
 */
@Slf4j
@ShellComponent
@AllArgsConstructor
public class StartupCommand {

  private static final int NUMBER_OF_PD = 3;
  private final ClusterManager clusterManager;

  @ShellMethod(value = "Startup a cluster.", group = "TiDB Cluster Commands")
  @SuppressWarnings("squid:S106")
  public void startup(
      @ShellOption(
              value = {"-n", "--cluster-name"},
              help = "Cluster name.")
          String clusterName,
      @ShellOption(value = "--tidbs", defaultValue = "1", help = "Number of TiDB instance.")
          Integer nTidb,
      @ShellOption(value = "--tikvs", defaultValue = "3", help = "Number of TiKV instance.")
          Integer nTikv,
      @ShellOption(defaultValue = "4000", help = "TiDB server port") Integer tidbServerPort,
      @ShellOption(defaultValue = "10080", help = "TiDB status port") Integer tidbStatusPort,
      @ShellOption(defaultValue = "9090", help = "Prometheus port") Integer prometheusPort,
      @ShellOption(defaultValue = "3000", help = "Grafana port") Integer grafanaPort,
      @ShellOption(defaultValue = "latest", help = "Version for docker image pingcap/pd.")
          String pdVersion,
      @ShellOption(defaultValue = "latest", help = "Version for docker image pingcap/tidb.")
          String tidbVersion,
      @ShellOption(defaultValue = "latest", help = "Version for docker image pingcap/tikv.")
          String tikvVersion) {
    Cluster cluster =
        this.createCluster(
            clusterName,
            nTidb,
            nTikv,
            tidbServerPort,
            tidbStatusPort,
            prometheusPort,
            grafanaPort,
            pdVersion,
            tidbVersion,
            tikvVersion);
    System.out.println(this.startupReport(cluster));
    this.clusterManager.startup(cluster);
  }

  @Nonnull
  private Cluster createCluster(
      @Nonnull String clusterName,
      @Nonnull Integer nTidb,
      @Nonnull Integer nTikv,
      @Nonnull Integer tidbServerPort,
      @Nonnull Integer tidbStatusPort,
      @Nonnull Integer prometheusPort,
      @Nonnull Integer grafanaPort,
      @Nonnull String pdVersion,
      @Nonnull String tidbVersion,
      @Nonnull String tikvVersion) {
    Cluster cluster = new Cluster(clusterName);
    cluster.setTidbs(this.createTidbs(nTidb, tidbVersion, tidbServerPort, tidbStatusPort));
    cluster.setTikvs(this.createTikvs(nTikv, tikvVersion));
    cluster.setPds(this.createPds(pdVersion));
    cluster.setPrometheus(new Prometheus(prometheusPort));
    cluster.setGrafana(new Grafana(grafanaPort));
    return cluster;
  }

  @Nonnull
  private List<Tidb> createTidbs(
      @Nonnull Integer nTidb,
      @Nonnull String tidbVersion,
      @Nonnull Integer tidbServerPort,
      @Nonnull Integer tidbStatusPort) {
    List<Tidb> result = new ArrayList<>();
    for (int i = 0; i < nTidb; i++) {
      result.add(
          new Tidb(
              String.format("tidb%d", i), tidbVersion, i == 0, tidbServerPort, tidbStatusPort));
    }
    return result;
  }

  @Nonnull
  private List<Tikv> createTikvs(@Nonnull Integer nTikv, @Nonnull String tikvVersion) {
    List<Tikv> result = new ArrayList<>();
    for (int i = 0; i < nTikv; i++) {
      result.add(new Tikv(String.format("tikv%d", i), tikvVersion));
    }
    return result;
  }

  @Nonnull
  private List<Pd> createPds(@Nonnull String pdVersion) {
    List<Pd> result = new ArrayList<>();
    for (int i = 0; i < NUMBER_OF_PD; i++) {
      result.add(new Pd(String.format("pd%d", i), pdVersion));
    }
    return result;
  }

  @SuppressWarnings("squid:S3457")
  private String startupReport(Cluster cluster) {
    return String.format("Here is your configuration:\n%s", ClusterReportUtil.report(cluster));
  }
}
