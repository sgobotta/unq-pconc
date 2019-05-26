package com.unq.handlers;

import com.unq.exeptions.PoisonException;

public class PoisonExceptionHandler implements Thread.UncaughtExceptionHandler {

  private long startTime;

  public PoisonExceptionHandler(long startTime) {
    this.startTime = startTime;
  }

  public void uncaughtException(Thread th, Throwable ex) {
    long endTime   = System.nanoTime();
    long totalTime = (endTime - this.startTime) / 1000000;

    if (th.isAlive() && ex instanceof PoisonException) {
      th.interrupt();
    }
    this.printStatus(th, totalTime);
  }

  private void printStatus(Thread th, long totalTime) {
    String currentThread = " Thread " + th.getId() + "\n";
    String isAlive       = " Alive: " + th.isAlive() + "\n";
    String isInterrupted = " Interrupted: " + th.isInterrupted() + "\n";
    String elapsedTime   = " Elapsed Time: " + totalTime + "\n";

    System.out.println("Status :::" + currentThread + isAlive + isInterrupted + elapsedTime);
  }
}
