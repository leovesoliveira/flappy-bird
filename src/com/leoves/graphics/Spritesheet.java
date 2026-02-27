package com.leoves.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Spritesheet {

  private final BufferedImage spritesheet;
  public BufferedImage PLAYER;
  public BufferedImage PIPE;
  public BufferedImage PIPE_UP;
  public BufferedImage PIPE_DOWN;

  public Spritesheet(String path) {
    try {
      spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));

      PLAYER = spritesheet.getSubimage(0, 0, 21, 15);
      PIPE = spritesheet.getSubimage(23, 0, 32, 1);
      PIPE_UP = spritesheet.getSubimage(57, 0, 36, 20);
      PIPE_DOWN = spritesheet.getSubimage(21, 2, 36, 20);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public BufferedImage getSprite(int spriteX, int spriteY) {
    assert spritesheet != null;

    int x = 0, y = 0;

    if (spriteX > 1) x = (spriteX - 1) * 16;
    if (spriteY > 1) y = (spriteY - 1) * 16;

    return spritesheet.getSubimage(x, y, 16, 16);
  }
}
