import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


//왼쪽 패널임 게임이 돌아가는 패널
public class GameGround extends JPanel{
   private ImageIcon groundIcon = new ImageIcon("groundImg.png");// 게임패널 배경화면에 사용
   private Image groundImg = groundIcon.getImage(); //게임패널 배경화면에 사용
   private ScorePanel scorePanel = null;
   private ImageIcon monster1_1 = new ImageIcon("monster1_1.png");
   private ImageIcon monster1_2 = new ImageIcon("monster1_2.png");
   private ImageIcon monster2_1 = new ImageIcon("monster2_1.png");
   private ImageIcon monster2_2 = new ImageIcon("monster2_2.png");
   private ImageIcon monster3_1 = new ImageIcon("monster3_1.png");
   private ImageIcon monster3_2 = new ImageIcon("monster3_2.png");
   private ImageIcon monster4_1 = new ImageIcon("monster4_1.png");
   private ImageIcon monster4_2 = new ImageIcon("monster4_2.png");
   private ImageIcon timeItem1_1 = new ImageIcon("timeItem1_1.png");
   private ImageIcon timeItem1_2 = new ImageIcon("timeItem1_2.png");
   private ImageIcon heartItem1_1 = new ImageIcon("heartItem1_1.png");
   private ImageIcon heartItem1_2 = new ImageIcon("heartItem1_2.png");
   private ImageIcon defenseItem1_1 = new ImageIcon("defenseItem1_1.png");
   private ImageIcon defenseItem1_2 = new ImageIcon("defenseItem1_2.png");
    private ImageIcon bubble1 = new ImageIcon("bubble1.png");
    private ImageIcon bubble2 = new ImageIcon("bubble2.png");
    private ImageIcon bubble3 = new ImageIcon("bubble3.png");
    private ImageIcon bubble4 = new ImageIcon("bubble4.png");
    private ImageIcon heartBubble = new ImageIcon("heartBubble.png");
    private ImageIcon timeBubble = new ImageIcon("timeBubble.png");
    private ImageIcon defenseBubble = new ImageIcon("defenseBubble.png");
    private ImageIcon bubblePop1 = new ImageIcon("bubblePop1.png");
    private ImageIcon bubblePop2 = new ImageIcon("bubblePop2.png");
    private ImageIcon bubblePop3 = new ImageIcon("bubblePop3.png");
    private ImageIcon bubblePop4 = new ImageIcon("bubblePop4.png");
    private ImageIcon heartBubblePop = new ImageIcon("heartBubblePop.png");
    private ImageIcon timeBubblePop = new ImageIcon("timeBubblePop.png");
    private ImageIcon defenseBubblePop = new ImageIcon("defenseBubblePop.png");
    
    private ImageIcon playerIcon1_1 = new ImageIcon("player1_1.png");
    private ImageIcon playerIcon1_2 = new ImageIcon("player1_2.png");
    private ImageIcon playerIcon2_1 = new ImageIcon("player2_1.png");
    private ImageIcon playerIcon2_2 = new ImageIcon("player2_2.png");
    
    
    private boolean isPaused = false; // 몬스터들을 정지 상태를 관리하는 플래그
    public int speed = 5;
    public int levelSpeed = 0;
    public int monsterSpeed = 2;
    public int generationTime = 500;
    public int time = 4;
    private boolean isInvincible = false; // 무적 상태 플래그
    private int invincibleCount = 0; // 무적 상태 지속 횟수
    
    private SoundEffects soundEffects = new SoundEffects();
   
    
    private boolean isMonster1Displayed = true; // 현재 이미지 상태 추적
   //private JLabel label = new JLabel("여기",monster1,SwingConstants.CENTER);
   private JTextField textInput = new JTextField(20);
   private TextSource textSource = null;
   
   private Vector<JLabel>monsterVector = new Vector<JLabel>(3000); // targetLabel을 담는 targetVector
   //private TextSource textSource = new TextSource(this); // 단어 벡터 생성
   // 단어를 생성하는 스레드
   private GenerateWordThread generateWordThread = new GenerateWordThread(monsterVector);
   // 단어를 떨어뜨리는 스레드
   private DropWordThread dropWordThread = new DropWordThread(monsterVector);
   // 땅에 닿은 단어 감지하는 스레드
   private DetectLeftThread detectBottomThread = new DetectLeftThread(monsterVector);
   // 맞힌 단어를 위로 올리는 스레드
   private CheckLabelPositionThread checkLabelPositionThread = new CheckLabelPositionThread();
   
