package com.github.strrl.panel.core.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Convert process stdout/stderr InputStream to a reactive style.
 *
 * <p>Using a thread pool reading these input stream with blocking IO.
 *
 * @author strrl
 * @date 2019/8/24 22:39
 */
@Slf4j
public class ProcessReactiveUtil {

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
  private static final ExecutorService PROCESS_STARTUP_EXECUTOR =
      new ThreadPoolExecutor(
          2,
          10,
          1,
          TimeUnit.MINUTES,
          new LinkedBlockingQueue<>(),
          new ThreadFactoryBuilder()
              .setUncaughtExceptionHandler(
                  (t, e) -> log.warn("Thread {} throw uncaught exception.", t, e))
              .setNameFormat("process-startup-executor-%d")
              .build());

  /**
   * Give all output once a time when process stopped.
   *
   * @param processMono
   * @return
   */
  public static Mono<String> getOutput(Mono<Process> processMono) {
    return Mono.create(
        callback ->
            processMono.subscribe(
                process -> {
                  log.info("process received");
                  RELAY_EXECUTOR.submit(
                      () -> {
                        InputStream stdout = process.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        try {
                          while ((line = reader.readLine()) != null) {
                            stringBuilder.append(line).append(System.lineSeparator());
                          }
                          callback.success(stringBuilder.toString());
                        } catch (IOException e) {
                          callback.error(e);
                        }
                      });
                },
                callback::error));
  }

  /**
   * Give output line-by-line.
   *
   * @param processMono
   * @return
   */
  public static Flux<String> getOutputReactive(Mono<Process> processMono) {
    return Flux.create(
        emitter ->
            processMono.subscribe(
                process ->
                    RELAY_EXECUTOR.submit(
                        () -> {
                          InputStream stdout = process.getInputStream();
                          BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
                          while (true) {
                            try {
                              String line = reader.readLine();
                              if (line == null) {
                                emitter.complete();
                                break;
                              } else {
                                emitter.next(line);
                              }
                            } catch (IOException e) {
                              emitter.error(e);
                              break;
                            }
                          }
                        }),
                emitter::error),
        FluxSink.OverflowStrategy.BUFFER);
  }

  /**
   * Run a process in non blocking style.
   *
   * @param commands
   * @return
   */
  public static Mono<Process> run(String... commands) {
    ProcessBuilder builder = new ProcessBuilder(commands).redirectErrorStream(true);
    return Mono.create(
        callback ->
            PROCESS_STARTUP_EXECUTOR.submit(
                () -> {
                  Process process;
                  try {
                    process = builder.start();
                  } catch (IOException e) {
                    callback.error(e);
                    return;
                  }
                  callback.success(process);
                }));
  }

  public static void cleanup() {
    RELAY_EXECUTOR.shutdownNow();
    PROCESS_STARTUP_EXECUTOR.shutdownNow();
  }
}
