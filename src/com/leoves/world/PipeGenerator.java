package com.leoves.world;

import com.leoves.entities.Pipe;
import com.leoves.entities.ScoreLine;
import com.leoves.main.Game;

public class PipeGenerator {
  public int time = 0;
  public int targetTime = 100;

  public PipeGenerator() {
    generatePipe();
  }

  private void generatePipe() {
    if (Game.status != Game.Status.PLAYING) return;

    int spaceBetween = 60;
    int minimalHeight = 20;
    int totalHeight = Game.HEIGHT - spaceBetween;

    int width = 32;

    int pipeUpHeight = Pipe.rand.nextInt(totalHeight - (minimalHeight * 2)) + minimalHeight;
    Pipe pipeUp = new Pipe(Game.WIDTH, 0, width, pipeUpHeight, Pipe.Direction.UP);

    int pipeDownHeight = totalHeight - pipeUpHeight;
    Pipe pipeDown =
        new Pipe(
            Game.WIDTH, Game.HEIGHT - pipeDownHeight, width, pipeDownHeight, Pipe.Direction.DOWN);

    ScoreLine scoreLine = new ScoreLine(Game.WIDTH + width, 0);

    Game.entities.add(pipeUp);
    Game.entities.add(pipeDown);
    Game.entities.add(scoreLine);
  }

  public void tick() {
    if (Game.status != Game.Status.PLAYING) return;

    time++;
    if (time >= targetTime) {
      time = 0;
      generatePipe();
    }
  }
}
