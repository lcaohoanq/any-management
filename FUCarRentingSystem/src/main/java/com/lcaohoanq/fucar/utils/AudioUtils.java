package com.lcaohoanq.fucar.utils;

import com.lcaohoanq.fucar.exceptions.ResourceNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AudioUtils {

    private static AudioUtils instance;
    private InputStream inputStreamCurrent;
    private AudioInputStream audioInputStream;
    private Clip clip;
    private boolean path = true;

    public boolean isEmptyPath() {
        return !path;
    }

    public static AudioUtils getInstance() {
        if (instance == null){
            instance = new AudioUtils();
        }
        return instance;
    }

    private byte[] getAudioData(InputStream inputStream) {
        try {
            return inputStream.readAllBytes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void playAudio(InputStream inputStream) {
        try {
            if (inputStreamCurrent != null) {
                throw new ResourceNotFoundException("Audio file is missing");
            }
            // Use ByteArrayInputStream to create an AudioInputStream
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getAudioData(inputStream));
            audioInputStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