   //이미지 상태를 독립적으로 추적
   private Map<JLabel, Boolean> labelImageState = new HashMap<>(3000);
   //위로 올라가는 거 
   private Vector<JLabel> movingLabels = new Vector<>();
   //시간 추적
   private TimeThread timeThread;
    
   //화면 왼쪽 플레이어 
   private JLabel playerLabel;
   private String selectedOption;
   
//   // SoundEffects 객체를 설정하는 메서드
//   public void setSoundEffects(SoundEffects soundEffects) {
//       this.soundEffects = soundEffects;
//   }

   

   public GameGround(ScorePanel scorePanel, String selectedOption) {
      this.selectedOption = selectedOption;
     this.scorePanel = scorePanel;
     System.out.println("옵션"+selectedOption);
      
      // 스레드 생성자 부르기
      generateWordThread = new GenerateWordThread(monsterVector);
      dropWordThread = new DropWordThread(monsterVector);
      textSource = new TextSource(this); // 단어 벡터 생성
      checkLabelPositionThread = new CheckLabelPositionThread();
        timeThread = new TimeThread(); // TimeThread 인스턴스 생성
      
      setLayout(null);
      textInput.setSize(300,20);// 입력창 크기 설정
      textInput.setLocation(60,450);// 입력창 위치 설정
      add(textInput);
      
      
      ImageIcon playerIcon;
      if ("P1".equals(selectedOption)) {
          playerIcon = playerIcon1_1;
      } else if ("P2".equals(selectedOption)) {
          playerIcon = playerIcon2_1;
      } else {
          // 기본값 또는 예외 처리
          playerIcon = playerIcon1_1; // 기본값으로 설정하거나 오류 처리
      }

      playerLabel = new JLabel(playerIcon);
      playerLabel.setBounds(15, 240, 120, 80);
      add(playerLabel);
      
      
      gameStart();
      


      //플레이어 입력이 맞았는지 확인 
      textInput.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              synchronized(monsterVector) {
                  JTextField t = (JTextField)(e.getSource());
                  String inWord = t.getText(); // 사용자가 입력한 단어
                  for (int i = 0; i < monsterVector.size(); i++) {
                      JLabel targetLabel = monsterVector.get(i); // JLabel을 가져옵니다.
                      String text = targetLabel.getText();
                      if(text.equals(inWord)) { // 단어맞추기 성공
                         soundEffects.okAudio();
                         System.out.println(inWord + " 정답");
                         JLabel matchedLabel = monsterVector.get(i);
                         toggleBubbleImage(matchedLabel);
                         
                          movingLabels.add(targetLabel); // 이동 중인 라벨로 추가
                          monsterVector.remove(i); // 기존 벡터에서는 제거
                          t.setText(null);
                          break; // 일치하는 단어를 찾았으므로 루프 종료
                      }
                      // 벡터 마지막원소에서도 일치하는 단어 못찾음
                      if((i == (monsterVector.size() - 1)) && !monsterVector.get(i).getText().equals(inWord)) {
                         soundEffects.notOkAudio();
                         System.out.println(inWord + "오답");
                          // 점수 감소
                          scorePanel.decrease();
                          scorePanel.repaintScore();
                          t.setText(null);
                      }
                  }
                  t.requestFocus(); // 엔터 친 후에도 textField에 focus유지
              }
          } // end of actionPerformed()
      });
   }
   
   //버블로 이미지 변환
   private void toggleBubbleImage(JLabel label) {
      ImageIcon icon = (ImageIcon) label.getIcon();
        // 몬스터 1 이미지 전환
        if (icon.equals(monster1_1) || icon.equals(monster1_2)) {
           //System.out.println(label.getIcon());
           label.setIcon(bubble1);
        }
        // 몬스터 2 이미지 전환
        else if (icon.equals(monster2_1) || icon.equals(monster2_2)) {
           //System.out.println(label.getIcon());
            label.setIcon(bubble2);
        }
        // 몬스터 3 이미지 전환
        else if (icon.equals(monster3_1) || icon.equals(monster3_2)) {
           //System.out.println(label.getIcon());
           label.setIcon(bubble3);
        }
     // 몬스터 4 이미지 전환
        else if (icon.equals(monster4_1) || icon.equals(monster4_2)) {
           //System.out.println(label.getIcon());
           label.setIcon(bubble4);
           
        }
        else if (icon.equals(timeItem1_1) || icon.equals(timeItem1_2)) {
           label.setIcon(timeBubble);
        }
        else if (icon.equals(heartItem1_1) || icon.equals(heartItem1_2)) {
           label.setIcon(heartBubble);
          }
        else if (icon.equals(defenseItem1_1) || icon.equals(defenseItem1_2)) {
           label.setIcon(defenseBubble);
       }
   }
   
   private void increaseTime() {
       // 여기서 시간을 관리하는 변수에 15초 추가
       time += 15;
       scorePanel.gameTime(time); // ScorePanel에 시간 업데이트
   }
   
   
   // 상단에 도달했는지 확인
   private boolean moveLabelToTop(JLabel label) {
       ImageIcon icon = (ImageIcon) label.getIcon();
       int newY = label.getY() - 8; // y값을 줄여서 위로 이동
       if (newY <= 5) { // 상단에 도달하면
           updateBubblePopImage(label);
       }
       if (newY <= 0) { // 상단에 도달하면
           return true; // 제거 대상으로 표시
       } else {
           label.setLocation(label.getX(), newY); // 위치 업데이트
           repaint();
           return false;
       }
   }
   

   // 버블팝 이미지 업데이트
   private void updateBubblePopImage(JLabel label) {
      soundEffects.bubblePopAudio();
       ImageIcon icon = (ImageIcon) label.getIcon();
       if (icon.equals(bubble1)) {
           label.setIcon(bubblePop1);
       } else if (icon.equals(bubble2)) {
           label.setIcon(bubblePop2);
       } else if (icon.equals(bubble3)) {
           label.setIcon(bubblePop3);
       } else if (icon.equals(bubble4)) {
           label.setIcon(bubblePop4); 
       } else if (icon.equals(timeBubble)) {
           label.setIcon(timeBubblePop);
       }else if (icon.equals(heartBubble)) {
           label.setIcon(heartBubblePop);
       }else if (icon.equals(defenseBubble)) {
           label.setIcon(defenseBubblePop);
       }
       repaint();
   }
   //private 

