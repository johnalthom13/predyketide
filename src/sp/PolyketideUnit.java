package sp;

import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * PolyketideUnit.java is a class to represent a polyketide unit.
 * This includes both starter and extender units.
 * 
 * */

public class PolyketideUnit
{

	/**
	 * Constant integers that describe the different synthesis processes.
	 * 
	 * */
	static final int KETOSYNTHASE = 0;
	static final int KETOREDUCTASE = 1;
	static final int DEHYDRATASE = 2;
	static final int ENOYL_REDUCTASE = 3; 
	
	/**
	 * PKSModule determines what synthesis process was performed.
	 * */
	private int PKSModule = -1; 
	
    private String unitName;		//The name of the polyketide unit.
    private String forwardSMILES;	//A SMILES representation of the polyketide unit.
    private ImageIcon image;		//The image of the unit to be used in the menu.
    private String imagePath;		//The path of the image to be used in the menu.


    /**
     * Constructors of the polyketide unit.
     * */
    public PolyketideUnit(String unitName, String forwardSMILES, String imagePath)
    {
        this.unitName = unitName;
        this.forwardSMILES = (!forwardSMILES.equals("XXX")) ? forwardSMILES : "";
        this.setImagePath(imagePath);
    }

    public PolyketideUnit(String unitName, String forwardSMILES)
    {
        this.unitName = unitName;
        this.forwardSMILES = (!forwardSMILES.equals("XXX")) ? forwardSMILES : "";
    }


    public PolyketideUnit(PolyketideUnit polyketideUnit)
    {
        this.unitName = polyketideUnit.getUnitName();
        this.forwardSMILES = polyketideUnit.getForwardSMILES();
    }



    /**
     * @return the unitName
     */
    public String getUnitName()
    {
        return unitName;
    }



    /**
     * @param unitName the unitName to set
     */
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }



    /**
     * @return the forwardSMILES
     */
    public String getForwardSMILES()
    {
        return forwardSMILES;
    }



    /**
     * @param forwardSMILES the forwardSMILES to set
     */
    public void setForwardSMILES(String forwardSMILES)
    {
        this.forwardSMILES = forwardSMILES;
    }


    /**
     * @return the image
     */
    public ImageIcon getImage()
    {
        return image;
    }



    /**
     * @param image the image to set
     */
    public void setImage(String imageName)
    {
        try
        {
            String unitFolder = "/resources/";
            InputStream in = getClass().getResourceAsStream(unitFolder+imageName);
            if(in == null)
            {
                in = getClass().getResourceAsStream("/"+imageName);
            }

            //Read from an input stream

            Image tempImage = ImageIO.read(in);
            this.image= new ImageIcon(tempImage);

            int NEW_WIDTH = (int) (this.image.getIconWidth()*0.75);
            int NEW_HEIGHT = (int) (this.image.getIconHeight()*0.75);
            tempImage = tempImage.getScaledInstance( NEW_WIDTH, NEW_HEIGHT,  Image.SCALE_SMOOTH ); 
            
            this.image = new ImageIcon(tempImage);
            
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }



    /**
     * @return the imagePath
     */
    public String getImagePath()
    {
        return imagePath;
    }



    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

	/**
	 * @return the pKSModule
	 */
	public int getPKSModule() {
		return PKSModule;
	}


	/**
	 * @param pKSModule the pKSModule to set
	 */
	public void setPKSModule(int pKSModule) {
		PKSModule = pKSModule;
	}
	
	
	/**
	 * 
	 * Methods that manipulates the SMILES string.
	 * These methods deal with the actual chemical synthesis.
	 * 
	 * */

    public void doNothing()
    {
    	this.setPKSModule(KETOSYNTHASE);
        this.forwardSMILES = "C(=O)C" + forwardSMILES;
    }

    public void addDehydretase()
    {
    	this.setPKSModule(DEHYDRATASE);
        this.forwardSMILES = "=CC" + forwardSMILES;
    }

    public void addKetoReductase()
    {
    	this.setPKSModule(KETOREDUCTASE);
        this.forwardSMILES = "C(O)C" + forwardSMILES;
    }

    public void addEnoylReductase()
    {
    	this.setPKSModule(ENOYL_REDUCTASE);
        this.forwardSMILES = "CC" + forwardSMILES;
    }

}
