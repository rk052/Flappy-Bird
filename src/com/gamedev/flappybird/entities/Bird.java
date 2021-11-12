package com.gamedev.flappybird.entities;

import com.gamedev.flappybird.Game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Bird extends Rectangle{
    private float spd=4;
    public boolean isPressed=false;
    private BufferedImage image;

    private ArrayList<Rectangle> tubes;
    private boolean isFalling=false;
    private float gravity=0.3f;

    public Bird(int x,int y,ArrayList<Rectangle>tubes){
        setBounds(x,y,32,32);
        this.tubes=tubes;
        try{
            image=ImageIO.read(getClass().getResource("/flappy.png"));

        }catch(IOException e){}
    }
    public void update(){
        isFalling=false;
        if(isPressed){
            spd=4;
            y-=spd;
        }
        else{
            isFalling=true;
            y+=spd;

        }
        if(isFalling){
            spd+=gravity;
            if(spd>8){
                spd=8;
            }
        }
        for(int i=0;i<tubes.size();i++){
            if(this.intersects(tubes.get(i))){
                Game.gameOver=true;
                break;
            }
        }
        if(y>=Game.HEIGHT || y<0){
            Game.gameOver=true;
        }


    }
    public void render(Graphics g){
        g.drawImage(image,x,y,width,height,null);
    }
}
