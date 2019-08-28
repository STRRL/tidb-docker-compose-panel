package com.github.strrl.panel.cli;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author strrl
 * @date 2019/8/28 12:56
 */
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
