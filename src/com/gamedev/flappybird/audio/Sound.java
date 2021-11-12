package com.gamedev.flappybird.audio;

import java.applet.AudioClip;
import java.applet.Applet;

public class Sound {
    public AudioClip clip;
    public static Sound music1= new Sound("/audio.wav");

    public Sound(String path){
        clip=Applet.newAudioClip(getClass().getResource(path));
    }
    public void play(){
        new Thread(){
            public void run(){
                clip.play();
            }
        }.start();
    }
}
