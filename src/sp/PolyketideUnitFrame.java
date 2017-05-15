package sp;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PolyketideUnitFrame extends JDialog
{

	private ArrayList<JButton> buttonSet;
	private JPanel layoutPanel;

    public PolyketideUnitFrame(boolean starter)
    {

    	layoutPanel = new JPanel();
    	
        if(starter){
        	layoutPanel.setLayout(new GridLayout(4, 0));
        	this.setTitle("Select starter unit");
        	buttonSet = Predyketide.starterUnitSelectionButton; 
        }else{
        	layoutPanel.setLayout(new GridLayout(2, 2));
        	this.setTitle("Select extender unit");
        	buttonSet = Predyketide.extenderUnitSelectionButton; 
        }
        
        for(JButton button : buttonSet)
        {
            layoutPanel.add(button);
        }

        this.add(layoutPanel);
        setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setModal(true);
    }


    public void showPolyketideMenu()
    {
        setVisible(true);
    }


    public void closePolyketideMenu()
    {
        dispose();
        setVisible(false);
    }
    
    public void addNewCustomizedButton(PolyketideUnit pkUnit, boolean starter){
    	//TODO on creation of a new extender and starter unit.
    	if(starter)
    		Predyketide.starterUnits.add(pkUnit);
    	pkUnit.setImage(pkUnit.getImagePath());
    	JButton newButton = new JButton(pkUnit.getImage());
    	//
    	//edit this to use the universal action listener
    	//newButton.addActionListener(new Predyketide_Events());
    	buttonSet.add(newButton);
    	layoutPanel.add(newButton);
    	layoutPanel.revalidate();
    }
    
}
