import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class GameFrame extends JFrame {
	  private GamePanel gamePanel;
	  private GameGround gameGround;
	  private String selectedOption;
	  private SoundEffects soundEffects = new SoundEffects();
	  
	    public GameFrame(String id, String selectedOption) {
	    this.selectedOption = selectedOption;
		setTitle("추억의 버블 보블");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1050,600);
		//makeMenu();
		makeToolbar();
		//gamePanel = new GamePanel(selectedOption);
		gamePanel = new GamePanel(selectedOption, soundEffects, this);
		gamePanel.setPlayerId(id);
		getContentPane().add(gamePanel,BorderLayout.CENTER);
		setVisible(true);
		soundEffects.loadAudio(); // 오디오 클립 로드
		
		
	}

	public void makeToolbar() {
		//기본 툴바가 움직임
		JToolBar bar = new JToolBar();
		//bar.setBackground(Color.green);
		getContentPane().add(bar,BorderLayout.NORTH);
		
//		JButton b = new JButton("Play");
//		bar.add(b);
		//이미지 버튼
//		ImageIcon normalIcon = new ImageIcon("normal.png");
//		ImageIcon rolloverIcon = new ImageIcon("rollover.png");
//		ImageIcon pressedIcon = new ImageIcon("pressed.png");
//		JButton imageBtn = new JButton(normalIcon);
//		imageBtn.setRolloverIcon(rolloverIcon);
//		imageBtn.setPressedIcon(pressedIcon);
//		bar.add(imageBtn);ImageIcon helpIcon = new ImageIcon("toolHelp.png");
		ImageIcon exitIcon = new ImageIcon("toolExit.png");
		JButton exitBtn = new JButton(exitIcon);
		exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
			      
		        int result = JOptionPane.showConfirmDialog(GameFrame.this, 
		                                                     "정말 종료하시겠습니까?",
		                                                     "종료 확인", 
		                                                     JOptionPane.YES_NO_OPTION,
		                                                     JOptionPane.ERROR_MESSAGE); 
		        if (result == JOptionPane.YES_OPTION) {
		            System.exit(0);
		        }
		    }
        });
		bar.add(exitBtn);
		
		
		ImageIcon helpIcon = new ImageIcon("toolHelp.png");
		JButton helpBtn = new JButton(helpIcon);
		helpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GameFrame.this, 
                                              "추억의 버블보블입니다.\n 각각의 몬스터 마다 능력이 다 다릅니다.\n"
                                              + " 텍스트를 맞춰 점수를 높게 얻으세요!", 
                                              "게임 정보", 
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        });
		
		bar.add(helpBtn);
		bar.addSeparator();
		bar.addSeparator();
		bar.addSeparator();

		ImageIcon soundOffIcon = new ImageIcon("toolSoundOff.png");
		JButton soundOffBtn = new JButton(soundOffIcon);
		bar.add(soundOffBtn);
		
		
		ImageIcon soundOnIcon = new ImageIcon("toolSoundOn.png");
		JButton soundOnBtn = new JButton(soundOnIcon);
		bar.add(soundOnBtn);
		
		soundOnBtn.setEnabled(false);
		
		soundOffBtn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("soundOffBtn  clicked");
	            soundOffBtn.setEnabled(false);
	            soundOnBtn.setEnabled(true);
	            soundEffects.stopAudio();
	        }
	    });
		
		soundOnBtn.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            System.out.println("soundOnBtn  clicked");
	            soundOffBtn.setEnabled(true);
	            soundOnBtn.setEnabled(false);
	            soundEffects.startAudio();
	        }
	    });
		
		
		
		
		bar.setFloatable(false);//툴바 고정
	
	}
	public void invisbleGameFrame() {
		this.setVisible(false);
	}
	

}