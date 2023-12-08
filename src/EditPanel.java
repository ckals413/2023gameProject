import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//오른쪽 아래 패널
public class EditPanel extends JPanel{
	private JTextField wordInput;
	private JLabel saveImageLabel;
	private JButton saveButton;
	File file = new File("words.txt");
	public EditPanel() {
		setBackground(Color.cyan);
		//setLayout(null);
		saveImageLabel = new JLabel(new ImageIcon("labelSave.png"));
        add(saveImageLabel);
		wordInput = new JTextField(10);
		add(wordInput);

        saveButton = new JButton("Save");
     
		 
        saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        	saveTextToFile();
        	}
        });
        add(saveButton);
	}
	
	private void saveTextToFile() {

	    String text = wordInput.getText().trim().replace(" ", ""); //단어 양끝에 공백 삭제
	    try {
			FileWriter fout = new FileWriter(file, true);
			fout.write(text);
			fout.write("\r\n",0,2);
			fout.close();
		} catch (Exception e) {
			System.out.println("단어 추가 예외발생!");
		}
	    wordInput.setText("");
	    
	    
	}
	
	
}
