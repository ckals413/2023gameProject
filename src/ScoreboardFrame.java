import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class ScoreBoardFrame extends JFrame {
    public ScoreBoardFrame(Scoreboard scoreboard) throws IOException {
        setTitle("점수판");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

      
        BackgroundPanel panel = new BackgroundPanel("topRankBackGround.png");
        panel.setLayout(null);

        ArrayList<Scoreboard.PlayerScore> topScores = scoreboard.getTopScores(10);
        
        ImageIcon scoreButtonIcon = new ImageIcon("scoreButton1.png");
        JLabel scoreButtonLabel = new JLabel(scoreButtonIcon);
        scoreButtonLabel.setBounds(100, 30, scoreButtonIcon.getIconWidth(), scoreButtonIcon.getIconHeight());
        panel.add(scoreButtonLabel);

        
        JLabel infoIdLabel = new JLabel("ID");
        infoIdLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        infoIdLabel.setBounds(112, 80, 100, 30); // x, y, width, height 설정
        infoIdLabel.setForeground(Color.yellow);
        panel.add(infoIdLabel);
        
        
        JLabel infoScoreLabel = new JLabel("SCORE");
        infoScoreLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        infoScoreLabel.setBounds(217, 80, 100, 30); // x, y, width, height 설정
        infoScoreLabel.setForeground(Color.yellow);
        panel.add(infoScoreLabel);
        
        int yPos = 125; // 초기 y 위치 설정
        for (Scoreboard.PlayerScore ps : topScores) {
            JLabel idLabel = createLabel(ps.playerId, 112, yPos);
            JLabel scoreLabel = createLabel(String.valueOf(ps.score), 235, yPos);
            panel.add(idLabel);
            panel.add(scoreLabel);

            yPos += 40; // 다음 라벨의 y 위치를 증가
        }

        add(panel);
        
        // 창이 닫힐 때의 리스너 추가,윈도우 어뎁터 
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                
            }
        });
        
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(Color.white); 
        label.setBounds(x, y, 200, 30);
        return label;
    }

    //배경 이미지 패널
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String fileName) {
            backgroundImage = new ImageIcon(fileName).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
    
    
}
