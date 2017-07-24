import com.cloudburst.lame.SimpleMp3Encoder;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;

/**
 * Created by ajchesney on 21/07/2017.
 */
public class SimpleMp3EncoderTest {


    @Test
    public void encodeTest2() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File pcmFile = new File(classLoader.getResource("test.wav").getFile());
        File mp3File = File.createTempFile("mp3encoder-test",".mp3");

        SimpleMp3Encoder encoder = new SimpleMp3Encoder();
        encoder.encodePcmToMp3(pcmFile,mp3File);

        System.out.print("Try playing the sound at: " + mp3File.getAbsolutePath());

    }

    @Test
    public void encodeTest() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        File pcmFile = new File(classLoader.getResource("test.wav").getFile());
        AudioFormat inputFormat = AudioSystem.getAudioFileFormat(pcmFile).getFormat();
        byte[] data = Files.readAllBytes(pcmFile.toPath());

        SimpleMp3Encoder encoder = new SimpleMp3Encoder();

        byte[] mp3 = encoder.encodePcmToMp3(data,inputFormat);

        //File mp3File = File.createTempFile("mp3encoder-test",".mp3");
        File mp3File = new File("test.mp3");


        Files.write(mp3File.toPath(), mp3);


        System.out.print("Try playing the sound at: " + mp3File.getAbsolutePath());

    }


}
