package com.github.strrl.panel.core.exception;

/**
 * @author strrl
 * @date 2019/8/24 21:18
 */
public class CliException extends Exception {
  private static final long serialVersionUID = 7974242018481617690L;

  public CliException() {}

  public CliException(String message) {
    super(message);
  }

  public CliException(String message, Throwable cause) {
    super(message, cause);
  }

  public CliException(Throwable cause) {
    super(cause);
  }

  public CliException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
