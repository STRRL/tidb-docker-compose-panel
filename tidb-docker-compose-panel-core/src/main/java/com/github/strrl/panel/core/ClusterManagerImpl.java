package com.github.strrl.panel.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.strrl.panel.core.cli.DockerCompose;
import com.github.strrl.panel.core.model.Cluster;
import com.github.strrl.panel.core.render.ClusterDockerComposeRenderer;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author strrl
 * @date 2019/8/26 12:59
 */
@Slf4j
public class ClusterManagerImpl implements ClusterManager {
  public static final String CLUSTER_JSON = "cluster.json";
  private static final String CONFIG_FILE_LIST = "config.lst";
  private static final String DOCKER_COMPOSE_YML = "docker-compose.yml";
  @Nonnull private final DockerCompose dockerCompose;
  @Nonnull private final Path workspace;
  private final ObjectMapper objectMapper;

  public ClusterManagerImpl(@Nonnull Path workspace) {
    this.dockerCompose = DockerCompose.getInstance();
    this.workspace = workspace;
    this.objectMapper = new ObjectMapper();
    this.createDirectoryIfNotExist(this.workspace);
  }

  @Nonnull
  @Override
  public Cluster startup(@Nonnull Cluster cluster) {
    if (this.getClusterDirectory(cluster).toFile().exists()) {
      log.info("A cluster with name {} already startup, it will be override.", cluster.getName());
      try {
        Files.deleteIfExists(
            Paths.get(this.getClusterDirectory(cluster).toString(), "docker-compose.yml"));
      } catch (IOException e) {
        throw new IllegalStateException("Can not remove old docker-compose.yml");
      }
    }
    this.createDirectoryIfNotExist(this.getClusterDirectory(cluster));
    this.copyConfigFiles(cluster);
    this.dockerCompose
        .up(this.writeDockerComposeYamlContext(cluster))
        .subscribe(lineOfLog -> log.info("{}", lineOfLog));
    this.saveCluster(cluster);
    return cluster;
  }

  @Nonnull
  @Override
  public List<Cluster> getAllClusters() {
    // TODO: 2019/8/26 Implement me!
    throw new UnsupportedOperationException(
        "Not implemented method: ClusterManagerImpl#getAllClusters");
  }

  private Path getClusterDirectory(@Nonnull Cluster cluster) {
    return Paths.get(this.workspace.toString(), cluster.getName());
  }

  private void copyConfigFiles(@Nonnull Cluster cluster) {
    final List<String> lines =
        this.readLines(
            Objects.requireNonNull(
                this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_LIST),
                "Can not find config.lst in resource."));
    int count = 0;
    for (String line : lines) {
      final InputStream resource = this.getClass().getClassLoader().getResourceAsStream(line);
      if (resource == null) {
        log.info("Can not find {} in resource, skip.", line);
      } else {
        count += 1;
        this.copyInputStreamToFile(
            resource, Paths.get(this.getClusterDirectory(cluster).toString(), line));
      }
    }
    log.info("{} of {} files copied.", count, lines.size());
  }

  @Nonnull
  private Path writeDockerComposeYamlContext(@Nonnull Cluster cluster) {
    final Path result = Paths.get(this.getClusterDirectory(cluster).toString(), DOCKER_COMPOSE_YML);
    this.copyInputStreamToFile(
        new ByteArrayInputStream(
            new ClusterDockerComposeRenderer(cluster).render().getBytes(StandardCharsets.UTF_8)),
        result);
    return result;
  }

  private void createDirectoryIfNotExist(@Nonnull Path path) {
    if (!path.toFile().exists()) {
      try {
        Files.createDirectories(path);
      } catch (IOException e) {
        throw new IllegalStateException(String.format("Can not create directory: %s", path), e);
      }
    }
  }

  private void createFileIfNotExist(@Nonnull Path path) {
    if (!path.toFile().exists()) {
      try {
        Files.createFile(path);
      } catch (IOException e) {
        throw new IllegalStateException(String.format("Can not create file: %s.", path), e);
      }
    }
  }

  private void saveCluster(@Nonnull Cluster cluster) {
    try {
      String context = this.objectMapper.writeValueAsString(cluster);
      com.google.common.io.Files.asCharSink(
              Paths.get(this.getClusterDirectory(cluster).toString(), CLUSTER_JSON).toFile(),
              StandardCharsets.UTF_8)
          .write(context);
      this.getClusterDirectory(cluster);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Can not serialize cluster.");
    } catch (IOException e) {
      throw new IllegalStateException("Can not write to cluster.json");
    }
  }

  @Nonnull
  private Optional<Cluster> readCluster(@Nonnull String clusterName) {
    Path clusterJsonPath = Paths.get(this.workspace.toString(), clusterName, CLUSTER_JSON);
    if (clusterJsonPath.toFile().exists() && clusterJsonPath.toFile().isFile()) {
      try {
        return Optional.of(
            this.objectMapper.readValue(
                com.google.common.io.Files.asCharSource(
                        clusterJsonPath.toFile(), StandardCharsets.UTF_8)
                    .read(),
                Cluster.class));
      } catch (IOException e) {
        log.warn("Can not read {}, skip.", clusterJsonPath, e);
        return Optional.empty();
      }
    } else {
      log.warn("{} not exist, skip.", clusterJsonPath);
      return Optional.empty();
    }
  }

  @Nonnull
  private List<String> readLines(@Nonnull InputStream inputStream) {
    final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    try {
      return CharStreams.readLines(reader);
    } catch (IOException e) {
      throw new IllegalStateException("Can not read file.", e);
    }
  }

  private void copyInputStreamToFile(@Nonnull InputStream in, @Nonnull Path target) {
    try {
      com.google.common.io.Files.createParentDirs(target.toFile());
    } catch (IOException e) {
      throw new IllegalStateException("Can not create parent directories.");
    }
    this.createFileIfNotExist(target);
    try (final OutputStream out = new FileOutputStream(target.toFile())) {;
      ByteStreams.copy(in, out);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("Can not find file.", e);
    } catch (IOException e) {
      throw new IllegalStateException("Can not write file.", e);
    }
  }
}
