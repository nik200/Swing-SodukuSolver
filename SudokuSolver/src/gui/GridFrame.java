package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GridFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	final GridPanel gridPanel=new GridPanel();
	GridActionListener al = new GridActionListener();
	class GridActionListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int keyCode = e.getKeyCode();
			switch(keyCode){
			case KeyEvent.VK_UP:gridPanel.highlightedCell-=9;break;
			case KeyEvent.VK_DOWN:gridPanel.highlightedCell+=9;break;
			case KeyEvent.VK_LEFT:gridPanel.highlightedCell-=1;break;
			case KeyEvent.VK_RIGHT:gridPanel.highlightedCell+=1;break;
			case KeyEvent.VK_1:gridPanel.setHighlightedCell(1);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_2:gridPanel.setHighlightedCell(2);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_3:gridPanel.setHighlightedCell(3);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_4:gridPanel.setHighlightedCell(4);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_5:gridPanel.setHighlightedCell(5);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_6:gridPanel.setHighlightedCell(6);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_7:gridPanel.setHighlightedCell(7);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_8:gridPanel.setHighlightedCell(8);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_9:gridPanel.setHighlightedCell(9);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_0:gridPanel.setHighlightedCell(0);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_SPACE:gridPanel.setHighlightedCell(0);gridPanel.highlightedCell++;break;
			case KeyEvent.VK_BACK_SPACE:gridPanel.setHighlightedCell(0);break;
			}
			if(gridPanel.highlightedCell<1) gridPanel.highlightedCell+=81;
			if(gridPanel.highlightedCell>81) gridPanel.highlightedCell-=81;
			
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			super.keyReleased(e);
		}
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			super.keyTyped(e);
		}
		
	}
	
	class GridMouseActionListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			//System.out.println(e.getX()+" "+e.getY());
			gridPanel.setHighlightedCell(e.getX(),e.getY());
			gridPanel.repaint();
		}
	}
	
	public GridFrame(){
		super("Sudoku Solver");
		this.addKeyListener(al);
		this.addMouseListener(new GridMouseActionListener());
		setSize(351,400);
		//setIconImage(ImageIO.read(new File("icon.jpg")));
		URL imageURL = ClassLoader.getSystemResource("icon.jpg");
		setIconImage(Toolkit.getDefaultToolkit().getImage(imageURL));
	
		setResizable(false);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			 public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//action listener for buttons and menus
		ActionListener printListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(arg0.getActionCommand());
				if(arg0.getActionCommand().equals("Solve")) gridPanel.solve();
				if(arg0.getActionCommand().equals("Reset")) gridPanel.reset();
				if(arg0.getActionCommand().equals("Clear")) gridPanel.clear();
				if(arg0.getActionCommand().equals("Save Grid")) gridPanel.save();
				if(arg0.getActionCommand().equals("Load Grid")) gridPanel.load();
				if(arg0.getActionCommand().equals("Exit")) System.exit(0);
				if(arg0.getActionCommand().equals("About")) gridPanel.about(GridFrame.this,true);
				//gridPanel.repaint();
			}
		};
		
		JMenuBar menuBar = new JMenuBar();
			JMenu menu1 = new JMenu("Option");
			menu1.setMnemonic(KeyEvent.VK_O);
				JMenuItem mi11 = new JMenuItem("Save Grid");
				mi11.setMnemonic(KeyEvent.VK_S);
				mi11.addActionListener(printListener);
				mi11.setAccelerator(KeyStroke.getKeyStroke(
				        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
				
				JMenuItem mi12 = new JMenuItem("Load Grid");
				mi12.setMnemonic(KeyEvent.VK_L);
				mi12.addActionListener(printListener);
				mi12.setAccelerator(KeyStroke.getKeyStroke(
				        KeyEvent.VK_L, ActionEvent.CTRL_MASK));
				
				JMenuItem mi13 = new JMenuItem("Exit");
				mi13.setMnemonic(KeyEvent.VK_X);
				mi13.addActionListener(printListener);
				mi13.setAccelerator(KeyStroke.getKeyStroke(
				        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
				
			menu1.add(mi11);
			menu1.add(mi12);
			menu1.addSeparator();
			menu1.add(mi13);
			
			
			JMenu menu2 = new JMenu("Help");
			menu2.setMnemonic(KeyEvent.VK_H);
				JMenuItem mi21 = new JMenuItem("About");
				mi21.setMnemonic(KeyEvent.VK_A);
				mi21.addActionListener(printListener);
				menu2.add(mi21);	
		menuBar.add(menu1);
		menuBar.add(menu2);
		
		JPanel buttonPanel;
			
		buttonPanel = new JPanel();
			JButton b1 = new JButton("Solve");
			b1.addActionListener(printListener);
		    b1.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");      
			b1.addKeyListener(al);
			JButton b2 = new JButton("Reset");
			b2.addActionListener(printListener);
			b2.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");  
			b2.addKeyListener(al);
			JButton b3 = new JButton("Clear");
			b3.addActionListener(printListener);
			b3.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");  
			b3.addKeyListener(al);
		buttonPanel.add(b1);
		buttonPanel.add(b2);
		buttonPanel.add(b3);
		
		this.add(menuBar,BorderLayout.NORTH);
		this.add(gridPanel);
		this.add(buttonPanel,BorderLayout.PAGE_END);
		gridPanel.about(this, false);//added because about dialog is not displayed when clicked for the
									//first time.(Dummy display of about screen)
	}

}
