package IFTTT;

import javax.swing.JFrame;

public class Start {
	public static void main(String[] args)throws Exception {
		MainFrame frame = new MainFrame();
		frame.setTitle("单机版IFTTT");
		frame.setSize(600,410);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
