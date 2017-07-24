package com.cloudburst.lame;

import com.cloudburst.lame.lowlevel.LameEncoder;
import com.cloudburst.lame.mp3.Lame;
import com.cloudburst.lame.mp3.MPEGMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Created by ajchesney on 21/07/2017.
 */
public class SimpleMp3Encoder {


    public void encodePcmToMp3 (File pcmInput, File destination){
        try {
            AudioFormat inputFormat = AudioSystem.getAudioFileFormat(pcmInput).getFormat();
            LameEncoder encoder = new LameEncoder(inputFormat, 320, MPEGMode.STEREO, Lame.QUALITY_HIGHEST, false);
            byte[] buffer = new byte[encoder.getPCMBufferSize()];
            byte[] pcm = new byte[encoder.getPCMBufferSize()];
            int bytesWritten;
            try(FileOutputStream mp3 = new FileOutputStream(destination)){
                try(FileInputStream fin = new FileInputStream(pcmInput)){
                    int read = 0;
                    while ( (read = fin.read(buffer)) != -1){
                        bytesWritten = encoder.encodeBuffer(pcm,0,read,buffer);
                        mp3.write(buffer, 0, bytesWritten);
                    }
                }
            }
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] encodePcmToMp3(byte[] pcm, AudioFormat inputFormat) {
        LameEncoder encoder = new LameEncoder(inputFormat, 256, MPEGMode.MONO, Lame.QUALITY_HIGHEST, false);

        ByteArrayOutputStream mp3 = new ByteArrayOutputStream();
        byte[] buffer = new byte[encoder.getPCMBufferSize()];

        int bytesToTransfer = Math.min(buffer.length, pcm.length);
        int bytesWritten;
        int currentPcmPosition = 0;
        while (0 < (bytesWritten = encoder.encodeBuffer(pcm, currentPcmPosition, bytesToTransfer, buffer))) {
            currentPcmPosition += bytesToTransfer;
            bytesToTransfer = Math.min(buffer.length, pcm.length - currentPcmPosition);
            mp3.write(buffer, 0, bytesWritten);
        }

        encoder.close();
        return mp3.toByteArray();
    }

}
