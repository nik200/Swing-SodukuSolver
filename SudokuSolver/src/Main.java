import gui.GridFrame;

import javax.swing.UIManager;

public class Main {
	public static void main(String[] args){
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {	}
		GridFrame frame = new GridFrame();
		frame.setVisible(true);
	}
}