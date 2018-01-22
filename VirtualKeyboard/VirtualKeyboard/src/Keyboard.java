/*
 * Sample code for CS 2610 Homework 1
 * 
 */

import javax.swing.*;        
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

import java.awt.Color;   
import javax.swing.JButton;  
import javax.swing.JComboBox;  
import javax.swing.JFrame;  
import javax.swing.JScrollPane;  
import javax.swing.JTextPane;  
import javax.swing.text.BadLocationException;  
import javax.swing.text.Style;  
import javax.swing.text.StyleConstants; 

public class Keyboard{
	
	JFrame window;
	Key[] keys;
	JPanel board; //for loading buttons
	JPanel wordList;
	JTextField input;
	JLabel outputdisplay;
	JTextPane promptBox;
	Style style0;
    Style style1;
    Style style2;
    Style style3;
	Container panel;
	MouseListener mouselistener;
	AllPathsFromASource searchMethod = new AllPathsFromASource(5);
	private static boolean USE_CROSS_PLATFORM_UI = true;
	
	public Keyboard() throws Exception{
		if(USE_CROSS_PLATFORM_UI) {
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		mouselistener = new MouseListener();
		window = new JFrame("keyboard");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(600,800);
		
		//set the keyboard pad layout
		board = new JPanel();
		Border border = BorderFactory.createEmptyBorder(0,10,20,10);
		board.setBorder(border);
		
		wordList = new JPanel();
		Border borderW = BorderFactory.createEmptyBorder(0,10,20,10);
		wordList.setBorder(borderW);
		//infomation display
		
		input = new JTextField("ACTIONS SPEAK LOUDER THAN WORDS");
		input.setFont(new Font("Serif", Font.PLAIN, 16));
		input.setBackground(new Color(237,237,237));
		input.setEditable(false);
		Border title = BorderFactory.createTitledBorder("Input: ");
		Border bevel = BorderFactory.createLoweredBevelBorder();
		Border border1 = BorderFactory.createEmptyBorder(0,10,5,10);
		Border border2 = BorderFactory.createEmptyBorder(10,10,5,10);
		Border border3 = BorderFactory.createCompoundBorder(border2, bevel);
		Border border4 = BorderFactory.createCompoundBorder(border3, title);
		input.setBorder(BorderFactory.createCompoundBorder(border4, border1));
		
		//modify input sample border
		outputdisplay = new JLabel("_");
		Border title2 = BorderFactory.createTitledBorder("Output: ");
		Border border5 = BorderFactory.createEmptyBorder(20,10,20,10);
		Border border6 = BorderFactory.createEmptyBorder(10,9,10,9);
		Border border7 = BorderFactory.createCompoundBorder(title2,border6);
		outputdisplay.setBorder(BorderFactory.createCompoundBorder(border5,border7));
		outputdisplay.setForeground(Color.blue);
		outputdisplay.setFont(new Font("Serif", Font.PLAIN, 16));
		
		//modify border of prompt box
		promptBox = new JTextPane();
		Border title3 = BorderFactory.createTitledBorder("Hint: ");
		Border border8 = BorderFactory.createEmptyBorder(20,10,20,10);
		Border border9 = BorderFactory.createEmptyBorder(10,9,10,9);
		Border border10 = BorderFactory.createCompoundBorder(title3,border9);
		promptBox.setBorder(BorderFactory.createCompoundBorder(border8,border10));
		promptBox.setEditable(false);
		Style style = promptBox.getStyledDocument().addStyle(null, null);
		StyleConstants.setFontSize(style, 12);
		style0 = promptBox.addStyle("style0", style);
        style1 = promptBox.addStyle("style1", style);
        style2 = promptBox.addStyle("style2", style);
        style3 = promptBox.addStyle("style3", style);
        StyleConstants.setForeground(style0, Color.BLACK);
        StyleConstants.setForeground(style1, Color.RED);
        StyleConstants.setForeground(style2, Color.GREEN);
        StyleConstants.setForeground(style3, Color.BLUE);
        
        
		board.setLayout(new GridBagLayout());
		wordList.setLayout(new GridBagLayout());
		//set the buttons:
		int[] keyNum =  {5,10,9,7};
		keys = new Key[34];
		String[] keyLabels ={"QWERTYUIOP","ASDFGHJKL","ZXCVBNM"}; //change to keyboard setting
		
		//add possible words
		String[] tempLabels = {"Possible Words","","","",""};
		String label = tempLabels[0];
		keys[0] = new Key(label);
		keys[0].setName(label);
		keys[0].setFocusPainted(false);
		addKey2(wordList,keys[0],1,0,2,1);
		
		for (int i = 1; i < keyNum[0]; i++){ //first line of keys
			label = tempLabels[i];
			keys[i] = new Key(label);
			keys[i].setName(label);
			keys[i].setFocusPainted(false);
			keys[i].addMouseListener(mouselistener);
			addKey2(wordList,keys[i],2*i+1,0,2,1);
			keys[i].setVisible(false);
		}
		
		//first line
		for (int i = 0; i < keyNum[1]; i++){ //first line of keys
			label = keyLabels[0].substring(i, i+1);
			keys[i+5] = new Key(label);
			keys[i+5].setName(label);
			keys[i+5].setFocusPainted(false);
			keys[i+5].addMouseListener(mouselistener);
			keys[i+5].addMouseMotionListener(mouselistener);
			addKey(board,keys[i+5],i+1,1,1,1);
		}
		
		//second line
		for (int i = 0; i < keyNum[2]; i++){ //second line of keys
			label = keyLabels[1].substring(i, i+1);
			keys[i+15] = new Key(label);
			keys[i+15].setName(label);
			keys[i+15].setFocusPainted(false);
			keys[i+15].addMouseListener(mouselistener);
			keys[i+15].addMouseMotionListener(mouselistener);
			addKey(board,keys[i+15],i+1,2,1,1);
		}
		
		
		//third line
		for (int i = 0;i< keyNum[3]; i++){ //third line of keys
			label = keyLabels[2].substring(i, i+1);
			keys[i+24] = new Key(label);
			keys[i+24].setName(label);
			keys[i+24].setFocusPainted(false);
			keys[i+24].addMouseListener(mouselistener);
			keys[i+24].addMouseMotionListener(mouselistener);
			addKey(board,keys[i+24],i+2,3,1,1);
		}
		
		label = "Back";
		keys[32] = new Key(label);
		keys[32].setName(label);
		keys[32].setFocusPainted(false);
		keys[32].addMouseListener(mouselistener);
		addKey(board,keys[32],9,3,2,1);
		
		//set the space button
		keys[31] = new Key(" ");
		keys[31].setName(" ");
		keys[31].setFocusPainted(false);
		keys[31].addMouseListener(mouselistener);
		keys[31].addMouseMotionListener(mouselistener);
		addKey(board,keys[31],2,4,7,1);
		
		panel = window.getContentPane();
		
		label = "Del word";
		keys[33] = new Key(label);
		keys[33].setName(label);
		keys[33].setFocusPainted(false);
		keys[33].addMouseListener(mouselistener);
		addKey(board,keys[33],9,4,2,1);
		
		//use gridBag layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.anchor= GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(input,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(outputdisplay,c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(promptBox,c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridheight = 1;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5,5,5,5);
		panel.add(wordList,c);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridheight = 4;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10,10,10,10);
		panel.add(board,c);
        
		window.pack();// adjust the window size
		window.setVisible(true);
	}	
	
