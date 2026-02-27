package com.leoves.graphics;

import com.leoves.main.Game;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

public class UI {

  private Graphics2D g2;
  private FontRenderContext frc;

  public void render(Graphics g) {
    g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    frc = g2.getFontRenderContext();

    write(String.valueOf(Game.score), 56, Color.WHITE, 8, Game.WIDTH * Game.SCALE / 2 - 16, 60);

    if (Game.status == Game.Status.START) {
      write(
          "PRESS SPACE TO START",
          60,
          new Color(255, 242, 48),
          12,
          Game.WIDTH * Game.SCALE / 2 - 200,
          170);
    }

    if (Game.status == Game.Status.GAME_OVER) {
      write("GAME OVER", 120, new Color(255, 242, 48), 15, Game.WIDTH * Game.SCALE / 2 - 200, 180);
      write("PRESS ESC TO RESTART", 42, Color.WHITE, 12, Game.WIDTH * Game.SCALE / 2 - 140, 230);
    }
  }

  private void write(String text, int size, Color color, int stroke, int x, int y) {
    Font font = Game.font.deriveFont(Font.PLAIN, size);
    TextLayout gameOverTl = new TextLayout(text, font, frc);

    Shape textShape =
        gameOverTl.getOutline(java.awt.geom.AffineTransform.getTranslateInstance(x, y));

    g2.setColor(Color.BLACK);
    g2.setStroke(new BasicStroke(stroke));
    g2.draw(textShape);

    g2.setColor(color);
    g2.fill(textShape);
  }
}
