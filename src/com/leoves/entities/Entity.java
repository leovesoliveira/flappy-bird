package com.leoves.entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.Random;

public class Entity {

  public static Comparator<Entity> depthSorter = Comparator.comparingInt(n0 -> n0.depth);
  public static Random rand = new Random();
  public int depth;
  public double x;
  public double y;
  public double speed;
  public int width;
  public int height;
  protected BufferedImage sprite;

  public Entity(double x, double y, int width, int height, double speed, BufferedImage sprite) {
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.width = width;
    this.height = height;
    this.sprite = sprite;
  }

  public boolean isColliding(Entity e) {
    Rectangle currentEntity = new Rectangle(getX(), getY(), width, height);
    Rectangle collidingEntity = new Rectangle(e.getX(), e.getY(), e.width, e.height);

    return currentEntity.intersects(collidingEntity);
  }

  public int getX() {
    return (int) x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public int getY() {
    return (int) y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public BufferedImage getSprite() {
    return sprite;
  }

  public void tick() {}

  public double calculateDistance(int x1, int y1, int x2, int y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }

  public void render(Graphics g) {
    g.drawImage(sprite, this.getX(), this.getY(), null);
  }
}
