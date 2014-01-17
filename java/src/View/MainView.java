package View;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainView extends JFrame
{
	private static final long serialVersionUID = 1L;
	GridBagConstraints gbc = new GridBagConstraints();
	
	public void setupView(String title)
	{
		setTitle("Gamebook XML - "+title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //EXIT_ON_CLOSE
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int w = d.width;
		int h = d.height;
		setSize(w/2,h/2);
		setLocation((w/2)-w/4,(h/2)-h/4);
		
		Container container = getContentPane();
		container.setBackground(new Color(120,192,255));
		GridBagLayout gbl = new GridBagLayout();
		container.setLayout(gbl);
	}
	
	public boolean isVisible()
	{
		return super.isVisible();
	}
	
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
	}
	
	public void close()
	{
		super.setVisible(false);
		super.dispose();
	}
	
	public void putGBC(int gridx, int gridy,int gridwidth, int gridheight)
	{
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;//nb de colonnes
		gbc.gridheight = gridheight;//nb de lignes
		gbc.fill = GridBagConstraints.BOTH;
	}
	
	public MainView()
	{
		setupView("");
	}
}
