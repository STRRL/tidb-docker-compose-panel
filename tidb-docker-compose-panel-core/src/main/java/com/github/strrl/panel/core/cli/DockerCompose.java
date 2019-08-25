package com.github.strrl.panel.core.cli;

import com.github.strrl.panel.core.util.ProcessReactiveUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * Cli adaptor for docker-compose.
 *
 * @author strrl
 * @date 2019/8/24 21:07
 */
@Slf4j
public class DockerCompose {
  private static DockerCompose INSTANCE;

  private DockerCompose() {}

  public static DockerCompose getInstance() {
    if (INSTANCE == null) {
      synchronized (DockerCompose.class) {
        ProcessBuilder processBuilder = new ProcessBuilder("docker-compose");
        Process process;
        try {
          process = processBuilder.start();
        } catch (IOException e) {
          throw new IllegalStateException("Please install docker and docker-compose first.");
        }
        try {
          boolean finished = process.waitFor(5, TimeUnit.SECONDS);
          if (finished) {
            INSTANCE = new DockerCompose();
          } else {
            process.destroy();
            throw new IllegalStateException("Can not execute 'docker-compose --version'.");
          }
        } catch (InterruptedException e) {
          log.warn("Thread interrupted.", e);
          Thread.currentThread().interrupt();
          throw new IllegalStateException("Thread interrupted.");
        }
      }
    }
    return INSTANCE;
  }

  /**
   * Call {@code docker-compose version}
   *
   * @return
   */
  public Mono<String> version() {
    return this.executeCommand("docker-compose", "version");
  }

  /**
   * Call {@code docker-compose up -d -f docker-compose.yml}. Return output with reactive style.
   *
   * @param composeFile
   * @return
   */
  public Flux<String> up(Path composeFile) {
    return this.executeCommandReactive(
        "docker-compose", "up", "-d", "-f", this.composeFilePathToString(composeFile));
  }

  /**
   * Call {@code docker-compose ps -f docker-compose.yml}
   *
   * @param composeFile
   * @return
   */
  public Mono<String> ps(Path composeFile) {
    return this.executeCommand(
        "docker-compose", "ps", "-f", this.composeFilePathToString(composeFile));
  }

  /**
   * Call {@code docker-compose config -f docker-compose.yml}
   *
   * @param composeFile
   * @return
   */
  public Mono<String> config(Path composeFile) {
    return this.executeCommand(
        "docker-compose", "config", "-f", this.composeFilePathToString(composeFile));
  }

  private String composeFilePathToString(Path composeFile) {
    return composeFile.toAbsolutePath().toString();
  }

  private Mono<String> executeCommand(String... commands) {
    return ProcessReactiveUtil.getOutput(ProcessReactiveUtil.run(commands));
  }

  private Flux<String> executeCommandReactive(String... commands) {
    return ProcessReactiveUtil.getOutputReactive(ProcessReactiveUtil.run(commands));
  }
}
