package com.unq.tasks;

import com.unq.exeptions.PoisonException;

public class PoisonPill implements Runnable {
  public void run() throws PoisonException {
    throw new PoisonException();
  }
}
