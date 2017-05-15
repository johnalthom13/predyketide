/**
 * @author John Althom A. Mendoza
 * 
 * Class to retrieve the reference data. By default, the attached list -- synthetic.txt and 
 * 		natprod.txt -- are processed as reference data.
 * 
 * TODO Catch if may error ang SMILES
 * 
 * */

package sp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;

@SuppressWarnings("serial")
public class ReferenceData extends ArrayList<IAtomContainer>{
	
	private boolean isNaturalProduct;
	
	public ReferenceData(boolean isNaturalProduct){
		this.isNaturalProduct = isNaturalProduct;
		if(this.isNaturalProduct)
			createReferenceDataObject("natprod.txt");
		else
			createReferenceDataObject("synthetic.txt");	
	}
	
	private void createReferenceDataObject(String path){
		
		try {
			
			InputStream in = getClass().getResourceAsStream("/resources/" + path);
			
	        if(in == null)
	        {
	            in = getClass().getResourceAsStream("/"+path);
	        }
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        String referenceSMILE;
			while ((referenceSMILE = br.readLine()) != null) {
				//TODO fix kung bakit may lumalabas na print 3
			    IAtomContainer referenceMolecule = Predyketide.smpars.parseSmiles(referenceSMILE);
			    
			    this.add(referenceMolecule);
			 }
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidSmilesException e) {
			e.printStackTrace();
		}
	}
	
}