/******************무적 함수**********************************/
   private void activateInvincibility() {
       isInvincible = true;
       invincibleCount = 2;  // 예를 들어 2회 무적으로 설정
   }

   private void deactivateInvincibility() {
       isInvincible = false;
   }

   // defenseItem을 맞췄을 때 호출될 메소드
   private void processDefenseItem() {
       activateInvincibility();
       // 필요하다면 무적 상태를 나타내는 효과음 또는 시각적 피드백 추가
   }

   // 몬스터 충돌 처리 메소드
   private void processMonsterCollision(JLabel label) {
       if (isInvincible) {
           invincibleCount--;
           if (invincibleCount <= 0) {
               deactivateInvincibility();
           }
       } else {
           // 기존의 하트 감소 로직
           scorePanel.loseHalfLife();
       }
   }

/************무적 함수 끝**************************************/


   // removeLabel 제거후 점수 증가
   private void removeLabel(JLabel label) {
       ImageIcon icon = (ImageIcon) label.getIcon();
       // 몬스터 1 이미지 전환
       if (icon.equals(bubblePop1)) {
          System.out.println(label.getIcon());
           scorePanel.increase(10); // 점수 업데이트
       }
       // 몬스터 2 이미지 전환
       else if (icon.equals(bubblePop2)) {
          //System.out.println(label.getIcon());
           scorePanel.increase(40); // 점수 업데이트
       }
       // 몬스터 3 이미지 전환
       else if (icon.equals(bubblePop3)) {
          System.out.println(label.getIcon());
          scorePanel.increase(100); // 점수 업데이트
          processMonster3();
       }
       // 몬스터 4 (몬스터 정지)
       else if (icon.equals(bubblePop4)) {
          //generationTime = 500;
           scorePanel.increase(100); // 점수 업데이트
           
           isPaused = true; // 일시정지 상태
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(5000); // 5초 동안 대기
                        isPaused = false; // 플래그를 다시 false로 설정
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
       }
       else if (icon.equals(timeBubblePop)) {  
          soundEffects.increaseTimeAudio();
          increaseTime();
       }
       else if (icon.equals(heartBubblePop)) {
          soundEffects.newLifeAudio();
          scorePanel.increaseLife();
       }
       else if (icon.equals(defenseBubblePop)) {
          /***************/
          //여기에 한번 맞아도 무적? 여기에서 
         activateInvincibility();
           
       }
       
       
       
       System.out.println(label.getIcon());
       this.remove(label);
       movingLabels.remove(label); // 라벨 제거
       repaint();
   }
   
   // 몬스터 3 처치 시 모든 몬스터 bubbled
   private void processMonster3() {
       Vector<JLabel> toMove = new Vector<>(); // 이동할 라벨을 저장할 임시 벡터

       synchronized (monsterVector) {
           for (JLabel label : monsterVector) {
               ImageIcon icon = (ImageIcon) label.getIcon();
               if (!icon.equals(bubblePop1) && !icon.equals(bubblePop2) && !icon.equals(bubblePop3)&& !icon.equals(bubblePop4)&& !icon.equals(timeBubblePop)&& !icon.equals(heartBubblePop)&& !icon.equals(defenseBubblePop)) {
                   toggleBubbleImage(label);
                   toMove.add(label); // 이동할 라벨 추가
               }
           }

           // 이동할 라벨을 처리
           for (JLabel label : toMove) {
               movingLabels.add(label); // movingLabels에 추가
               monsterVector.remove(label); // targetVector에서 제거
           }
       }
   }
   
   @Override //게임 배경화면을 출력하기 위해 사용
   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(groundImg,0,0,this.getWidth(),this.getHeight(), this);
      setOpaque(false);
   }
   

   public void gameStart() {
      
      // 단어생성 시작
      generateWordThread.start();
      // 단어 떨어뜨리기 시작
      dropWordThread.start();
      // 땅에 닿은 단어 감지 시작
      detectBottomThread.start();
      
      checkLabelPositionThread.start();
      //soundEffects.loadAudio();
      timeThread.start(); // 시간 추적 스레드 시작x
      
   }
   
   public void gameOver() { // 게임종료
      // 단어생성 중단
      generateWordThread.interrupt();
      // 단어 떨어뜨리기 중단
      dropWordThread.interrupt();
      // 땅에 닿은 단어 감지 중단
      detectBottomThread.interrupt();
      timeThread.interrupt(); // 시간 추적 스레드 중단
      CheckLabelPositionThread.interrupted();
      
   
      soundEffects.closeAudio();
      
      /**************00000000000000000000000000000000000000000000000000********/

      
      /**************00000000000000000000000000000000000000000000000000********/
      
      try {
          Scoreboard scoreboard = new Scoreboard();

          // scorePanel에서 플레이어 ID를 가져오기
          String playerId = scorePanel.getPlayerId();
          scoreboard.updateScore(playerId, scorePanel.getScore());

          // 점수판 창 열기
          new ScoreboardFrame(scoreboard).setVisible(true);
      } catch (IOException e) {
          e.printStackTrace(); // 에러 처리
      }
      
   }
    
   class TimeThread extends Thread {

       public int getTime() {
           return time;
       }

       synchronized public void run() {
           while (time > 0) {
               try {
                  time--;  // 시간 감소
                   scorePanel.gameTime(time);  // 시간 업데이트
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   break;
               }
           }
           if (time == 0) {
               // 시간이 0에 도달했을 때의 처리 (예: 게임 종료 로직)
               System.out.println("시간 만료");
               gameOver(); // 게임 종료 메서드 호출 (필요한 경우)
           }
       }
   }
   /************0000000000000000000000000000000000000000000000000***************/
   // 단어 생성하는 스레드
      public class GenerateWordThread extends Thread{
         
         private Vector<JLabel>targetVector = null;
         
         // 단어 가져와 Label설정, 부착하는 메소드
         synchronized void generateWord() {
            
            JLabel targetLabel;
              String newWord = textSource.next();
              double rand = Math.random();

              if (rand < 0.1) { // 5% 확률로 몬스터 2 생성
                  targetLabel = new JLabel(newWord, monster2_1, SwingConstants.CENTER);
              } else if (rand < 0.2) { // 추가 5% 확률로 몬스터 3 생성
                  targetLabel = new JLabel(newWord, monster3_1, SwingConstants.CENTER);
              } else if (rand < 0.3) { // 추가 5% 확률로 몬스터 4 생성
                  targetLabel = new JLabel(newWord, monster4_1, SwingConstants.CENTER);
              } else if (rand < 0.4) { // 추가 5% 확률로 시간아이템 생성
                  targetLabel = new JLabel(newWord, timeItem1_1, SwingConstants.CENTER);
              } else if (rand < 0.6) { // 추가 5% 확률로 하트 아이템
                  targetLabel = new JLabel(newWord, heartItem1_1, SwingConstants.CENTER);
              }else if (rand < 0.9) { // 추가 5% 확률로 방패 아이템
                  targetLabel = new JLabel(newWord, defenseItem1_1, SwingConstants.CENTER);
              }else { // 나머지 확률로 일반 몬스터 생성
                  targetLabel = new JLabel(newWord, monster1_1, SwingConstants.CENTER);
              }
            
            targetLabel.setVerticalTextPosition(SwingConstants.BOTTOM); //사진과 레이블 정렬
              targetLabel.setHorizontalTextPosition(SwingConstants.CENTER); //사진과 레이블 정렬
              targetLabel.setSize(120, 80); // 크기 조정
            
            targetLabel.setFont(new Font("Dialog", 1, 10));
            targetLabel.setForeground(Color.WHITE);
            
            labelImageState.put(targetLabel, true);
            
            // x 좌표
            // y 좌표 랜덤 설정
            int startX = 700;//GameGround.this.getWidth() - targetLabel.getWidth();
             int startY = (int) (Math.random() * (GameGround.this.getHeight() * 3 / 4));   
            targetLabel.setLocation(startX,startY);
            
            targetLabel.setOpaque(false); // 배경 투명하게
            targetVector.addElement(targetLabel); // targetVector에 생성한 newWord 추가
            GameGround.this.add(targetLabel);
         }
         
         public GenerateWordThread(Vector<JLabel>targetVector) {
            this.targetVector = targetVector;
            
         }
         
         @Override
         public void run() {
            while(true) {
               generationTime = 500;
               generateWord();
               GameGround.this.repaint();
               try {
                  sleep(generationTime);
               } catch (InterruptedException e) {
                  return;
               }
              
            } 
         } 
      }
      
      
      /*******************************************************************/
      
      // 계속해서 위치를 파악,
      public class CheckLabelPositionThread extends Thread {
          @Override
          public void run() {
              while (!Thread.interrupted()) {
                  try {
                      sleep(100); // 적당한 간격으로 대기

                      // 제거할 라벨을 저장할 임시 벡터
                      Vector<JLabel> toRemove = new Vector<>();

                      synchronized (movingLabels) {
                          for (JLabel label : new Vector<>(movingLabels)) { // 반복 중 수정 문제를 피하기 위해 복사본을 생성
                              if (moveLabelToTop(label)) {
                                  toRemove.add(label);
                              }
                          }
                      }

                      // 제거할 라벨 처리
                      synchronized (movingLabels) {
                          for (JLabel label : toRemove) {
                              removeLabel(label);
                          }
                      }

                  } catch (InterruptedException e) {
                      Thread.currentThread().interrupt(); // 스레드 중단 처리
                  }
              }
          }
      }


      /*******************************************************************/
      
      // 단어를 왼쪽으로 움직이는 스레드
      public class DropWordThread extends Thread {
          private Vector<JLabel> targetVector = null;

          public DropWordThread(Vector<JLabel> targetVector) {
              this.targetVector = targetVector;
          }

          // 조건에 맞게 몬스터를 왼쪽 중앙으로 움직임
          synchronized void dropWord() {
              synchronized (targetVector) { // targetVector에 대한 접근을 동기화
                  Vector<JLabel> toRemove = new Vector<>(); // 제거할 라벨을 저장할 임시 벡터
                  if (isPaused) {
                      try {
                          Thread.sleep(100);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                  } else {
                      for (JLabel label : targetVector) {
                          ImageIcon icon = (ImageIcon) label.getIcon();

                          if (icon.equals(monster1_1) || icon.equals(monster1_2)) {
                              speed = 6;
                          } else if (icon.equals(monster2_1) || icon.equals(monster2_2)) {
                              speed = 7;
                          } else if (icon.equals(monster3_1) || icon.equals(monster3_2)) {
                              speed = 9;
                          } else if (icon.equals(monster4_1) || icon.equals(monster4_2)) {
                              speed = 9;
                          } else if (icon.equals(timeItem1_1) || icon.equals(timeItem1_2)) {
                              speed = 5;
                          } else if (icon.equals(heartItem1_1) || icon.equals(heartItem1_2)) {
                              speed = 5;
                          }else if (icon.equals(defenseItem1_1) || icon.equals(defenseItem1_2)) {
                              speed = 5;
                          }
                          
                          if(scorePanel.getScore()<50) {
                             levelSpeed = 0;
                          }
                          else if(scorePanel.getScore()<100) {
                             levelSpeed = 5;
                          }
                          else if(scorePanel.getScore()<150) {
                             levelSpeed = 10;
                          }
                          else if(scorePanel.getScore()<200) {
                             levelSpeed = 15;
                          }
                          else {
                             levelSpeed = 15;
                          }

                          // 왼쪽으로 이동
                          int newX = label.getX() - speed - levelSpeed;
                          int newY = label.getY();

                          // 몬스터만 중앙에 도달했을 때 y축 이동
                          if ((icon.equals(monster1_1) || icon.equals(monster1_2) || 
                               icon.equals(monster2_1) || icon.equals(monster2_2) ||
                               icon.equals(monster3_1) || icon.equals(monster3_2) ||
                               icon.equals(monster4_1) || icon.equals(monster4_2)) && 
                              newX < GameGround.this.getWidth() / 2) {
                              newY += (newY < GameGround.this.getHeight() / 2) ? 5 : -5;
                          }


                          label.setLocation(newX, newY);

                          // 이미지 전환 로직
                          toggleMonsterImage(label);
                      }
                      for (JLabel label : toRemove) {
                          targetVector.remove(label); // 반복 외부에서 컬렉션 수정
                      }

                      GameGround.this.repaint();
                  }
              }
          }
         
          private void toggleMonsterImage(JLabel label) {
              ImageIcon icon = (ImageIcon) label.getIcon();
              // 몬스터 1 이미지 전환
              if (icon.equals(monster1_1) || icon.equals(monster1_2)) {
                  label.setIcon(icon.equals(monster1_1) ? monster1_2 : monster1_1);
              }
              // 몬스터 2 이미지 전환
              else if (icon.equals(monster2_1) || icon.equals(monster2_2)) {
                  label.setIcon(icon.equals(monster2_1) ? monster2_2 : monster2_1);
              }
              // 몬스터 3 이미지 전환
              else if (icon.equals(monster3_1) || icon.equals(monster3_2)) {
                  label.setIcon(icon.equals(monster3_1) ? monster3_2 : monster3_1);
              }
              // 몬스터 4 이미지 전환
              else if (icon.equals(monster4_1) || icon.equals(monster4_2)) {
                  label.setIcon(icon.equals(monster4_1) ? monster4_2 : monster4_1);
              }
              // 시간아이템 이미지 전환
              else if (icon.equals(timeItem1_1) || icon.equals(timeItem1_2)) {
                  label.setIcon(icon.equals(timeItem1_1) ? timeItem1_2 : timeItem1_1);
              }
              else if (icon.equals(heartItem1_1) || icon.equals(heartItem1_2)) {
                  label.setIcon(icon.equals(heartItem1_1) ? heartItem1_2 : heartItem1_1);
              }
              else if (icon.equals(defenseItem1_1) || icon.equals(defenseItem1_2)) {
                  label.setIcon(icon.equals(defenseItem1_1) ? defenseItem1_2 : defenseItem1_1);
              }
          }

          // Vector에 들어있는 모든 JLabel들의 x좌표 감소
          @Override
          public void run() {
              while (true) {
                  int dropTime = 200;
                  dropWord();
                  GameGround.this.repaint();
                  try {
                      sleep(dropTime);
                  } catch (InterruptedException e) {
                      return;
                  }
              } 
          } 
      } 

      /**********0000000000000000[[[ 왼쪽 끝까지 확인하는 스레드 ]]]]]000000000000000000000000**************/      
      //왼쪽끝에까지 왔는지 확인하는 스레드
      public class DetectLeftThread extends Thread {
         
         private Vector<JLabel>targetVector = null;
         
         public DetectLeftThread(Vector<JLabel>targetVector) {
            this.targetVector = targetVector;
         }
         
         @Override
         public void run() {
            while(true) {
               try {
                  sleep(1);
                  for(int i=0; i<targetVector.size(); i++) {
                     JLabel label = targetVector.get(i); // 현재 라벨을 가져옴
                     // 바닥에 닿은 단어 구하기 위함
                     int x = ((JLabel)targetVector.get(i)).getX();
                     if (x < 35) {
                        ImageIcon icon = (ImageIcon) label.getIcon();
                      
                         if (icon.equals(monster1_1) || icon.equals(monster1_2)) {
                        	 onMonsterReachEnd();
                            scorePanel.decrease(5);
                            processMonsterCollision(label); //무적 상태인지 확인, 
                            //scorePanel.loseHalfLife();
                             }
                             else if (icon.equals(monster2_1) || icon.equals(monster2_2)) {
                               onMonsterReachEnd(); // 플레이어 이미지 변경을 위해 
                                scorePanel.decrease(10);
                                processMonsterCollision(label); //무적 상태인지 확인, 
                                //scorePanel.loseHalfLife();
                             }
                             else if (icon.equals(monster3_1) || icon.equals(monster3_2)) {
                               onMonsterReachEnd(); // 플레이어 이미지 변경을 위해 
                                scorePanel.decrease(15);
                                processMonsterCollision(label); //무적 상태인지 확인, 
                                //scorePanel.loseHalfLife();
                             }
                             else if (icon.equals(monster4_1) || icon.equals(monster4_2)) {
                               onMonsterReachEnd(); // 플레이어 이미지 변경을 위해 
                               scorePanel.decrease(20);
                                processMonsterCollision(label); //무적 상태인지 확인, 
                                //scorePanel.loseHalfLife();
                             }
                             else if (icon.equals(timeItem1_1) || icon.equals(timeItem1_2)) {
                                scorePanel.decrease(0); //변화 없음
                             }
                             else if (icon.equals(heartItem1_1) || icon.equals(heartItem1_2)) {
                                scorePanel.decrease(0); //변화 없음
                             }
                             else if (icon.equals(defenseItem1_1) || icon.equals(defenseItem1_2)) {
                                scorePanel.decrease(0); //변화 없음
                             }
                        

                        repaint();
                        System.out.println(targetVector.get(i).getText() + " 실패");
                        
                        // 생명이 다 떨어지면 종료
//                        boolean isGameOver = scorePanel.isGameOver();
//                        if(isGameOver == true) { // 모든스레드 종료
//                           gameOver();
//                        }
                        
                        // 게임이 종료되지 않을 경우 패널에서 라벨 제거 게임 계속됨
                        GameGround.this.remove(targetVector.get(i)); // 패널에서 라벨 떼기
                        targetVector.remove(i); // targetVector에서 삭제
                     }
                  }
               } catch (InterruptedException e) {
                  return;
               }
            } 
         } 
      }
      
      
      // 몬스터가 왼쪽 끝에 닿았을 때 호출되는 메소드
      private void onMonsterReachEnd() {
          new PlayerImageChangeThread().start(); // 플레이어 이미지 변경 스레드 시작
      }

      /**********0000000000000000[[[ 왼쪽 끝까지 확인하는 스레드 끝 ]]]]]000000000000000000000000**************/ 
      
      /**********0000000000000000[[[ 플레이어 이미지용 스레드  ]]]]]000000000000000000000000**************/ 

      // 플레이어 이미지 변경 스레드
      private class PlayerImageChangeThread extends Thread {
           @Override
           public void run() {
               System.out.println(selectedOption);
               if (selectedOption.equals("P1")) {
                   playerLabel.setIcon(playerIcon1_2); // p1 선택 시 playerIcon1_2 설정
                   try {
                       Thread.sleep(2000); // 2초 대기
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   playerLabel.setIcon(playerIcon1_1); // 원래 이미지로 복귀
               } else if (selectedOption.equals("P2")) {
                   playerLabel.setIcon(playerIcon2_2); // p2 선택 시 playerIcon2_2 설정
                   try {
                       Thread.sleep(2000); // 2초 대기
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
                   playerLabel.setIcon(playerIcon2_1); // 원래 이미지로 복귀
               }
           }
       }


      
      /**********0000000000000000[[[ 플레이어 이미지용 스레드  ]]]]]000000000000000000000000**************/ 
      
      
    
      
}