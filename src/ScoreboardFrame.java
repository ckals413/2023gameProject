import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ScoreboardFrame extends JFrame {
    public ScoreboardFrame(Scoreboard scoreboard) throws IOException {
        setTitle("점수판");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        ArrayList<Scoreboard.PlayerScore> topScores = scoreboard.getTopScores(10);
        
        JLabel infoIdLabel = new JLabel("ID");
        infoIdLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        infoIdLabel.setBounds(15, 10, 100, 30); // x, y, width, height 설정
        panel.add(infoIdLabel);
        
        
        JLabel infoScoreLabel = new JLabel("SCORE");
        infoScoreLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        infoScoreLabel.setBounds(123, 10, 100, 30); // x, y, width, height 설정
        panel.add(infoScoreLabel);
        
        int yPos = 50; // 초기 y 위치 설정
        for (Scoreboard.PlayerScore ps : topScores) {
        	JLabel idLabel = new JLabel(ps.playerId);
            JLabel scoreLabel = new JLabel(" " + ps.score);
            idLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
            idLabel.setBounds(10, yPos, 100, 30); // x, y, width, height 설정
            scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
            scoreLabel.setBounds(120, yPos, 200, 30); // x, y, width, height 설정
            panel.add(idLabel);
            panel.add(scoreLabel);

            yPos += 40; // 다음 라벨의 y 위치를 30 (라벨 높이) + 10 (간격) 픽셀만큼 증가
        }

        JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }
}
