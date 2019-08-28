package com.github.strrl.panel.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
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

  @Bean
  RouterFunction<ServerResponse> staticResourceRouter() {
    return RouterFunctions.resources("/**", new ClassPathResource("static/"));
  }

  @Bean
  public RouterFunction<ServerResponse> indexRouter(
      @Value("classpath:/static/index.html") final Resource indexHtml) {
    return route(GET("/"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(indexHtml));
  }
}
