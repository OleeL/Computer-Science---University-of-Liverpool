
//importing all required modules for creating the GUI
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.*;
import java.awt.GridLayout;

//This extends JFrame so the class is a frame when it's implemented.
//It implements actionlistener so the buttons can press
public class VendorInformation extends JFrame implements ActionListener
{
	private int width  = 100;   //width of the window
	private int height = 100;   //height of the window
    private JLabel[] labels;    //all of the labels from Vending.java
    private Candy[] candy_list; //all of the candy made in Vending.java
	private JButton[] buttons;  //all of the buttons made in Vending.java
    private String pound = "\u00a3";  //unicode for the pound
	private JFrame f;
    public  JPanel top       = new JPanel(new GridLayout());
	public  JLabel top_label = new JLabel();

	public VendorInformation(Vending f){
		super("Vendor Information"); //passing the name of the window
		setResizable(false);	     //doesnt allow the frame to be resized.
    	this.f = f;
    	this.candy_list = f.candy_list;
    	this.labels = f.labels;
		JPanel top = new JPanel(new GridLayout());	  //panels for formatting
		JPanel bottom = new JPanel(new GridLayout());
		JButton button = new JButton("Reset stock");  //creates a button to reset stock
		buttons = f.buttons;            //dupicates the button's properties from vending.java
		button.addActionListener(this); //actionPerformed() runs when the button is pressed
		String temp = String.format("%.2f",Candy.getRevenue()); //the total revenue.
		top_label = new JLabel("Total Sales: "+pound+temp);     //creates a label for the money
		top.add(top_label, BorderLayout.NORTH);
		bottom.add(button, BorderLayout.SOUTH);
		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		setBounds(0, 0, width, height); //sets the size of the frame.
		getContentPane().setBackground(new Color(225, 223, 207));
		top.setBackground(new Color(225, 223, 207));
		bottom.setBackground(new Color(225, 223, 207));
		button.setBackground(new Color(225, 223, 207));
		pack(); //condenses the size of the window

	}

	//this function is called to update the labels in the 
	public void update_text()
	{
		top_label.setText("Total Sales: "+pound+String.format("%.2f",Candy.getRevenue()));
	}


	//runs when a button is pressed in this frame
    public void actionPerformed(ActionEvent e) {
    	if (e.getActionCommand().equals("Reset stock")){
    		//resets the revenue back to 0
	    	Candy.reset_revenue();
	    	for (int i = 0; i < candy_list.length; i++){
	    		candy_list[i].reset_stock();
	    		labels[i].setText(candy_list[i].getStock()+" left");
	    		labels[i].setForeground(Color.BLACK);
	    		if (candy_list[i].getPrefix() == "Chocolate")
	    			buttons[i].setForeground(Color.WHITE);
	    		else
	    			buttons[i].setForeground(Color.BLACK);
	    	}
	    	String temp = String.format("%.2f",Candy.getRevenue());
	    	top_label.setText("Total Sales: "+pound+temp); //updates the text
	    	repaint();
			pack();
		}
    }
}