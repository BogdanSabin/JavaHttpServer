package gui.helpers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JDataInfo extends JPanel{
	private JLabel staticLabel;
	private JLabel dynamicLabel;
	
	public JDataInfo(String textLabelStatic, String textLabelDynamic, Font font, Color c) {
		 staticLabel = new JLabel(textLabelStatic);
		 staticLabel.setFont(font);
		 
		 dynamicLabel = new JLabel(textLabelDynamic);
		 dynamicLabel.setFont(font);
		 
		 setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));
		 setMaximumSize(new Dimension(250, 20));
		 add(staticLabel);
		 add(dynamicLabel);	
		 
	}
	
	public void setText(String text) {
		this.dynamicLabel.setText(text);
	}
	
}
