package com.gamedev.flappybird;

import com.gamedev.flappybird.display.Display;

import com.gamedev.flappybird.entities.Bird;
import com.gamedev.flappybird.entities.Pipes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Game implements Runnable , KeyListener {
    private Display display;
    public static final int WIDTH=800, HEIGHT=640;
    public String title;

    private boolean running=false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    private BufferedImage background;

    private Pipes pipes;
    private Bird bird;

    public static double score=0;
    public static boolean gameOver=false;

    public Game(String title){
        this.title=title;
    }

    private void init(){
        display=new Display(title, WIDTH, HEIGHT);
        pipes = new Pipes(100);
        bird = new Bird(20, HEIGHT/2, pipes.tubes);
        display.getFrame().addKeyListener(this);
        try{
            background= ImageIO.read(getClass().getResource("/background.png"));

        }catch(IOException e){}
    }

    private void update(){
        pipes.update();
        bird.update();
    }

    private void render(){
        bs=display.getCanvas().getBufferStrategy();

        if(bs==null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g= bs.getDrawGraphics();
        g.drawImage(background,0,0, WIDTH,HEIGHT, null);

        if(gameOver){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", 1, 100));
            g.drawString("Game Over!", 120, HEIGHT/2-50);

            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", 1, 50));
            g.drawString("Score: "+(int)score, 290, HEIGHT/2);
        }
        else{
            pipes.render(g);
            bird.render(g);

            g.setColor(Color.BLUE);
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
            g.drawString("Score:" +(int)score, 10, 20);
        }

        bs.show();
        g.dispose();
    }

    public void run(){
        init();
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;

            while (delta >= 1){
                update();
                render();
                delta--;
            }
        }
        stop();
    }

    public synchronized void start(){
        if(running) return;
        running=true;

        thread=new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running) return;
        running=false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP){
            bird.isPressed=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP){
            bird.isPressed=false;
        }
    }
}
