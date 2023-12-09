import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class SoundEffects {
	private Clip clip;
  
  //배경음악
    public void loadAudio() {
    	try {
    		this.clip = AudioSystem.getClip();
    		//File soundFile = new File("startGameTheme.wav");
    		File soundFile = new File("startGameTheme.wav");
    		AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
    		this.clip.open(audioStream);

    		// 배경음악 무한 반복재생
    		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    		this.clip.start();
    	} 
    	catch (LineUnavailableException e) {e.printStackTrace();}
    	catch (UnsupportedAudioFileException e) {e.printStackTrace();}
    	catch (IOException e) { e.printStackTrace();}
    }

    // 맞출 때의 효과음 재생
    public void okAudio() {
        playSound("shotbubble.wav");
    }

    // 틀릴 때의 효과음 재생
    public void notOkAudio() {
        playSound("notOk.wav");
    }

    // 버블 터질 때의 효과음 재생
    public void bubblePopAudio() {
        playSound("bubblePop.wav");
    }

    // 새 생명 아이템 효과음 재생
    public void newLifeAudio() {
        playSound("newLife.wav");
    }

    // 시간 아이템 효과음 재생
    public void increaseTimeAudio() {
        playSound("timeIncrease.wav");
    }
    // 게임 오버 효과음 
    public void gameOverAudio() {
        playSound("gameOver.wav");
    }
    // 레벨업 효과음
    public void levelUpAudio() {
        playSound("levelUp.wav");
    }


    // 키보드 타건 효과음 재생
    public void keyBordAudio() {
        playSound("keybord.wav");
    }

    // 오디오 파일을 로드하고 재생하는 메소드
    private void playSound(String soundFileName) {
        try {
            Clip clip = AudioSystem.getClip();
            File audioFile = new File(soundFileName);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioStream);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
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