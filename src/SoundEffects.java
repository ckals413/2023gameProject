import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundEffects {
	 private Clip clip;
	    private Clip okClip; // 맞췄을 때 
	    private Clip notOkClip; // 오답.
	    private Clip bubblePopClip;
	    private Clip newLifeClip;
	    private Clip increaseTimeClip;
	    private Clip keyBordClip;
  
	    // 배경음악
	      public void loadAudio(String pathName) {
	          try {
	              this.clip = AudioSystem.getClip();
	              File soundFile = new File(pathName);
	              AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
	              GameFrame.clip.open(audioStream);
//	              this.clip.open(audioStream);
//
//	              // 배경음악 무한 반복재생
//	              this.clip.loop(Clip.LOOP_CONTINUOUSLY);
//	              this.clip.start();
	          } 
	          catch (LineUnavailableException e) {e.printStackTrace();}
	         catch (UnsupportedAudioFileException e) {e.printStackTrace();}
	         catch (IOException e) { e.printStackTrace();}
	      }
	      // 맞출때 효과음
	      public void okAudio() {
	         try {
	            okClip = AudioSystem.getClip();
	            File audioFile = new File("shotbubble.wav");
	            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
	            okClip.open(audioStream);
	            okClip.start();
	            System.out.println("맞춤");
	         } 
	         catch (LineUnavailableException e) {e.printStackTrace();}
	         catch (UnsupportedAudioFileException e) {e.printStackTrace();}
	         catch (IOException e) { e.printStackTrace();}
	         
	      }
	      
	      // 틀릴때 효과음
	      public void notOkAudio() {
	         try {
	            notOkClip = AudioSystem.getClip();
	            File audioFile = new File("notOk.wav");
	            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
	            notOkClip.open(audioStream);
	            notOkClip.start();
	         } 
	         catch (LineUnavailableException e) {e.printStackTrace();}
	         catch (UnsupportedAudioFileException e) {e.printStackTrace();}
	         catch (IOException e) { e.printStackTrace();}
	      }
	      //버블 터질 때 효과음
	      public void bubblePopAudio() {
	         try {
	            bubblePopClip = AudioSystem.getClip();
	            File audioFile = new File("bubblePop.wav");
	            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
	            bubblePopClip.open(audioStream);
	            bubblePopClip.start();
	         }
	         catch (LineUnavailableException e) {e.printStackTrace();}
	         catch (UnsupportedAudioFileException e) {e.printStackTrace();}
	         catch (IOException e) { e.printStackTrace();}
	      }
	      //새 생명 아이템 효과음
	      public void newLifeAudio() {
	         try {
	           newLifeClip = AudioSystem.getClip();
	            File audioFile = new File("newLife.wav");
	            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
	            newLifeClip.open(audioStream);
	            newLifeClip.start();
	         }
	         catch (LineUnavailableException e) {e.printStackTrace();}
	         catch (UnsupportedAudioFileException e) {e.printStackTrace();}
	         catch (IOException e) { e.printStackTrace();}
	      }
	      
	      //시간 아이템 효과음
	      public void increaseTimeAudio() {
	          try {
	            increaseTimeClip = AudioSystem.getClip();
	             File audioFile = new File("timeIncrease.wav");
	             AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
	             increaseTimeClip.open(audioStream);
	             increaseTimeClip.start();
	          }
	          catch (LineUnavailableException e) {e.printStackTrace();}
	          catch (UnsupportedAudioFileException e) {e.printStackTrace();}
	          catch (IOException e) { e.printStackTrace();}
	       }
	      
	      //키보드 타건 효과음
	      public void keyBordAudio() {
	          try {
	             keyBordClip = AudioSystem.getClip();
	             File audioFile = new File("keybord.wav");
	             AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
	             keyBordClip.open(audioStream);
	             keyBordClip.start();
	          }
	          catch (LineUnavailableException e) {e.printStackTrace();}
	          catch (UnsupportedAudioFileException e) {e.printStackTrace();}
	          catch (IOException e) { e.printStackTrace();}
	       }
	      
	      
//    
// // 배경 오디오 관련 쓰레드
 	class AudioThread extends Thread {
 		@Override
 		synchronized public void run() {
 			try {
 				GameFrame.clip.loop(Clip.LOOP_CONTINUOUSLY); // 무한재생
 			} catch (Exception e) {
 				e.printStackTrace();
 			}
 		}
 	}

    
    
    public void closeAudio() {
    	  if (clip != null) {
    		  clip.close();
          }
    }
    public void stopAudio() {
    	clip.stop();
    }
    public void startAudio() {
    	clip.start();
    }
    
}
