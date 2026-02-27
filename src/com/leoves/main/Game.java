package com.leoves.main;

import com.leoves.entities.Entity;
import com.leoves.entities.Player;
import com.leoves.graphics.Spritesheet;
import com.leoves.graphics.UI;
import com.leoves.world.PipeGenerator;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Game extends Canvas implements Runnable, KeyListener {

  public static final int WIDTH = 240;
  public static final int HEIGHT = 160;
  public static final int SCALE = 4;
  public static JFrame frame;
  public static Spritesheet spritesheet;
  public static Player player;
  public static List<Entity> entities;
  public static int score = 0;
  public static PipeGenerator pipeGenerator;
  public static Font font;
  public static Status status = Status.START;
  private final BufferedImage image;
  public UI ui;
  public InputStream stream =
      ClassLoader.getSystemClassLoader().getResourceAsStream("Micro5-Regular.ttf");
  private Thread thread;
  private boolean isRunning = true;

  public Game() {
    addKeyListener(this);
    setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
    initFrame();
    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    spritesheet = new Spritesheet("/spritesheet.png");

    entities = new ArrayList<>();

    player = new Player(0, 0);
    player.setX(((double) WIDTH / 2) - ((double) player.getWidth() / 2) - 32);
    player.setY(((double) HEIGHT / 2) - ((double) player.getHeight() / 2));
    entities.add(player);

    pipeGenerator = new PipeGenerator();

    ui = new UI();

    try {
      font = Font.createFont(Font.TRUETYPE_FONT, stream);
    } catch (FontFormatException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    Game game = new Game();
    game.start();
  }

  public static void restart() {
    entities = new ArrayList<>();

    player = new Player(0, 0);
    player.setX(((double) WIDTH / 2) - ((double) player.getWidth() / 2) - 32);
    player.setY(((double) HEIGHT / 2) - ((double) player.getHeight() / 2));
    entities.add(player);

    score = 0;
  }

  public void initFrame() {
    frame = new JFrame("Flappy Bird");
    frame.add(this);
    frame.setResizable(false);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public synchronized void start() {
    thread = new Thread(this);
    isRunning = true;
    thread.start();
  }

  public synchronized void stop() {
    isRunning = false;

    try {
      thread.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void tick() {
    if (status == Status.PLAYING) {
      pipeGenerator.tick();

      for (int i = 0; i < entities.size(); i++) {
        entities.get(i).tick();
      }
    }

    if (status == Status.GAME_OVER) {
      if (HEIGHT + player.height + player.width > player.y) player.y += 2;

      player.angle += Math.toRadians(5);
      if (player.angle > player.fallingMaxAngle) {
        player.angle = player.fallingMaxAngle;
      }
    }
  }

  public void render() {
    BufferStrategy bs = this.getBufferStrategy();

    if (bs == null) {
      this.createBufferStrategy(3);
      return;
    }

    Graphics g = image.getGraphics();
    g.setColor(new Color(125, 200, 215));
    g.fillRect(0, 0, WIDTH, HEIGHT);

    entities.sort(Entity.depthSorter);

    for (Entity e : entities) {
      e.render(g);
    }

    g.dispose();
    g = bs.getDrawGraphics();
    g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

    ui.render(g);

    bs.show();
  }

  @Override
  public void run() {
    long lastTime = System.nanoTime();
    double amountOfTicks = 60.0;
    double ns = 1000000000 / amountOfTicks;
    double delta = 0;
    int frames = 0;
    double timer = System.currentTimeMillis();

    requestFocus();

    while (isRunning) {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;

      if (delta >= 1) {
        tick();
        render();
        frames++;
        delta--;
      }

      if (System.currentTimeMillis() - timer >= 1000) {
        System.out.println("FPS: " + frames);
        frames = 0;
        timer += 1000;
      }
    }

    stop();
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      if (status == Status.START) status = Status.PLAYING;

      if (!player.isPressed && status == Status.PLAYING) {
        Sound.FLY.play();
        player.isFlying = true;
        player.isPressed = true;
      }
    }

    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      if (status == Status.GAME_OVER) {
        restart();
        status = Status.START;
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      player.isPressed = false;
    }
  }

  public enum Status {
    START,
    PLAYING,
    GAME_OVER
  }
}
