import java.io.*;
import java.util.*;

public class Scoreboard {
    private ArrayList<PlayerScore> scores = new ArrayList<>();

    public static class PlayerScore {
        public String playerId;
        public int score;

        public PlayerScore(String playerId, int score) {
            this.playerId = playerId;
            this.score = score;
        }
    }

    public void loadScores() throws IOException {
        File file = new File("topRank.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        scores.add(new PlayerScore(parts[0], Integer.parseInt(parts[1])));
                    }
                }
            }
        }
    }

    public void updateScore(String playerId, int score) throws IOException {
        scores.add(new PlayerScore(playerId, score));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("topRank.txt", true))) {
            writer.write(playerId + "," + score);
            writer.newLine();
        }
    }

    public ArrayList<PlayerScore> getTopScores(int topN) throws IOException {
        loadScores();
        simpleSortScores(); // 점수를 정렬하는 메소드

        ArrayList<PlayerScore> topScores = new ArrayList<>();
        for (int i = 0; i < Math.min(scores.size(), topN); i++) {
            topScores.add(scores.get(i));
        }
        return topScores;
    }

    // 정렬
    private void simpleSortScores() {
        for (int i = 0; i < scores.size(); i++) {
            for (int j = i + 1; j < scores.size(); j++) {
                if (scores.get(i).score < scores.get(j).score) {
                    PlayerScore temp = scores.get(i);
                    scores.set(i, scores.get(j));
                    scores.set(j, temp);
                }
            }
        }
    }
}
