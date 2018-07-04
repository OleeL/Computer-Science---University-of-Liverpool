//Name       = Oliver Legg
//Student ID = 201244658
//Email      = O.Legg@student.liverpool.ac.uk

//importing all required modules for creating the GUI
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

//This extends JFrame so the class is a frame when it's implemented.
//It implements actionlistener so the buttons can press
public class Vending extends JFrame implements ActionListener
{
	private int      width      = 800; //width of the window
	private int      height     = 350; //height of the window
	private int      insets     = 2;   //distance between objects in the GUI
	public JButton[] buttons    = new JButton[9]; //creates the list of buttons. All of the purchasing candy buttons will be here
	public JLabel[]  labels     = new JLabel[9];  //All of the purchasing candy labels will be here.
	public Candy[]   candy_list = new Candy[9];   //All of the can
	private String   pound      = "\u00a3";       //The pound uni code
	private GridBagConstraints gbc        = new GridBagConstraints(); //this is used to format the code
	private VendorInformation  vendorInfo = new VendorInformation(this); //this creates the vendor info panel

	public Vending() 
	{
		super("Twisty JigglyBomb surprises");    //the name of the frame
		vendorInfo.setVisible(false);            //sets the vendor info frame not visible
		setResizable(false);                     //This doesn't allow the user to change the window size
		setDefaultCloseOperation(EXIT_ON_CLOSE); //closes the whole entire program if the user tries to close the main frame.
		JPanel top    = new JPanel(new GridBagLayout()); //segments the top half of the frame. Buttons will be organised here
		JPanel bottom = new JPanel(new GridBagLayout()); //segments bottom half for the vendor button.
		candy_list[0] = new Candy("Chocolate",      "Jigglypuffs",  1.30, 4); //creates candy candy(prefix, suffix, cost, stock)
		candy_list[1] = new Candy("Caramel",        "Jigglypuffs",  1.30, 4);
		candy_list[2] = new Candy("French Vanilla", "Jigglypuffs",  1.30, 4);
		candy_list[3] = new Candy("Chocolate",      "Bombs",        1,    4);
		candy_list[4] = new Candy("Caramel",        "Bombs",        1,    4);
		candy_list[5] = new Candy("French Vanilla", "Bombs",        1,    4);
		candy_list[6] = new Candy("Chocolate",      "Twists",       0.80, 4);
		candy_list[7] = new Candy("Caramel",        "Twists",       0.80, 4);
		candy_list[8] = new Candy("French Vanilla", "Twists",       0.80, 4);
		top.setLayout(new GridBagLayout()); //makes top panel grid bag formatting
		String text, cost, stock; 			
		int x = 0;
		int y = 0;
		gbc.insets = new Insets(insets,insets,insets,insets); //distance between objects in the GUI

		//this loop sets the names, colours and x, y coordinates of the buttons in the GUI
		for (int i = 0; i < buttons.length; i++){  
			text  = candy_list[i].getName();
			stock = candy_list[i].getStock()+" left";
			cost  = " ("+pound+String.format("%.2f",candy_list[i].getCost())+")";
			buttons[i] = new JButton(text+cost);
			buttons[i].setPreferredSize(new Dimension(257, 30)); //sets the size of the button (width, height)
			if (candy_list[i].getPrefix() == "Chocolate"){  	 //this is because black text is hard to see on brown
				buttons[i].setBackground(new Color(102, 51, 0));
				buttons[i].setForeground(Color.WHITE);
			}
			if (candy_list[i].getPrefix() == "Caramel")
				buttons[i].setBackground(new Color(153, 102, 0));
			if (candy_list[i].getPrefix() == "French Vanilla")
				buttons[i].setBackground(new Color(255, 255, 100));
			labels[i] = new JLabel(stock); //shows the stock of the button
			if (i % 3 == 0){ //after 3 buttons have been placed, increase the x coordinate
				x++;
				y = 0; //restart the y back to 0 to start button stack again
			}
			gbc.gridx = x;
			y++;
			gbc.gridy = y;
			top.add(buttons[i], gbc);
			buttons[i].addActionListener(this); //puts the action listener to the button. When you press the button, actionPerformed(e) triggers
			y++;
			gbc.gridy = y;
			top.add(labels[i],  gbc); //adds the button to the panael
		}
		JButton b = new JButton("Vendor Information"); //creates the vendor information button.
		b.setPreferredSize(new Dimension(257, 30)); //sets the size of the button
		b.setBackground(Color.MAGENTA);
		b.addActionListener(this);						
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1.0; //requests any extra vertical space
		gbc.anchor = GridBagConstraints.PAGE_END; //bottom of space
		bottom.add(b, gbc); //adds the button to the bottom panel
		add(top, BorderLayout.NORTH); //adds the panels to the frame
		add(bottom, BorderLayout.EAST);
		setBounds(0, 0, width, height); //sets the width and height of the frame
		getContentPane().setBackground(Color.PINK); //sets the background colour
		top.setBackground(Color.PINK);
		bottom.setBackground(Color.PINK);
		setVisible(true); //makes the frame visible
		setLocation(0, 70); //sets the location of the frame so the vendor information frame isn't on top of this vending frame
	}

	//runs when a button is pressed
    public void actionPerformed(ActionEvent e) {
    	//loops over all of the buttons
    	for (int i = 0; i < candy_list.length; i++){
        	if (e.getSource() == buttons[i]){ //checks if a specific button is pressed.
        		if (candy_list[i].getStock() > 0) 
        		{
        			//decreases the stock and updates GUI 
        			candy_list[i].consume();
        			labels[i].setText(candy_list[i].getStock()+" left");
					if (candy_list[i].getStock() <= 0){
						labels[i].setForeground(Color.RED);
						buttons[i].setForeground(Color.RED);
					}
		    		vendorInfo.update_text();
					vendorInfo.repaint();
        		}
        		else //the button has been pressed even though there is no stock
        		{
        			JOptionPane.showMessageDialog(null, "Oops. there are none left!"); //creates an information box
        		}
        	}
        }
        //if the vendor information button is pressed then create the vendor info frame.
    	if (e.getActionCommand().equals("Vendor Information"))
    	{
    		vendorInfo.update_text();
			vendorInfo.repaint();
			vendorInfo.pack();
    		vendorInfo.setVisible(true);
    	}
    }

	public static void main(String[] args)
	{
		new Vending(); //opens the GUI
	}
}