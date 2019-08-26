package com.github.strrl.panel.core.render;

import com.github.strrl.panel.core.model.Cluster;
import com.google.common.io.CharStreams;
import com.hubspot.jinjava.Jinjava;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author strrl
 * @date 2019/8/26 12:16
 */
public class ClusterDockerComposeRenderer implements Renderer {
  private static final String DOCKER_COMPOSE_YML_JINJA = "docker-compose.yml.jinja";
  private final Cluster cluster;
  private final Jinjava jinjava;

  public ClusterDockerComposeRenderer(Cluster cluster) {
    this.cluster = cluster;
    this.jinjava = new Jinjava();
  }

  @Nonnull
  @Override
  public String render() {
    return this.jinjava.render(this.readTemplate(), this.getParameters());
  }

  @Nonnull
  private Map<String, Object> getParameters() {
    Map<String, Object> result = new HashMap<>();
    result.put("pds", this.cluster.getPds());
    result.put("tidbs", this.cluster.getTidbs());
    result.put("tikvs", this.cluster.getTikvs());
    return result;
  }

  @Nonnull
  private String readTemplate() {
    try {
      return CharStreams.toString(
          new InputStreamReader(
              Objects.requireNonNull(
                  this.getClass().getClassLoader().getResourceAsStream(DOCKER_COMPOSE_YML_JINJA),
                  String.format("Can not find %s.", DOCKER_COMPOSE_YML_JINJA))));
    } catch (IOException e) {
      throw new IllegalStateException(String.format("Can not read %s.", DOCKER_COMPOSE_YML_JINJA));
    }
  }
}
