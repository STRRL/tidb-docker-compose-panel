package com.github.strrl.panel.cli.command;

import com.github.strrl.panel.core.util.ProcessReactiveUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.ExitRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * @author strrl
 * @date 2019/8/28 13:28
 */
@Slf4j
@ShellComponent
@AllArgsConstructor
public class ExitCommand {
  @ShellMethod(
      value = "Exit the shell.",
      key = {"quit", "exit"},
      group = "Built-In Commands")
  public void quit() {
    ProcessReactiveUtil.cleanup();
    throw new ExitRequest();
  }
}
