/**
 * @author John Althom A. Mendoza
 * 
 * The ActionListener class of the whole application.
 * 
 * */

package sp;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import utils.FileManipulation;


public class Predyketide_Events implements ActionListener
{

    private PredyketideWorkspaceVars workspaceVariables = new PredyketideWorkspaceVars();
    
    /**
     * height and width per segment of the biosynthesis
     */
    private int HEIGHT = 350;					
	private int baseWidth =120;					//baseWidth, to be referenced when a new synthesis is made
    private int WIDTH = 120;
    private int totalWidth = 0;

    
    /**
     * height of the supporting text
     */
    private int captionHeight = 30;
    //caption width will always be equal to panel width
    
    private double widthFactor = 12;			//widthfactor for larger molecules
	private double smallWidthFactor = 18;		//widthFactor for small molecules

    private String currModule = "";
    private String workingSMILES = "";

    private PolyketideUnit currUnit;
    
    private boolean isNewlyImported = false;
    
    @Override
    public void actionPerformed(ActionEvent e)
    {


        if(e.getSource() == Predyketide.starterSelectButton)
        {
        	/**
        	 * Selects the starter button. Shows all the available starter units.
        	 */
            Predyketide.selectStarterMenu.showPolyketideMenu();

        }
        else if(Predyketide.starterUnitSelectionButton.contains(e.getSource()))
        {

        	/**
        	 * Selects a starter. 
        	 */
            int index = Predyketide.starterUnitSelectionButton.indexOf(e.getSource());
            
            	workingSMILES = getWorkingSMILES_Starter(index);
            	workspaceVariables.addSequenceOfSMILES(workingSMILES);
            	
            	if(workingSMILES.length() < 12){
            		WIDTH = 70;
            		widthFactor = this.smallWidthFactor;
            	}
            	
            	paintNewSynthesisPanel(workingSMILES, "Starter Unit");
            	

            updateGUI_Buttons(false);
            Predyketide.selectStarterMenu.closePolyketideMenu();

        }
        else if(e.getSource() == Predyketide.synthesis_Plain_Button)
        {
        	/**
        	 * Selects the KS-AT-ACP button.
        	 */
        	if(workspaceVariables.isLimited()){
        		JOptionPane.showMessageDialog(null, "Maximum of " + PredyketideWorkspaceVars.LIMIT + " units are allowed!");
        		return;
        	}
            currModule = "KS-AT-ACP";
            Predyketide.selectExtenderMenu.showPolyketideMenu();

        }
        else if(e.getSource() == Predyketide.synthesis_KR_Button)
        {
        	/**
        	 * Selects the KS-AT-KR-ACP button.
        	 */
        	if(workspaceVariables.isLimited()){
        		JOptionPane.showMessageDialog(null, "Maximum of " + PredyketideWorkspaceVars.LIMIT + " units are allowed!");
        		return;
        	}
            currModule = "KS-AT-KR-ACP";
            Predyketide.selectExtenderMenu.showPolyketideMenu();

        }
        else if(e.getSource() == Predyketide.synthesis_DH_Button)
        {
        	/**
        	 * Selects the KS-AT-DH-KR-ACP button.
        	 */
        	if(workspaceVariables.isLimited()){
        		JOptionPane.showMessageDialog(null, "Maximum of " + PredyketideWorkspaceVars.LIMIT + " units are allowed!");
        		return;
        	}
            currModule = "KS-AT-DH-KR-ACP";
            Predyketide.selectExtenderMenu.showPolyketideMenu();

        }
        else if(e.getSource() == Predyketide.synthesis_ER_Button)
        {
        	/**
        	 * Selects the KS-AT-DH-ER-KR-ACP button.
        	 */
        	if(workspaceVariables.isLimited()){
        		JOptionPane.showMessageDialog(null, "Maximum of " + PredyketideWorkspaceVars.LIMIT + " units are allowed!");
        		return;
        	}
            currModule = "KS-AT-DH-ER-KR-ACP";
            Predyketide.selectExtenderMenu.showPolyketideMenu();

        }
        else if(Predyketide.extenderUnitSelectionButton.contains(e.getSource()))
        {

        	isNewlyImported = false;
        	/**
        	 * Selects an extender unit.
        	 */
            int index = Predyketide.extenderUnitSelectionButton.indexOf(e.getSource());

            
            	workingSMILES = getWorkingSMILES_Extender(index);
            	workspaceVariables.addSequenceOfSMILES(workingSMILES);
            
            	paintNewSynthesisPanel(workingSMILES, currModule);

            updateGUI_Buttons(false);
            Predyketide.selectExtenderMenu.closePolyketideMenu();
        }
        else if(e.getSource() == Predyketide.newSynthesisMenuItem){
        	/**
        	 * Chooses the "Create new synthesis" menu item.
        	 * Clears all variables.
        	 * 
        	 * 
        	 */
        	int toSave = -1;
        	if(workspaceVariables.getSequenceOfSMILES().size() > 0){
        		toSave = askVerification("Do you want to save this workspace\nbefore creating a new one?");
        	}
        	
        	if(toSave == JOptionPane.CANCEL_OPTION){
            	return;
        	}else if(toSave == JOptionPane.YES_OPTION){
        		workspaceVariables.setLastUnit(currUnit);
            	FileManipulation.exportSynthesisGUI(workspaceVariables);
        	}
        	revalidateWorkspacePanel(true);
        	updateGUI_Buttons(true);
        }
        else if(e.getSource() == Predyketide.generatePredictionsButton){
        	/**
        	 * Clicks the "Predictions" button.
        	 * 
        	 * (O) signifies an OH in the actual chemical molecule. This is the place
        	 * 		where thioesterification will occur.
        	 */
        	String findStr = "(O)"; 	
        	ArrayList<String> resultsSMILES = new ArrayList<String>();
        	
        	String resultCompundSMILES = "";
        	
        	if(isNewlyImported){
        		resultCompundSMILES = "SC(=O)C" + workingSMILES;
        	}else{
        		resultCompundSMILES = workingSMILES;
        	}
        	
        	int lastIndex = 0; //to eliminate a ring having less than 5 atoms
        	int ringNumber = 1;
        	while(resultCompundSMILES.indexOf(""+ringNumber) >= 0){
        		ringNumber++;
        	}
        	
        	
        	while(lastIndex != -1){
        		//assures that the OH in the starter are not candidates for the thioesterification
        	       lastIndex = resultCompundSMILES.indexOf(findStr,lastIndex);
        	       
        	       if( lastIndex != -1){
        	    	   //there is an OH
        	    	   String newRing = resultCompundSMILES.substring(1, lastIndex);
        	    	   
        	    	   String aPrediction = "O" + ringNumber+ newRing;
        	    	   String remainder = resultCompundSMILES.substring(lastIndex+3, resultCompundSMILES.length());
        	    	   if(remainder.length() > 0)
        	    		   aPrediction += "(" +remainder + ")" + ringNumber;
        	    	   else
        	    		   aPrediction += ringNumber;
        	    	   
        	    	  // System.out.println(aPrediction);
        	    	   if(newRing.replaceAll("\\([^\\(]*\\)", "").length() >= 5)
        	    		   resultsSMILES.add(aPrediction);
        	           lastIndex+=findStr.length();
        	      }
        	}
        	
        	/**
        	 * Condition if the candidates are less than or equal to 2. 
        	 * This will add the polyketide chain to the candidates list.
        	 * */
        	if(resultsSMILES.size() <= 2){
            	resultsSMILES.add(0, "O" + resultCompundSMILES.substring(1)); 
            	//adds the polyketide chain at the beggining of the list
        	}
        	
        	/**
        	 * This condition is for the preliminary candidate selection.
        	 * 
        	 * Due to chemical strain, the more possible chemical molecules from the compound
        	 * 		will form bonds on the farther OH.
        	 * 
        	 * The last five will be considered for ranking.
        	 * */
        	ArrayList<String> top5Molecules = new ArrayList<String>();
        	if(resultsSMILES.size() > 5){
        		for(int init = resultsSMILES.size()-1; ; init--){
        			top5Molecules.add(resultsSMILES.get(init));
        			if(top5Molecules.size() >=5 )
        				break;
        		}
        		resultsSMILES = top5Molecules;
        	}

        	workspaceVariables.setLastUnit(currUnit);
        	PredictionResultsFrame generatedResults = new PredictionResultsFrame(resultsSMILES,
        			workspaceVariables.getWorkingPolyketideUnits(),Predyketide.synthesisMainPanel, workspaceVariables,
        			totalWidth, HEIGHT+captionHeight);
        	new Thread(generatedResults).start();        	
        }
        else if(e.getSource() == Predyketide.importSynthesisMenuItem){
        	
        	/**
        	 * Imports a text file to the current workspace.
        	*/
        	int toSave = -1;
        	if(workspaceVariables.getSequenceOfSMILES().size() > 0){
        		toSave = askVerification("Do you want to save this workspace\nbefore opening a new one?");
        	}
        	
        	if(toSave == JOptionPane.CANCEL_OPTION){
            	return;
        	}else if(toSave == JOptionPane.YES_OPTION){
        		workspaceVariables.setLastUnit(currUnit);
            	FileManipulation.exportSynthesisGUI(workspaceVariables);
        	}
        	
        	workspaceVariables.setLastUnit(currUnit);
        	FileManipulation.exportSynthesisGUI(workspaceVariables);
        	
        	revalidateWorkspacePanel(true);
        	
        	workspaceVariables = FileManipulation.importSynthesis();
        	currUnit = workspaceVariables.getLastUnit();
        	
        		boolean firstUnit = true;
            	for(int x = 0; x < workspaceVariables.getSequenceOfSMILES().size(); x++){
            		String SMILES = workspaceVariables.getSequenceOfSMILES().get(x);
            		String labelString = "";
                	if(firstUnit){
                		labelString = "Starter Unit";
                		firstUnit = false;
                	}else{
                		switch(workspaceVariables.getWorkingPolyketideUnits().get(x-1).getPKSModule()){
                		case PolyketideUnit.KETOSYNTHASE:
                			labelString = "KS-AT-ACP";
                			break;
                		case PolyketideUnit.KETOREDUCTASE:
                			labelString = "KS-AT-KR-ACP";
                			break;
                		case PolyketideUnit.DEHYDRATASE:
                			labelString = "KS-AT-DH-KR-ACP";
                			break;
                		case PolyketideUnit.ENOYL_REDUCTASE:
                			labelString = "KS-AT-DH-ER-KR-ACP";
                			break;
                		}
                	}
                	
            		paintNewSynthesisPanel(SMILES, labelString);
            		
            	}
            	
        	updateGUI_Buttons(false);
        	workingSMILES = workspaceVariables.getWorkingSMILES();
        	isNewlyImported = true;
        }
        else if(e.getSource() == Predyketide.exportSynthesisMenuItem){
        	/**
        	 * Exports the workspace into a text file.
        	 */;
        	if(workspaceVariables.getSequenceOfSMILES().size() > 0){
        		workspaceVariables.setLastUnit(currUnit);
            	FileManipulation.exportSynthesisGUI(workspaceVariables);
            }else{
            	JOptionPane.showMessageDialog(null, "No workspace to save.");
            }
        	
        }
        else if(e.getSource() == Predyketide.exportImageMenuItem){
        	/**
        	 * Exports the workspace into a text file.
        	 */
        	if(workspaceVariables.getSequenceOfSMILES().size() > 0){
        		FileManipulation.exportImageGUI(Predyketide.synthesisMainPanel, totalWidth, HEIGHT+captionHeight);
            }else{
            	JOptionPane.showMessageDialog(null, "No workspace to render.");
            }
        }
        else if(e.getSource() == Predyketide.changeNatProdMenuItem){
        	Predyketide.NP_ReferenceData = FileManipulation.importReferenceData();
        }
        else if(e.getSource() == Predyketide.changeSyntheticMenuItem){
        	Predyketide.Synthetic_ReferenceData = FileManipulation.importReferenceData();
        } else if(e.getSource() == Predyketide.aboutButton || e.getSource() == Predyketide.aboutMenuItem)
        {
        	new AboutDialog();
        } else if(e.getSource() == Predyketide.helpMenuItem)
        {
        	new HelpDialog("temporary help");
        }
        else if(e.getSource() == Predyketide.exitButton || e.getSource() == Predyketide.exitMenuItem)
        {
        	/**
        	 * Closes the application.
        	 */
        	int toSave = -1;
        	if(workspaceVariables.getSequenceOfSMILES().size() > 0){
        		toSave = askVerification("Do you want to save this workspace\nbefore closing the application?");
        	}
        	
        	if(toSave == JOptionPane.CANCEL_OPTION){
            	return;
        	}else if(toSave == JOptionPane.YES_OPTION){
        		workspaceVariables.setLastUnit(currUnit);
            	FileManipulation.exportSynthesisGUI(workspaceVariables);
        	}
            System.exit(0);
        }
    }

    
    

