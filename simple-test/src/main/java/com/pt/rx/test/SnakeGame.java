package com.pt.rx.test;

import java.util.List;
import java.util.Queue;

public class SnakeGame {

	public static void main(String[] args) {
		
	}
	
	
	
	
	
	
	
}


class Scene {
	Queue<Point> snake;
	List<Point> apple;
	int score;
	
	
	public Scene(Queue<Point> snake, List<Point> apple, int score) {
		super();
		this.snake = snake;
		this.apple = apple;
		this.score = score;
	}
	public Queue<Point> getSnake() {
		return snake;
	}
	public void setSnake(Queue<Point> snake) {
		this.snake = snake;
	}
	public List<Point> getApple() {
		return apple;
	}
	public void setApple(List<Point> apple) {
		this.apple = apple;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
}

class Point{
	int x;
	int y;
	
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}