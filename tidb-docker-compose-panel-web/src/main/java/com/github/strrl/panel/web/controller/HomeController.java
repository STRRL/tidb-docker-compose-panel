package com.github.strrl.panel.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author strrl
 * @date 2019/8/26 22:08
 */
@RestController
@RequestMapping("/home")
public class HomeController {
  @GetMapping({"", "/"})
  public Mono<String> welcome() {
    return Mono.just("Welcome");
  }
}
