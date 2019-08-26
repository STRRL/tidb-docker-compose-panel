package com.github.strrl.panel.web;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author strrl
 * @date 2019/8/26 22:01
 */
@EnableWebFlux
@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    new SpringApplicationBuilder()
        .bannerMode(Banner.Mode.LOG)
        .sources(Application.class)
        .build()
        .run(args);
  }
}
