package com.gamedev.flappybird.entities;
import com.gamedev.flappybird.Game;
import com.gamedev.flappybird.audio.Sound;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Pipes {
    public ArrayList<Rectangle> tubes;
    private int time;
    private int currentTime=0;
    private int speed=5;

    private Random random;

    private final int SPACE_TUBES =128;
    private final int WIDTH_TUBES = 64;

    public Pipes(int time){
        tubes=new ArrayList<>();
        this.time=time;

        random=new Random();
    }

    public void update() {
        currentTime++;
        if(currentTime==time){

            currentTime=0;
            int height1=100+random.nextInt(Game.HEIGHT/2);
            int y1=height1+SPACE_TUBES-60;
            int height2=Game.HEIGHT-y1;

            tubes.add(new Rectangle(Game.WIDTH, 0, WIDTH_TUBES, height1-100));
            tubes.add(new Rectangle(Game.WIDTH, y1, WIDTH_TUBES, height2-100));
        }

        for(int i=0; i< tubes.size(); i++){
            Rectangle rect= tubes.get(i);
            rect.x-=speed;
        }
    }
    public void render(Graphics g) {
        g.setColor(Color.green.darker());

        for(int i=0; i< tubes.size(); i++){
            Rectangle rect= tubes.get(i);
            g.fillRect(rect.x, rect.y, rect.width,rect.height);

            if(rect.x+rect.width<=0){
                if(!Game.gameOver){
                    Sound.music1.play();
                    tubes.remove(i--);
                    Game.score+=0.5;
                }
                continue;
            }
        }
    }
}
