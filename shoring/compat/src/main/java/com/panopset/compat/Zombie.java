package com.panopset.compat;

import java.util.ArrayList;
import java.util.List;

public class Zombie {

  private boolean active = true;

  public void stop() {
    if (!isActive()) {
      return;
    }
    active = false;
    for (Runnable sa : stopActions) {
      sa.run();
    }
  }

  public void resume() {
    active = true;
  }

  public boolean isActive() {
    return active;
  }

  public void addStopAction(Runnable runnable) {
    if (!stopActions.contains(runnable)) {
      stopActions.add(runnable);
    }
  }

  public void removeStopAction(Runnable runnable) {
    stopActions.remove(runnable);
  }

  private final List<Runnable> stopActions = new ArrayList<>();
}