	public void addKey2(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.ipady = 30;
		c.ipadx = 50;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,0);
		container.add(component, c);
	}
	
	public void addKey(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = gridx;
		c.gridy = gridy;
		c.ipady = 30;
		c.ipadx = 30;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.anchor=GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,0);
		container.add(component, c);
	}
	
	private void changeKey(ArrayList<String> words){
		
		if(words.size() != 0){
			if(words.size() == 1){
				String temS = words.get(0);
				temS = temS.toUpperCase();
				String oldString = outputdisplay.getText();
				String newString = oldString.substring(0, oldString.length()-1) + temS + " " + "_";
				outputdisplay.setText(newString);
			}else{
				String temS = words.get(0);
				temS = temS.toUpperCase();
				String oldString = outputdisplay.getText();
				String newString = oldString.substring(0, oldString.length()-1) + temS + " " + "_";
				outputdisplay.setText(newString);
				int num = words.size();
				if(num > 4) num = 4;
				for(int i = 1; i < num + 1; i++){
					keys[i].setText(words.get(i-1).toUpperCase());
					keys[i].setVisible(true);
				}
				keys[0].setVisible(false);
			}
		}
	}
	
	private void recoverWordList(){
		for(int i = 1; i < 5; i++){
			keys[i].setText("");
			keys[i].setVisible(false);
		}
		keys[0].setVisible(true);
	}
	
	private void wholeCheck() throws BadLocationException{
		promptBox.setText(null);
		LevenshteinDistance checkDis = new LevenshteinDistance();
		String[] expectedS = "ACTIONS SPEAK LOUDER THAN WORDS".split(" ");
		if(outputdisplay.getText().length() != 1){
			String[] outS = outputdisplay.getText().substring(0, outputdisplay.getText().length()-1).split(" ");
			int i = 0;
			while(i < outS.length && i < expectedS.length){
			setPromptBox(checkDis.calDis(expectedS[i], outS[i]));
			promptBox.getStyledDocument().insertString(promptBox.getStyledDocument().getLength()," ", style0);
			i++;
			}
			if(outS.length > expectedS.length){
				for(int j = expectedS.length; j < outS.length; j++){
					promptBox.getStyledDocument().insertString(promptBox.getStyledDocument().getLength(),outS[j], style2);
					promptBox.getStyledDocument().insertString(promptBox.getStyledDocument().getLength()," ", style0);
				}
			}
		}
	}
	private void setPromptBox(Pair<ArrayList<Integer>, ArrayList<Character> > renew) throws BadLocationException{
//		promptBox.getStyledDocument().insertString(promptBox.getStyledDocument().getLength(),"The ", style1);
//        promptBox.getStyledDocument().insertString(promptBox.getStyledDocument().getLength(),"red ", style2);
//        promptBox.getStyledDocument().insertString(promptBox.getStyledDocument().getLength(),"apple", style3);
        String temString;
        int count = 0;
        for(int i = 0; i < renew.getKey().size(); i++){
        	if(renew.getKey().get(i) == 0){
        		temString = renew.getValue().get(count) + "";
        		promptBox.getStyledDocument().insertString(promptBox.getStyledDocument().getLength(),temString, style0);
        		count++;
        	}else if(renew.getKey().get(i) == 1){
        		temString = renew.getValue().get(count) + "";
        		promptBox.getStyledDocument().insertString(promptBox.getStyledDocument().getLength(),temString, style1);
        		count++;
        	}else if(renew.getKey().get(i) == 2){
        		temString = renew.getValue().get(count) + "";
        		promptBox.getStyledDocument().insertString(promptBox.getStyledDocument().getLength(),temString, style2);
        		count++;
        	}else if(renew.getKey().get(i) == 3){
        		temString = "(" + renew.getValue().get(count) + ")" + renew.getValue().get(count+1);
        		promptBox.getStyledDocument().insertString(promptBox.getStyledDocument().getLength(),temString, style3);
        		count += 2;
        	}
        }
	}
	
	class MouseListener extends MouseAdapter implements MouseMotionListener {
		boolean tracing;			// whether the input method is button clicking or tracing
		ArrayList<Key> tracelist;	// a list to store all buttons on the trace
		Key curKey;
		
		MouseListener() throws Exception{
			super();
			tracing = false;
			tracelist = new ArrayList<Key>();
			curKey = new Key("");
			mouseClicked(null);  //added by Xingtian Dong
		}
		
		private void updateOutput(Key theEventer){
			String theChar = theEventer.getText();
			String oldString = outputdisplay.getText();
			String newString = oldString.substring(0, oldString.length()-1) + theChar + "_";
			outputdisplay.setText(newString);
		}
		
		private void updateOutput1 (Key theEventer){
			String theChar = theEventer.getText();
			String oldString = outputdisplay.getText();
			String newString = oldString.substring(0, oldString.length()-1) + theChar + " _";
			outputdisplay.setText(newString);
		}
		
		private void deleteWord(){
			String oldString = outputdisplay.getText();
			String[] temA = oldString.split(" ");
			String newString = "";
			if(temA[temA.length - 1].length() != 1 || temA.length == 1){
				for(int i = 0; i < temA.length - 1; i++)
					newString += temA[i] + " ";
				newString += "_";
			}
			else{
				for(int i = 0; i < temA.length - 2; i++)
					newString += temA[i] + " ";
				newString += "_";
			};
			outputdisplay.setText(newString);
		}
		
		private void recoverState(){
			//when mouse is released, tracing is ended. reset the letter state in the tmptlist
			//change status
			while (!tracelist.isEmpty()){
				Key e2 = tracelist.get(tracelist.size()-1);
				e2.setBackground(new JButton().getBackground());
				
				Key e = tracelist.get(0);
				
				e.LineList.clear();
				e.PointList.clear();
				e.repaint();
				tracelist.remove(0);		
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e){
			Key theEventer = (Key) e.getSource();
			recoverWordList();
			if (tracing == false){//start tracing
				curKey = theEventer;
				tracelist.add(theEventer);
				tracing = true;
				System.out.println("Entering Mouse tracing mode");
                theEventer.PointList.add(e.getPoint());
			} else{
				Point2D p = e.getPoint();
				int x = (int)p.getX()- (curKey.getX() - theEventer.getX());
				int y = (int)p.getY()- (curKey.getY() - theEventer.getY());
				Point newPoint = new Point(x, y);
				System.out.println("Mouse position:(" + (curKey.getX() + x) + "," + (curKey.getY() + y) + "), In key " + curKey.getText() + ".");
				
				curKey.PointList.add(newPoint);
				curKey.repaint();
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e){
			
			if(e!=null){
				Key theEventer = (Key) e.getSource();
				recoverWordList();
				if(theEventer == keys[1] || theEventer == keys[2] || theEventer == keys[3] || theEventer == keys[4]){
					recoverWordList();
				}
				if(theEventer == keys[32]){
					String oldString = outputdisplay.getText();
					if(oldString.length() > 1){
						String newString = oldString.substring(0, oldString.length()-2) + "_";
						outputdisplay.setText(newString);
					}
				}
				if(theEventer == keys[33]) {
						deleteWord();
				}
			}
			// TODO Auto-generated method stub
			try {
		         // Open an audio input stream.
				File url = new File("button-3.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         clip.start();
		      } catch (Exception a) {
		         a.printStackTrace();
		      } 
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();
			if(theEventer != keys[1] && theEventer != keys[2] && theEventer != keys[3] && theEventer != keys[4] && theEventer != keys[32] && theEventer != keys[33])
			if (tracing) {
				curKey = theEventer;
				tracelist.add(theEventer);
				theEventer.setFocusPainted(true);
				
	            //start the mouse trace in this button
	            theEventer.PointList.add(e.getPoint());
	            theEventer.setBackground(Color.RED);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();
			if(theEventer != keys[1] && theEventer != keys[2] && theEventer != keys[3] && theEventer != keys[4] && theEventer != keys[32] && theEventer != keys[33])
			if(tracing){
				theEventer.setFocusPainted(false); 
                theEventer.LineList.add(theEventer.PointList);
                theEventer.PointList = new ArrayList<Point>();
                theEventer.setBackground(new JButton().getBackground());
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();
			theEventer.setFocusPainted(true);
		}
		
		@Override
		public void mouseReleased(MouseEvent e){
			// TODO Auto-generated method stub
			Key theEventer = (Key) e.getSource();//e is the same source as pressed
			theEventer.setBackground(new JButton().getBackground());
			if (tracing == false) {
				if(theEventer != keys[32] && theEventer != keys[33])
				if(theEventer == keys[1] || theEventer == keys[2] || theEventer == keys[3] || theEventer == keys[4]){
					try{
						deleteWord();
					updateOutput1(theEventer);}
					catch(Exception a){
						
					}
					recoverWordList();
				}
				else
					try{
						updateOutput(theEventer);
					}catch(Exception a){
						
					}
				System.out.println("Input key " + theEventer.getText());
			} else {
				tracing = false;
				String check = "";
				for(Key tem: tracelist)
					check += tem.getText();
				check = check.toLowerCase();
				System.out.println(check);
				changeKey(searchMethod.processInput(check,false));
				System.out.println("Tracing Completes. Clear all traces.");
				recoverState();
			}
			try{
				wholeCheck();
			}catch(Exception a){
				
			}
		}
	}
	    
	public static void main(String[] args) throws FileNotFoundException,Exception {
		Keyboard gui = new Keyboard(); 
//		Dictionary dict = new Dictionary();
	}
}
