package com.unq.workers;

import com.unq.monitors.Buffer;

public class Worker extends Throwable implements Runnable {

  private Buffer buffer;

  public Worker(Buffer buffer) {
    this.buffer = buffer;
  }

  public void run() {
    while (true) {
      Runnable task = (Runnable) this.buffer.read();
      task.run();
    }
  }
}
