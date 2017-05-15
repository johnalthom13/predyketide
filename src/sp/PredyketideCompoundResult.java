package sp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.BitSet;

import javax.swing.ImageIcon;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.fingerprint.HybridizationFingerprinter;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.qsar.descriptors.molecular.ALOGPDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.APolDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.BPolDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.HBondAcceptorCountDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.HBondDonorCountDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.HybridizationRatioDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.LargestChainDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.LargestPiSystemDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.LongestAliphaticChainDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.TPSADescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.VABCDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.WeightDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.ZagrebIndexDescriptor;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.ExtendedAtomGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;
import org.openscience.cdk.similarity.Tanimoto;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

import com.ibm.icu.util.StringTokenizer;

public class PredyketideCompoundResult {
	
	private ImageIcon thumbnail;
	private Image image;
	private	String resultSMILES;
	private double NP_LikenessScore = 0;
	private IAtomContainer compoundMolecule;
	static final int IMAGE_THUMBLENGTH = 120;
	static final int IMAGE_LENGTH = 400;
	
	
	public PredyketideCompoundResult(String resultsSMILES){
		this.resultSMILES = resultsSMILES;
		//System.out.println(resultsSMILES);
		try {
			generatePredictedCompoundMolecule();
			computeScores();
		} catch (InvalidSmilesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the thumbnail
	 * @throws InvalidSmilesException 
	 */
	public void generateResultImage() throws InvalidSmilesException {

        Rectangle drawArea = new Rectangle(IMAGE_LENGTH, IMAGE_LENGTH);
        Image image = new BufferedImage(IMAGE_LENGTH, IMAGE_LENGTH, BufferedImage.TYPE_INT_RGB);
   	        
        // generators make the image elements
        ArrayList<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
        generators.add(new BasicSceneGenerator());
        generators.add(new BasicBondGenerator());
        generators.add(new BasicAtomGenerator());
        generators.add(new ExtendedAtomGenerator());

        AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
        
        Graphics2D g2 = (Graphics2D) image.getGraphics();
         
         g2.setColor(Color.WHITE);
         g2.fillRect(0, 0, IMAGE_LENGTH, IMAGE_LENGTH);
                
        renderer.paint(compoundMolecule, new AWTDrawVisitor(g2), drawArea, true);
        
        this.image = image;
        image = image.getScaledInstance(IMAGE_THUMBLENGTH, IMAGE_THUMBLENGTH, Image.SCALE_SMOOTH);
        this.thumbnail = new ImageIcon(image);
	}
	
	
	//create the molecule object
	private void generatePredictedCompoundMolecule() throws InvalidSmilesException {
		
		IAtomContainer dummyMolecule = (IAtomContainer) Predyketide.smpars.parseSmiles(this.resultSMILES);
        StructureDiagramGenerator sdg = new StructureDiagramGenerator();
        sdg.setMolecule(dummyMolecule);
        try {
            sdg.generateCoordinates();
        }
        catch (Exception ex)
        {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.compoundMolecule = sdg.getMolecule();
                
	}

	private void computeScores() throws CDKException{
		
		HybridizationFingerprinter fingerprinter = new HybridizationFingerprinter();
		BitSet bitset1 = fingerprinter.getFingerprint(this.compoundMolecule);
		
		//computing for the NP score
		float natProdScore = 0;
		for(IAtomContainer anNP : Predyketide.NP_ReferenceData){
			BitSet bitset2 = fingerprinter.getFingerprint(anNP);
			natProdScore += Math.log(Tanimoto.calculate(bitset1, bitset2));
		}
		
		//computing for the NP score
		float syntheticScore = 0;		
		for(IAtomContainer anNP : Predyketide.Synthetic_ReferenceData){
				BitSet bitset2 = fingerprinter.getFingerprint(anNP);
				syntheticScore += Math.log(Tanimoto.calculate(bitset1, bitset2));
		}
		
		double ratioSyntheticToNP = ((double) Predyketide.Synthetic_ReferenceData.size()/(double) Predyketide.NP_ReferenceData.size());
		this.NP_LikenessScore = natProdScore - syntheticScore + Math.log(ratioSyntheticToNP);
		
	}
	
	public double getNP_LikenessScore(){
		return NP_LikenessScore;
	}
	
	
	public IAtomContainer getPredictedMolecule(){
		return this.compoundMolecule;
	}
	
	public ImageIcon getThumbnail(){
		return thumbnail;
	}
	
	/**
	 * @return the resultSMILES
	 */
	public String getResultSMILES() {
		return resultSMILES;
	}
	
	/**
	 * @param resultSMILES the resultSMILES to set
	 */
	public void setResultSMILES(String resultSMILES) {
		this.resultSMILES = resultSMILES;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return the descriptors
	 * @throws CDKException 
	 */
	public String getDescriptors() throws CDKException {
		
		DecimalFormat df = new DecimalFormat("#.00000");
		
		IMolecularFormula molFormula = MolecularFormulaManipulator.getMolecularFormula(this.compoundMolecule);
		String returnString = MolecularFormulaManipulator.getString(molFormula);
		molFormula = MolecularFormulaManipulator.getMolecularFormula(returnString, DefaultChemObjectBuilder.getInstance());
		returnString = MolecularFormulaManipulator.getString(molFormula);
		
		returnString += "\nNP-Likeness Score: " + df.format(getNP_LikenessScore());
         
        ALOGPDescriptor alogp = new ALOGPDescriptor();
        StringTokenizer tokenizeLogP = new StringTokenizer(alogp.calculate(this.compoundMolecule).getValue().toString(), ",");
        returnString += "\n\nALogP - Ghose-Crippen LogKow: " + df.format(Double.parseDouble(tokenizeLogP.nextToken()));
        returnString += "\nALogP2: " + df.format(Double.parseDouble(tokenizeLogP.nextToken()));
        returnString += "\nAMR - molar refractivity: " + df.format(Double.parseDouble(tokenizeLogP.nextToken()));
 		
        APolDescriptor apol = new APolDescriptor();
        Double apolValue = Double.parseDouble(apol.calculate(this.compoundMolecule).getValue().toString());
        returnString += "\nTotal atomic polarizability: " + df.format(apolValue);
        
        returnString += "\nAtom count: " + this.compoundMolecule.getAtomCount();
        
        returnString += "\nBond count: " + this.compoundMolecule.getBondCount();
        
        BPolDescriptor bondPol= new BPolDescriptor();
        Double bondPolValue = Double.parseDouble(bondPol.calculate(this.compoundMolecule).getValue().toString());
        returnString += "\nBond polarizability: " + df.format(bondPolValue);
        
        HBondDonorCountDescriptor hDonor = new HBondDonorCountDescriptor();
        returnString += "\nHyrdogen bond donors: " + hDonor.calculate(this.compoundMolecule).getValue().toString();
        
        HBondAcceptorCountDescriptor hAcceptor = new HBondAcceptorCountDescriptor();
        returnString += "\nHyrdogen bond acceptors: " + hAcceptor.calculate(this.compoundMolecule).getValue().toString();
        
        HybridizationRatioDescriptor hRatio = new HybridizationRatioDescriptor();
        Double hRatioVal = Double.parseDouble(hRatio.calculate(this.compoundMolecule).getValue().toString());
        returnString += "\nHybridization ratio: " + df.format(hRatioVal);
        
        LargestChainDescriptor chain = new LargestChainDescriptor();
        returnString += "\nLargest chain: " + chain.calculate(this.compoundMolecule).getValue().toString();
        
        LargestPiSystemDescriptor piChain = new LargestPiSystemDescriptor();
        returnString += "\nLargest PI chain: " + piChain.calculate(this.compoundMolecule).getValue().toString();
        
        LongestAliphaticChainDescriptor aliphaticChain = new LongestAliphaticChainDescriptor();
        returnString += "\nLongest aliphatic chain: " + aliphaticChain.calculate(this.compoundMolecule).getValue().toString();
                   
        TPSADescriptor tpsa = new TPSADescriptor();
        Double tpsaVal = Double.parseDouble(tpsa.calculate(this.compoundMolecule).getValue().toString());
        returnString += "\nTopological surface area: " + df.format(tpsaVal);
        
        VABCDescriptor vabc = new VABCDescriptor();
        Double vabcVal = Double.parseDouble(vabc.calculate(this.compoundMolecule).getValue().toString());
        returnString += "\nVolume: " + df.format(vabcVal);
        
        WeightDescriptor weight = new WeightDescriptor();
        Double weightVal = Double.parseDouble(weight.calculate(this.compoundMolecule).getValue().toString());
        returnString += "\nWeight: " + df.format(weightVal);
        
        XLogPDescriptor xlogp = new XLogPDescriptor();
        Double xlogVal = Double.parseDouble(xlogp.calculate(this.compoundMolecule).getValue().toString());
        returnString += "\nXLogP: " + df.format(xlogVal);
        
        ZagrebIndexDescriptor zagreb = new ZagrebIndexDescriptor();
        Double zagrebVal = Double.parseDouble(zagreb.calculate(this.compoundMolecule).getValue().toString());
        returnString += "\nZagreb index: " + df.format(zagrebVal);
        
 		return returnString;
	}
}
