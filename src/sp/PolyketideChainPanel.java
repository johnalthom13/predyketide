package sp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.vecmath.Point2d;

import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.ExtendedAtomGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;


@SuppressWarnings("serial")
public class PolyketideChainPanel extends JPanel
{

    private int height;
    private int width;
    private IAtomContainer molecule;
    private AtomContainerRenderer renderer;
    private boolean willFlip = false;

    public PolyketideChainPanel(String moleculeSMILES, int width, int height) throws InvalidSmilesException
    {
        this.setSize(width, height);

       
        IAtomContainer dummyMolecule = (IAtomContainer) Predyketide.smpars.parseSmiles(moleculeSMILES);
        StructureDiagramGenerator sdg = new StructureDiagramGenerator();
        sdg.setMolecule(dummyMolecule);
        try {
            sdg.generateCoordinates();
        }
        catch (Exception ex)
        {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.molecule = sdg.getMolecule();

        //determine whether to rotate the image
        //since the first atom will always be S, check if it has 2d Points (0,0)
        IAtom atom = molecule.getAtom(0);
        //System.out.println(atom.getSymbol());
        if(atom.getPoint2d().equals(new Point2d(0, 0)) ){
        	willFlip = true;
        }else{
        	willFlip = false;
        }
        
        
        this.width = width;
        this.height = height;

        // generators make the image elements
        ArrayList<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
        generators.add(new BasicSceneGenerator());
        generators.add(new BasicBondGenerator());
        generators.add(new BasicAtomGenerator());
        generators.add(new ExtendedAtomGenerator());

        // the renderer needs to have a toolkit-specific font manager
        this.renderer = new AtomContainerRenderer(generators, new AWTFontManager());

    }

    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }

    public void paint(Graphics graphics)
    {
        // the call to 'setup' only needs to be done on the first paint
        //renderer.setup(this.molecule, new Rectangle(width, height));
        //renderer.setZoom(0.6);
    	//TODO words sa ibaba.
        // paint the background
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // the paint method also needs a toolkit-specific renderer
        Graphics2D g2d = (Graphics2D) graphics; // Create a Java2D version of g.
        if(willFlip){
            g2d.translate(width/2, height/2);
            g2d.scale(-1.0, -1.0);
            g2d.translate(-width/2, -height/2);
        }
        //g2d.translate(-45, 0); // Translate the center of our coordinates.
        //g2d.rotate(-0.26);  // Rotate the image by 1 radian.
        AWTDrawVisitor visitor = new AWTDrawVisitor(g2d);
        renderer.paint(this.molecule, visitor, new Rectangle(width, height), true);

    }
}
