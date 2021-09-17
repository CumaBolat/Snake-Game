import java.awt.*;
import java.awt.event.*;
import java.awt.image.ColorConvertOp;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.*;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener{
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static final int SIZE =25;
	private static int DELAY = 45;
	private int snakeSize = 6;
	private int h;
	private static final int SCREEN_SIZE = (WIDTH * HEIGHT) / SIZE;
	private int[] x = new int[SCREEN_SIZE];
	private int[] y = new int[SCREEN_SIZE];
	Random random;
	Timer timer;
	private boolean isGameOver = true;
	private char direction = 'R';
	private int appleX;
	private int appleY;
	private int applesEaten = 0;
	private int phase = 10;
	
	
	public SnakeGame() {
		random = new Random();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.white);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		playGame();
	}
	
	
	private void playGame() {
		createApple();
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void drawApple(Graphics g) {
		
		g.setColor(Color.red);
		g.fillOval(appleX, appleY, SIZE, SIZE);
		
	}
	
	public void drawSnake(Graphics g) {
		
		for (int i = snakeSize; i > 0; i--) {
			if (i % 2 == 1) {
				g.setColor(Color.yellow);
			} else {
				g.setColor(Color.black);
			}
			g.fillRect(x[i], y[i], SIZE, SIZE);
			
		}
		g.setColor(Color.blue);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
	}
	
	public void move() {
		
		for (int i = snakeSize; i > 0; i--) {
			
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
		
		switch (direction) {
		case 'U': 
			y[0] = y[0] - SIZE;
			break;
		case 'D':
			y[0] = y[0] + SIZE;
			break;
		case 'R':
			x[0] = x[0] + SIZE;
			break;
		case 'L':
			x[0] = x[0] - SIZE;
			break;
		}	
	}
	
	private void checkSize() {
		
		if (x[0] == appleX && y[0] == appleY) {
			
			snakeSize++;
			x[snakeSize] = x[snakeSize - 1];
			y[snakeSize] = y[snakeSize - 1];
			createApple();
			applesEaten++;
			System.out.println(applesEaten);
			if (applesEaten == phase) {
				
				DELAY = DELAY - 10;
				phase+=5;
			}
		}	
	}
	
	private void isGameOver(){
		
		if (x[0] < 0 || x[0] == WIDTH 
				|| y[0] < 0 || y[0] == HEIGHT) {
			
			isGameOver = false;
			return;
		}
		for (int i = snakeSize; i > 0; i--) {
			
			if (x[0] == x[i] && y[0] == y[i]) {
				isGameOver = false;
				return;
			}
			
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		lines(g);
		drawApple(g);
		drawSnake(g);
		if (!isGameOver) {
			gameOver(g);
		}
	}
	
	public void lines(Graphics g) {
		
		for (int i = 0; i < WIDTH / SIZE; i++) {
			g.setColor(Color.gray);
			g.drawLine(0, i*SIZE, i*SIZE, HEIGHT);
			g.drawLine(HEIGHT, i*SIZE, i*SIZE, 0);
			
		}
	}
	
	
	private void createApple() {
		// TODO Auto-generated method stub
		appleX = random.nextInt(WIDTH / SIZE) * SIZE;
		appleY = random.nextInt(HEIGHT / SIZE) * SIZE;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (isGameOver) {
			
			move();
			isGameOver();
			checkSize();
			repaint();
		} 	
	}
	
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (WIDTH - metrics2.stringWidth("Game Over"))/2, HEIGHT/2);
		JButton btnNewButton_1 = new JButton("play again");
		btnNewButton_1.setFont(new Font("Ink Free", Font.BOLD, 15));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setForeground(Color.RED);
		btnNewButton_1.setBounds(WIDTH/2 - 50, HEIGHT/2 + 50,100, 50);
		this.add(btnNewButton_1);
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}
