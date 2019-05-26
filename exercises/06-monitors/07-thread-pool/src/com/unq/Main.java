package com.unq;

import com.unq.handlers.PoisonExceptionHandler;
import com.unq.monitors.ThreadPool;

public class Main {

  public static void main(String[] args) {

    long startTime = System.nanoTime();
    int bufferSize = 500;
    int tasks = 100000;
    int workers = 8;
    PoisonExceptionHandler exceptionHandler = new PoisonExceptionHandler(startTime);

    ThreadPool threadPool = new ThreadPool(workers, bufferSize);
    threadPool.setExceptionHandler(exceptionHandler);
    threadPool.init();
    for (int i = 0; i < tasks; i++) {
      threadPool.launchDummyTask();
    }
    for (int i = 0; i < workers; i++) {
      threadPool.stop();
    }
  }
}
