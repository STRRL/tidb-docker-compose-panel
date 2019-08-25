package com.github.strrl.panel.core.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.*;

/**
 * Convert process stdout/stderr InputStream to a reactive style.
 *
 * <p>Using a thread pool reading these input stream with blocking IO.
 *
 * @author strrl
 * @date 2019/8/24 22:39
 */
@Slf4j
public class ProcessReactiveOutputUtil {

  private static final ExecutorService RELAY_EXECUTOR =
      new ThreadPoolExecutor(
          2,
          10,
          1,
          TimeUnit.MINUTES,
          new LinkedBlockingQueue<>(),
          new ThreadFactoryBuilder()
              .setUncaughtExceptionHandler(
                  (t, e) -> log.warn("Thread {} throw uncaught exception.", t, e))
              .setNameFormat("relay-executor-%d")
              .build());

  /**
   * Give all output once a time when process stopped.
   *
   * @param process
   * @return
   */
  public static Mono<String> getOutput(Process process) {
    return Mono.fromFuture(
        CompletableFuture.supplyAsync(
            () -> {
              InputStream stdout = process.getInputStream();
              BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
              StringBuilder stringBuilder = new StringBuilder();
              String line;
              try {
                while ((line = reader.readLine()) != null) {
                  stringBuilder.append(line).append(System.lineSeparator());
                }
                return stringBuilder.toString();
              } catch (IOException e) {
                log.warn("Can not read stdout.", e);
                return null;
              }
            },
            RELAY_EXECUTOR));
  }

  /**
   * Give output line-by-line.
   *
   * @param process
   * @return
   */
  public static Flux<String> getOutputReactive(Process process) {
    InputStream stdout = process.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));

    return Flux.from(
        s ->
            RELAY_EXECUTOR.submit(
                () -> {
                  while (true) {
                    try {
                      String line = reader.readLine();
                      if (line == null) {
                        s.onComplete();
                        break;
                      } else {
                        s.onNext(line);
                      }

                    } catch (IOException e) {
                      s.onError(e);
                      break;
                    }
                  }
                }));
  }
}
