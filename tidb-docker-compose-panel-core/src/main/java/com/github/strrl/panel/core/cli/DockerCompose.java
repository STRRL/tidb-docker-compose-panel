package com.github.strrl.panel.core.cli;

import com.github.strrl.panel.core.exception.CliException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

/**
 * Cli adaptor for docker-compose.
 *
 * @author strrl
 * @date 2019/8/24 21:07
 */
public class DockerCompose {
  private DockerCompose() {}

  public static DockerCompose getInstance() {
    throw new UnsupportedOperationException();
  }

  /**
   * Call {@code docker-compose version}
   *
   * @return
   * @throws CliException
   */
  public Mono<String> version() throws CliException {
    throw new UnsupportedOperationException();
  }

  /**
   * Call {@code docker-compose up -d -f docker-compose.yml}. Return output with reactive style.
   *
   * @param composeFile
   * @return
   * @throws CliException
   */
  public Flux<String> up(Path composeFile) throws CliException {
    throw new UnsupportedOperationException();
  }

  /**
   * Call {@code docker-compose ps -f docker-compose.yml}
   *
   * @param composeFile
   * @return
   * @throws CliException
   */
  public Mono<String> ps(Path composeFile) throws CliException {
    throw new UnsupportedOperationException();
  }

  /**
   * Call {@code docker-compose config -f docker-compose.yml}
   *
   * @param composeFile
   * @return
   * @throws CliException
   */
  public Mono<String> config(Path composeFile) throws CliException {
    throw new UnsupportedOperationException();
  }
}
