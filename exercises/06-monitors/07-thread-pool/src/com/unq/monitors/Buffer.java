package com.unq.monitors;

import java.util.ArrayDeque;

public class Buffer {
  private ArrayDeque queue;
  private int capacity;

  public Buffer(int n) {
    this.queue = new ArrayDeque(n);
    this.capacity = n;
  }

  private boolean isEmpty() {
    return this.queue.isEmpty();
  }

  private boolean isFull() {
    return this.queue.size() == this.capacity;
  }

  public synchronized Object read() {
    while (isEmpty()) {
      try {
        wait();
      } catch (InterruptedException e) {
        System.out.println(String.format("Buffer ::: can't read an empty buffer, err=%s", e.toString()));
        e.printStackTrace();
      }
    }
    Object current = this.queue.pop();
    notifyAll();
    return current;
  }

  public synchronized void write(Object object) {
    while (isFull()) {
      try {
        wait();
      } catch (InterruptedException e) {
        System.out.println(String.format("Buffer ::: can't write a full buffer, err=%s", e.toString()));
        e.printStackTrace();
      }
    }
    this.queue.add(object);
    notifyAll();
  }
}



