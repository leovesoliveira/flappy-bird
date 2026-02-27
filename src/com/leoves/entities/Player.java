package com.leoves.entities;

import com.leoves.main.Game;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Player extends Entity {

  public final double fallingMaxAngle = Math.toRadians(60);
  private final double flyMaxAngle = Math.toRadians(-30);
  public boolean isPressed = false;
  public boolean isFlying = false;
  public int flyFrame = 0;
  public double angle = 0;

  public Player(int x, int y) {
    super(x, y, 21, 15, 1, null);
  }

  public void tick() {
    depth = 2;
    flyFrame++;
    if (flyFrame > 15) {
      flyFrame = 0;
      isFlying = false;
    }

    if (isFlying) {
      y -= 2;

      angle += Math.toRadians(-1);
      if (angle > flyMaxAngle) {
        angle = flyMaxAngle;
      }

    } else {
      y += 2;
      flyFrame = 0;

      angle += Math.toRadians(5);
      if (angle > fallingMaxAngle) {
        angle = fallingMaxAngle;
      }
    }

    if (y > Game.HEIGHT || y < 0) {
      Game.status = Game.Status.GAME_OVER;
    }
  }

  public void render(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(
        RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

    AffineTransform original = g2.getTransform();

    g2.rotate(angle, this.getX() + 8, this.getY() + 8);
    g2.drawImage(Game.spritesheet.PLAYER, this.getX(), this.getY(), width, height, null);

    g2.setTransform(original);
  }
}
