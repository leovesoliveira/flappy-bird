package com.leoves.entities;

import com.leoves.main.Game;
import com.leoves.main.Sound;
import java.awt.*;

public class ScoreLine extends Entity {
  private boolean scored = false;

  public ScoreLine(double x, double y) {
    super(x, y, 1, Game.HEIGHT, 0, null);
  }

  public void tick() {
    depth = 1;
    x--;

    if (x + width < 0) {
      com.leoves.main.Game.entities.remove(this);
      return;
    }

    if (!scored && isColliding(Game.player)) {
      scored = true;
      Game.score++;
      Sound.SCORE.play();
    }
  }

  public void render(Graphics g) {
    // ScoreLine is invisible
  }
}
