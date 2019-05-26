package com.unq.monitors;

import com.unq.handlers.PoisonExceptionHandler;
import com.unq.tasks.DummyTask;
import com.unq.tasks.PoisonPill;
import com.unq.workers.Worker;

public class ThreadPool {

  private Buffer buffer;
  private int workers;
  private PoisonExceptionHandler exceptionHandler;

  public ThreadPool(int workers, int bufferSize) {
    this.workers = workers;
    this.buffer = new Buffer(bufferSize);
  }

  public ThreadPool(int workers) {
    int bufferSize = 10;
    this.workers = workers;
    this.buffer = new Buffer(bufferSize);
  }

  public void setExceptionHandler(PoisonExceptionHandler exceptionHandler) {
    this.exceptionHandler = exceptionHandler;
  }

  public void init() {
    for (int i = 0; i < this.workers; i++) {
      Worker worker = new Worker(this.buffer);
      Thread workerThread = new Thread(worker);
      if (this.exceptionHandler != null) {
        workerThread.setUncaughtExceptionHandler(this.exceptionHandler);
      }
      workerThread.start();
    }
  }

  private void launch(Runnable task) {
    this.buffer.write(task);
  }

  public void launchDummyTask() {
    DummyTask task = new DummyTask();
    this.launch(task);
  }

  public void stop() {
    PoisonPill task = new PoisonPill();
    this.launch(task);
  }

}
