package sp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PredyketideWelcome extends JFrame
{

    private BufferedImage image;
    private JPanel mainWelcomePanel;
    private String statusLabel = "Initializing...";

    private int StringX = 70;
    private int StringY = 300;
    
    
    public PredyketideWelcome()
    {
    	
    	mainWelcomePanel = new JPanel();
    	
        try
        {

            String logo = "/resources/logo.jpg";
            InputStream in = getClass().getResourceAsStream(logo);
            if(in == null)
            {
                in = getClass().getResourceAsStream("/logo.jpg");
            }

            this.image= ImageIO.read(in);
        
            mainWelcomePanel.add(new JLabel(new ImageIcon(image)));
            mainWelcomePanel.setBackground(new Color(0,0,0,0));
            this.add(mainWelcomePanel);
            this.setUndecorated(true);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setVisible(true);


        }
        catch (IOException ex)
        {

        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paintComponents(g);
        g.drawString(statusLabel, StringX, StringY); // see javadoc for more info on the parameters            
    }
    
    
    public void setStatusLabel(String statusLabel){
    	this.statusLabel = statusLabel;
    }
    
}