    /**
     * Function: returns the SMILES string of the requested starter unit.
     * @param starterID 
     * */
    private String getWorkingSMILES_Starter(int starterID)
    {

        currUnit = new PolyketideUnit(Predyketide.starterUnits.get(starterID));
        String forwardSMILES = currUnit.getForwardSMILES();
        forwardSMILES = "SC(=O)C" + forwardSMILES;

        return forwardSMILES;
        
    }

    
    private String getWorkingSMILES_Extender(int extenderID)
    {
    	
        if(currModule.equals("KS-AT-ACP"))
        {
            currUnit.doNothing();
        }
        else if(currModule.equals("KS-AT-KR-ACP"))
        {
            currUnit.addKetoReductase();
        }
        else if(currModule.equals("KS-AT-DH-KR-ACP"))
        {
            currUnit.addDehydretase();
        }
        else if(currModule.equals("KS-AT-DH-ER-KR-ACP"))
        {
            currUnit.addEnoylReductase();
        }

        workspaceVariables.addWorkingPolyketideUnit(currUnit);
        currUnit = new PolyketideUnit(Predyketide.extenderUnits.get(extenderID));

        String forwardSMILES = "";
        
        for(PolyketideUnit unit : workspaceVariables.getWorkingPolyketideUnits())
        {
            forwardSMILES = unit.getForwardSMILES() + forwardSMILES;
        }
        forwardSMILES = "SC(=O)C" + currUnit.getForwardSMILES() + forwardSMILES;

       
        return forwardSMILES;
    }

    
    private void revalidateWorkspacePanel(boolean isNewPanel){
    	if(isNewPanel){
    		this.WIDTH = this.baseWidth ;
        	Predyketide.synthesisMainPanel.removeAll();
        	Predyketide.synthesisMainPanel.repaint();
        	
            workspaceVariables = new PredyketideWorkspaceVars();
            currUnit = null;
            totalWidth = 0;
    	}
    	
        Predyketide.synthesisMainPanel.revalidate();
    }
    
    
    private void updateGUI_Buttons(boolean isStarterActive){
    	
        Predyketide.starterSelectButton.setEnabled(isStarterActive);
        Predyketide.synthesis_Plain_Button.setEnabled(!isStarterActive);
        Predyketide.synthesis_KR_Button.setEnabled(!isStarterActive);
        Predyketide.synthesis_DH_Button.setEnabled(!isStarterActive);
        Predyketide.synthesis_ER_Button.setEnabled(!isStarterActive);
        

        if(workspaceVariables.getWorkingPolyketideUnits().size() >= 3)
        {
            Predyketide.generatePredictionsButton.setEnabled(true);
        }else{
            Predyketide.generatePredictionsButton.setEnabled(false);
        }
    }
    
    
    private void paintNewSynthesisPanel(String SMILES, String label){
    	try{

        	PolyketideChainPanel newPKPanel = new PolyketideChainPanel("[S-]"+SMILES.substring(1), WIDTH+=widthFactor, HEIGHT);
        	
            
            JPanel dummyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        	dummyPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT+captionHeight));
        	dummyPanel.setBackground(Color.WHITE);
        	JPanel captionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        	
        	captionPanel.setSize(WIDTH, captionHeight);
        	captionPanel.setBackground(Color.WHITE);
        	captionPanel.add(new JLabel(label));
        	
        	dummyPanel.add(captionPanel);
        	dummyPanel.add(newPKPanel);
        	

        	Predyketide.synthesisMainPanel.add(dummyPanel);
            revalidateWorkspacePanel(false);
            totalWidth += WIDTH;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    private int askVerification(String question){
    	try{
    		return JOptionPane.showConfirmDialog(null, question, "", JOptionPane.YES_NO_CANCEL_OPTION);
    				
    	}catch(Exception e){
    		return JOptionPane.CANCEL_OPTION;
    	}
    	
    }
    
}
