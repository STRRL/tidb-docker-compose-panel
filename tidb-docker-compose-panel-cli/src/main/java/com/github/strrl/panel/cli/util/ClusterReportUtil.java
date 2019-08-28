package com.github.strrl.panel.cli.util;

import com.github.strrl.panel.core.model.Cluster;
import com.github.strrl.panel.core.model.Tidb;

/**
 * @author strrl
 * @date 2019/8/28 15:54
 */
public class ClusterReportUtil {
  private ClusterReportUtil() {}

  @SuppressWarnings("squid:S3457")
  public static String report(Cluster cluster) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Cluster name: %s\n", cluster.getName()));
    sb.append(String.format("%d TiDB instance:\n", cluster.getTidbs().size()));
    for (Tidb tidb : cluster.getTidbs()) {
      sb.append(String.format("\tTiDB: %s", tidb.getName()));
      if (tidb.getExposed()) {
        sb.append(
            String.format(
                "\tserver port: %d\tstatus port: %d",
                tidb.getExposeServerPort(), tidb.getExposeStatusPort()));
      } else {
        sb.append("\tNo exposed port");
      }
      sb.append("\n");
    }
    sb.append(String.format("%d TiKV instance\n", cluster.getTikvs().size()));
    sb.append(String.format("%d PD instance\n", cluster.getPds().size()));
    sb.append(String.format("Prometheus exposed on %d\n", cluster.getPrometheus().getExposePort()));
    sb.append(String.format("Grafana exposed on %d\n", cluster.getGrafana().getExposePort()));
    return sb.toString();
  }
}
