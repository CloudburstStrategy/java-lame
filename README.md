# Java LAME
This java port of LAME 3.98.4 was created by Ken Händel for his 'jump3r - Java Unofficial MP3 EncodeR' project:
http://sourceforge.net/projects/jsidplay2/

Original sources by the authors of LAME: http://www.sourceforge.net/projects/lame

The code is - as the original - licensed under the LGPL (see LICENSE).

This is a mavenised version of Nico Waldispühl's project the here: https://github.com/nwaldispuehl/java-lame

## How to get 

Add JCenter to your repos:

    <repository>
        <id>jcenter-snapshots</id>
        <name>jcenter</name>
        <url>https://jcenter.bintray.com/</url>
    </repository>
    
Then include this dependency:

    <dependency>
        <groupId>com.cloudburst</groupId>
        <artifactId>java-lame</artifactId>
        <version>3.98.4</version>
    </dependency>
    

## How to use Java LAME in a project?

To convert a PCM byte array to an MP3 byte array, you may use Ken Händels ```LameEncoder``` which offers the 
following convenience method for converting chunks of pcm byte array:

```
LameEncoder#encodeBuffer(final byte[] pcm, final int pcmOffset, final int pcmLength, final byte[] encoded)
```

A sample of its use:

```java
public byte[] encodePcmToMp3(byte[] pcm) {
  LameEncoder encoder = new LameEncoder(inputFormat, 256, MPEGMode.STEREO, Lame.QUALITY_HIGHEST, false);

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
```


## To Release new version to Bintray

    mvn clean release:prepare -Darguments="-Dmaven.javadoc.skip=true"
    mvn release:perform -Darguments="-Dmaven.javadoc.skip=true"
