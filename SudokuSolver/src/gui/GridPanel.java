package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import model.Grid;
import model.SudokuSolver;

class GridPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	final private int x = 29,y=17,w=288,h=270;//grid parameters
	public int highlightedCell=1;
	private byte[][] grid = new byte[9][9];
	private byte[][] solvedGrid=new byte[9][9];
	private byte[][] unsolvedGrid=new byte[9][9];
	private String time="";
	
	@Override 
	public void paint(Graphics g) {
		
		super.paint(g);
		g.setColor(Color.white);
		g.fillRect(x,y,w,h);
		g.setColor(Color.black);
		g.drawRect(x+1,y+1,w+1,h+1);
		for(int i=0;i<10;i++){  //draw grid
			g.drawLine((w/9)*i+x,y,(w/9)*i+x,y+h);
			g.drawLine(x,(h/9)*i+y,x+w,(h/9)*i+y);
			if(i%3==0){
				g.drawLine((w/9)*i+x+1,y,(w/9)*i+x+1,y+h);
				g.drawLine(x,(h/9)*i+y+1,x+w,(h/9)*i+y+1);
			}
		}
		
		for(int i=0;i<81;i++){  //fill grid
			g.setFont(new Font(null,Font.PLAIN,17));
			if(grid[i/9][i%9]!=0)
				g.drawString(grid[i/9][i%9]+"", (w/9)*(i%9)+x+12, (h/9)*(i/9)+y+23);
			g.setFont(new Font(null,Font.BOLD,17));
			if(unsolvedGrid[i/9][i%9]!=0)
				g.drawString(grid[i/9][i%9]+"", (w/9)*(i%9)+x+12, (h/9)*(i/9)+y+23);
			
		}
		highlightCell(g);
		g.setFont(new Font(null,Font.BOLD,12));
		g.drawString(time, 207, y+h+17);
		repaint();
	}

	public void about(JFrame f,boolean disp) {	
		URL imageURL = ClassLoader.getSystemResource("about.jpg");
		final Image image = Toolkit.getDefaultToolkit().getImage(imageURL);
		final JDialog d = new JDialog(f){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				g.drawImage(image,0,0,null);
			}
		};
		d.setVisible(false);
		JButton b = new JButton("OK");
		b.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				d.dispose();
			}
			
		});
		 d.setTitle("About");
		 try {
			d.setIconImage(ImageIO.read(new File("icon.jpg")));
		} catch (IOException e) {
		}
		 d.setSize(300,200);
         d.setLocationRelativeTo(f);
         JPanel p = new JPanel();
         p.add(b,BorderLayout.EAST);
         d.add(p,BorderLayout.AFTER_LAST_LINE);
         d.setVisible(true);
         if(!disp) d.dispose();
	}

	//sets highlightedCell with click x,y coordinates
	public void setHighlightedCell(int x2, int y2) {
		y2-=48;
		x2-=3;
		for(int i=0;i<81;i++){
			int xPos = (w/9)*(i%9)+x;
			int yPos = (h/9)*(i/9)+y;
			int cellW = w/9;
			int cellH = h/9;
			if(x2>xPos+1 && x2<(xPos+1+cellW-2) && y2>yPos+1 && y2<(yPos+1+cellH-2))
					highlightedCell=i+1;
		}
		
	}

	//sets the value of highlightedCell in grid[][] array
	public void setHighlightedCell(int i) {
		if(time.equals(""))
			grid[(highlightedCell-1)/9][(highlightedCell-1)%9] = (byte)i;
	}

	public void highlightCell(Graphics g){
		int cellPosition=highlightedCell-1;
		int xPos = (w/9)*(cellPosition%9)+x;
		int yPos = (h/9)*(cellPosition/9)+y;
		int cellW = w/9;
		int cellH = h/9;
		g.drawRect(xPos+1,yPos+1,cellW-2,cellH-2);
		g.drawRect(xPos+2,yPos+2,cellW-4,cellH-4);
		//g.setColor(Color.yellow);
		//g.fillRect(xPos+3,yPos+3,cellW-5,cellH-5);
		
	}
	
	public void solve(){
		if(!time.equals("")) return;
		unsolvedGrid = grid;
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				SudokuSolver solver = new SudokuSolver();
				Grid g = new Grid(unsolvedGrid);
				Grid sg = solver.solve(g);
				if(sg!=null){
					solvedGrid = sg.getArray();
					time="Time Taken : "+(float)sg.getTime()/1000+"s";
					grid=solvedGrid;
				}
				else{
					solvedGrid = new byte[9][9];
					time = "          Invalid Puzzle";
				}
			}
			
		});
		t.isDaemon();
		t.start();
		time="                     Solving...";
			
	}
	
	public void reset(){
		grid = unsolvedGrid;
		unsolvedGrid = new byte[9][9];
		solvedGrid = new byte[9][9];
		time="";
	}
	
	public void clear(){
		unsolvedGrid = new byte[9][9];
		solvedGrid = new byte[9][9];
		grid = new byte[9][9];;
		time="";
		highlightedCell=1;
	}
	
	// saves currently displayed grid as a serialized object( an array byte[][])
	public void save(){
		JFileChooser fileChooser = new JFileChooser("Save Grid");
		fileChooser.addChoosableFileFilter(new FileFilter(){

			@Override
			public boolean accept(File arg0) {
				String name=arg0.getName();
				if(name.length()>3)
					return name.substring(name.length()-4).equals("grid");
				else return false;
			}

			@Override
			public String getDescription() {
				return "*.grid";
			}
			
		});
		fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[1]);
		JFrame jf = new JFrame("Save");
		URL imageURL = ClassLoader.getSystemResource("icon.jpg");
		jf.setIconImage(Toolkit.getDefaultToolkit().getImage(imageURL));
		fileChooser.showSaveDialog(jf);
		File f = fileChooser.getSelectedFile();
		if(f==null) return;
		f=new File(f.getAbsolutePath()+".grid");
		
		try{
			if(f.createNewFile()){
				ObjectOutputStream fOut = new ObjectOutputStream(new FileOutputStream(f));
				fOut.writeObject(grid);
				fOut.close();
			}
		}catch(IOException e){
			
		}
		
	}
	
	public void load(){
		//System.out.println("Loading");
		JFileChooser fileChooser = new JFileChooser("Load Grid");
		fileChooser.addChoosableFileFilter(new FileFilter(){

			@Override
			public boolean accept(File arg0) {
				String name=arg0.getName();
				if(name.length()>3)
					return name.substring(name.length()-4).equals("grid");
				else return false;
			}

			@Override
			public String getDescription() {
				return "*.grid";
			}
			
		});
		fileChooser.setFileFilter(fileChooser.getChoosableFileFilters()[1]);
		JFrame jf = new JFrame("Load");
		URL imageURL = ClassLoader.getSystemResource("icon.jpg");
		jf.setIconImage(Toolkit.getDefaultToolkit().getImage(imageURL));
		fileChooser.showOpenDialog(jf);
		File f=fileChooser.getSelectedFile();
		if(f==null) return;
		try{
				ObjectInputStream fIn = new ObjectInputStream(new FileInputStream(f));
				grid=(byte[][])fIn.readObject();
				fIn.close();
		}catch(Exception e){
			grid = new byte[9][9];
		}
		solvedGrid = new byte[9][9];
		unsolvedGrid = new byte[9][9];
		time="";
		highlightedCell=1;
	}
	
}
