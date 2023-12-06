import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
	private MainPanel mainPanel = null; 
	private JMenu helpMenu = new JMenu();
	private JMenuItem helpItem = new JMenuItem("게임 방법");
	private ImageIcon helpIcon = new ImageIcon("toolHelp.png");

	public MainFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("buble boble");
		setSize(800,600);
		makeMenu();
		mainPanel = new MainPanel();
		getContentPane().add(mainPanel);
		setResizable(false);
		setVisible(true);
	}
	
	private void makeMenu() {
		JMenuBar menuB = new JMenuBar();
		this.setJMenuBar(menuB); //add가 아니라 set인 이유= 메뉴바가 한개라서
		helpMenu.setIcon(helpIcon);
		helpMenu.add(helpItem);
		menuB.add(helpMenu);
		
		helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, 
                                              "추억의 버블보블입니다.\n 각각의 몬스터 마다 능력이 다 다릅니다.\n"
                                              + " 텍스트를 맞춰 점수를 높게 얻으세요!", 
                                              "게임 정보", 
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        });
		
		//분리 선그리기
		helpMenu.addSeparator();
				JMenuItem exitItem = new JMenuItem("Exit");
				exitItem.addActionListener(new ActionListener() { // Listener for exit item
				    @Override
				    public void actionPerformed(ActionEvent e) {
				      
				        int result = JOptionPane.showConfirmDialog(MainFrame.this, 
				                                                     "정말 종료하시겠습니까?",
				                                                     "종료 확인", 
				                                                     JOptionPane.YES_NO_OPTION,
				                                                     JOptionPane.ERROR_MESSAGE); 
				        if (result == JOptionPane.YES_OPTION) {
				            System.exit(0);
				        }
				    }
				});
				helpMenu.add(exitItem);
				menuB.add(helpMenu);
		
		
	}

}
