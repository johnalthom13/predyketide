package sp;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;



/**
 *
 * @author user
 */
@SuppressWarnings("serial")
public class Predyketide extends JFrame
{
	

    // Variables declaration - do not modify
	static ArrayList<IAtomContainer> NP_ReferenceData;
	static ArrayList<IAtomContainer> Synthetic_ReferenceData;
	static SmilesParser smpars = new SmilesParser(DefaultChemObjectBuilder.getInstance());
	
	
    static JButton aboutButton;
    static JButton exitButton;

   	static  JMenuItem aboutMenuItem;
    private JMenu fileMenu;
    private JPanel guiMainPanel;

    static JMenuItem helpMenuItem;
    
    static JMenuItem newSynthesisMenuItem;
    static JMenuItem exitMenuItem;
    static JMenuItem importSynthesisMenuItem;
    static JMenuItem exportSynthesisMenuItem;
    static JMenuItem exportImageMenuItem;
    
    private JMenu optionsMenu;
    
    private JMenu referenceDataMenu;
    static JMenuItem changeNatProdMenuItem;
    static JMenuItem changeSyntheticMenuItem;
    
    private JMenuBar predyketideMenu;

    static JPanel synthesisMainPanel;
    static JScrollPane synthesisScrollPanel;

    private Predyketide_Events eventsHandler;
	

    static ArrayList<JButton> starterUnitSelectionButton;
    static ArrayList<JButton> extenderUnitSelectionButton;

    static JButton starterSelectButton;
    static JButton synthesis_DH_Button;
    static JButton synthesis_ER_Button;
    static JButton synthesis_KR_Button;
    static JButton synthesis_Plain_Button;

    static JButton generatePredictionsButton;

    static StarterUnitCollection allStarterUnits;
    static ExtenderUnitCollection allExtenderUnits;
    static ArrayList<PolyketideUnit> starterUnits;
    static ArrayList<PolyketideUnit> extenderUnits;

    static PolyketideUnitFrame selectStarterMenu;
    static PolyketideUnitFrame selectExtenderMenu;

    // End of variables declaration

