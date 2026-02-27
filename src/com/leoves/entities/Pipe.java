package com.leoves.entities;

import com.leoves.main.Game;
import com.leoves.main.Sound;
import java.awt.*;

public class Pipe extends Entity {
  private Direction direction;

  public Pipe(float x, float y, int width, int height, Direction direction) {
    super(x, y, width, height, 0, null);
    this.direction = direction;
  }

  public void tick() {
    depth = 1;
    x--;

    if (x + width < 0) {
      Game.entities.remove(this);
    }

    if (isColliding(Game.player)) {
      Game.status = Game.Status.GAME_OVER;
      Sound.HIT.play();
    }
  }

  public void render(Graphics g) {
    int topHeight = 20;

    if (direction == Direction.UP) {
      g.drawImage(
          Game.spritesheet.PIPE_DOWN, (int) x, (int) y + height - topHeight, 36, topHeight, null);

      for (int i = 1; i <= height - topHeight; i++) {
        g.drawImage(Game.spritesheet.PIPE, (int) x + 2, (int) y + height - topHeight - i, null);
      }
    }

    if (direction == Direction.DOWN) {
      g.drawImage(Game.spritesheet.PIPE_UP, (int) x, (int) y, 36, topHeight, null);

      for (int i = 1; i <= height - topHeight; i++) {
        g.drawImage(Game.spritesheet.PIPE, (int) x + 2, (int) y + topHeight - 1 + i, null);
      }
    }
  }

  public enum Direction {
    UP,
    DOWN
  }
}