    /** Creates new form Predyketide_GUI */
    public Predyketide()
    {

    	PredyketideWelcome welcome = new PredyketideWelcome();
    	welcome.setStatusLabel("Initializing variables...");
    	welcome.repaint();
    	
    	
        eventsHandler = new Predyketide_Events();
        starterUnits = new ArrayList<PolyketideUnit>();
        starterUnitSelectionButton = new ArrayList<JButton>();
        extenderUnits = new ArrayList<PolyketideUnit>();
        extenderUnitSelectionButton = new ArrayList<JButton>();

        //tasks on initializing starter units
    	welcome.setStatusLabel("Gathering info on starter units...");
    	welcome.repaint();
        allStarterUnits = new StarterUnitCollection();

    	welcome.setStatusLabel("Rendering images of starter units...");
    	welcome.repaint();
        for(int i = 0; i < starterUnits.size(); i++)
        {
            String unitPath = starterUnits.get(i).getImagePath();
        	welcome.setStatusLabel("Rendering " + unitPath);
        	welcome.repaint();
            starterUnits.get(i).setImage(unitPath);
        }

       // System.out.println("Creating menu for starter units...");
    	welcome.setStatusLabel("Creating menu for starter units...");
    	welcome.repaint();
        for(int i = 0; i < starterUnits.size(); i++)
        {
            PolyketideUnit unit = starterUnits.get(i);

            JButton button = new JButton(unit.getImage());
            starterUnitSelectionButton.add(button);
            starterUnitSelectionButton.get(i).addActionListener(eventsHandler);
        }
        selectStarterMenu = new PolyketideUnitFrame(true);


        //tasks on initializing extender units
        //System.out.println("Gathering info on extender units...");
    	welcome.setStatusLabel("Gathering info on extender units...");
    	welcome.repaint();
        allExtenderUnits = new ExtenderUnitCollection();

        //System.out.println("Rendering images of extender units...");
    	welcome.setStatusLabel("Rendering images of extender units...");
    	welcome.repaint();

        for(int i = 0; i < extenderUnits.size(); i++)
        {
            String unitPath = extenderUnits.get(i).getImagePath();
            //System.out.println("Rendering " + unitPath);
            welcome.setStatusLabel("Rendering " + unitPath);
        	welcome.repaint();
            extenderUnits.get(i).setImage(unitPath);
        }

        //System.out.println("Creating menu for extender units...");
        welcome.setStatusLabel("Creating menu for extender units...");
    	welcome.repaint();
        for(int i = 0; i < extenderUnits.size(); i++)
        {
            PolyketideUnit unit = extenderUnits.get(i);

            JButton button = new JButton(unit.getImage());
            extenderUnitSelectionButton.add(button);
            extenderUnitSelectionButton.get(i).addActionListener(eventsHandler);
        }
        selectExtenderMenu = new PolyketideUnitFrame(false);

        welcome.setStatusLabel("Fetching NATPROD reference data...");
    	welcome.repaint();
        NP_ReferenceData = new ReferenceData(true);
        //System.out.print(NP_ReferenceData.size());
        
        welcome.setStatusLabel("Fetching SYNTHETIC reference data...");
    	welcome.repaint();
    	Synthetic_ReferenceData = new ReferenceData(false);
    	//System.out.println(Synthetic_ReferenceData.size());

        welcome.setStatusLabel("Initializing user interface...");
    	welcome.repaint();
        initComponents();
        welcome.dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents()
    {

        guiMainPanel = new JPanel();
        starterSelectButton = new JButton();
        synthesis_Plain_Button = new JButton();
        synthesis_KR_Button = new JButton();
        synthesis_DH_Button = new JButton();
        synthesis_ER_Button = new JButton();
        exitButton = new JButton();
        aboutButton = new JButton();
        generatePredictionsButton = new JButton();
        synthesisScrollPanel = new JScrollPane();

        synthesisMainPanel = new JPanel();
        predyketideMenu = new JMenuBar();
        referenceDataMenu = new JMenu();
        fileMenu = new JMenu();
        changeNatProdMenuItem = new JMenuItem();
        changeSyntheticMenuItem = new JMenuItem();
        newSynthesisMenuItem = new JMenuItem();
        exportImageMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        importSynthesisMenuItem = new JMenuItem();
        exportSynthesisMenuItem = new JMenuItem();
        optionsMenu = new JMenu();
        helpMenuItem = new JMenuItem();
        aboutMenuItem = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        
        starterSelectButton.setText("Starter");
        starterSelectButton.addActionListener(eventsHandler);

        synthesis_Plain_Button.setText("KS-AT-ACP");
        synthesis_Plain_Button.setEnabled(false);
        synthesis_Plain_Button.addActionListener(eventsHandler);

        synthesis_KR_Button.setText("KS-AT-KR-ACP");
        synthesis_KR_Button.setEnabled(false);
        synthesis_KR_Button.addActionListener(eventsHandler);

        synthesis_DH_Button.setText("KS-AT-DH-KR-ACP");
        synthesis_DH_Button.setEnabled(false);
        synthesis_DH_Button.addActionListener(eventsHandler);

        synthesis_ER_Button.setText("KS-AT-DH-ER-KR-ACP");
        synthesis_ER_Button.setEnabled(false);
        synthesis_ER_Button.addActionListener(eventsHandler);

        exitButton.setText("Exit");
        exitButton.addActionListener(eventsHandler);

        aboutButton.setText("About");
        aboutButton.addActionListener(eventsHandler);

        generatePredictionsButton.setText("Predictions");
        generatePredictionsButton.addActionListener(eventsHandler);
        generatePredictionsButton.setEnabled(false);

        synthesisScrollPanel.setBackground(new Color(255, 255, 255));
        synthesisScrollPanel.setAutoscrolls(true);

        synthesisMainPanel.setBackground(new Color(255, 255, 255));

        GroupLayout synthesisMainPanelLayout = new GroupLayout(synthesisMainPanel);
        synthesisMainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        synthesisMainPanelLayout.setHorizontalGroup(
            synthesisMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 818, Short.MAX_VALUE)
        );
        synthesisMainPanelLayout.setVerticalGroup(
            synthesisMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );

        synthesisScrollPanel.setViewportView(synthesisMainPanel);

        GroupLayout guiMainPanelLayout = new GroupLayout(guiMainPanel);
        guiMainPanel.setLayout(guiMainPanelLayout);
        guiMainPanelLayout.setHorizontalGroup(
            guiMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, guiMainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(synthesisScrollPanel, GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(guiMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(generatePredictionsButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(starterSelectButton, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(synthesis_Plain_Button, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(synthesis_KR_Button, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(synthesis_DH_Button, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(synthesis_ER_Button, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(exitButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(aboutButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())





        );
        guiMainPanelLayout.setVerticalGroup(
            guiMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(guiMainPanelLayout.createSequentialGroup()
                .addGap(63, 63, 63)




                .addGroup(guiMainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(guiMainPanelLayout.createSequentialGroup()
                        .addComponent(starterSelectButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(synthesis_Plain_Button, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(synthesis_KR_Button, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(synthesis_DH_Button, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(synthesis_ER_Button, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                        .addComponent(generatePredictionsButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aboutButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitButton))
                    .addComponent(synthesisScrollPanel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
                .addContainerGap())
        );


        fileMenu.setText("File");


        newSynthesisMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        newSynthesisMenuItem.setText("Create new synthesis");
        newSynthesisMenuItem.addActionListener(eventsHandler);
        fileMenu.add(newSynthesisMenuItem);
        
        fileMenu.addSeparator();
        
        importSynthesisMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        importSynthesisMenuItem.setText("Import synthesis");
        importSynthesisMenuItem.addActionListener(eventsHandler);
        fileMenu.add(importSynthesisMenuItem);
        
        exportSynthesisMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        exportSynthesisMenuItem.setText("Export synthesis");
        exportSynthesisMenuItem.addActionListener(eventsHandler);
        fileMenu.add(exportSynthesisMenuItem);
        
        exportImageMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        exportImageMenuItem.setText("Save synthesis as JPG");
        exportImageMenuItem.addActionListener(eventsHandler);
        fileMenu.add(exportImageMenuItem);
        
        referenceDataMenu.setText("Change reference data");

        changeNatProdMenuItem.setText("Natural product data");
        changeNatProdMenuItem.addActionListener(eventsHandler);
        referenceDataMenu.add(changeNatProdMenuItem);
        
        changeSyntheticMenuItem.setText("Synthetic data");
        changeSyntheticMenuItem.addActionListener(eventsHandler);
        referenceDataMenu.add(changeSyntheticMenuItem);

        fileMenu.addSeparator();
        
        fileMenu.add(referenceDataMenu);
        
        fileMenu.addSeparator();
        
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        exitMenuItem.addActionListener(eventsHandler);
        exitMenuItem.setText("Exit");
        fileMenu.add(exitMenuItem);

        predyketideMenu.add(fileMenu);

        optionsMenu.setText("Options");

        helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        helpMenuItem.setText("Help");
        helpMenuItem.addActionListener(eventsHandler);
        optionsMenu.add(helpMenuItem);

        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(eventsHandler);
        optionsMenu.add(aboutMenuItem);

        predyketideMenu.add(optionsMenu);

        setJMenuBar(predyketideMenu);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                      .addContainerGap()
                      .addComponent(guiMainPanel, GroupLayout.DEFAULT_SIZE, 996, Short.MAX_VALUE)
                      .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                      .addContainerGap()
                      .addComponent(guiMainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                      .addContainerGap())
        );
        setTitle("Predyketide v1.0");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>

}
