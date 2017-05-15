/**
 * @author John Althom A. Mendoza
 * 
 * Class collectively representins the variables used in every biosynthesis.
 * 
 * */

package sp;

import java.util.ArrayList;

public class PredyketideWorkspaceVars {

	private ArrayList<PolyketideUnit> workingPolyketideUnits;
	private ArrayList<String> sequenceOfSMILES;
	private PolyketideUnit lastUnit; 		//important variable necessary when retrieving a saved work
	static final int LIMIT = 20;

	public PredyketideWorkspaceVars(ArrayList<PolyketideUnit> workingPolyketideUnits, ArrayList<String> sequenceOfSMILES) {
		this.workingPolyketideUnits = workingPolyketideUnits;
		this.sequenceOfSMILES = sequenceOfSMILES;
	}

	public PredyketideWorkspaceVars() {
		this.workingPolyketideUnits = new ArrayList<PolyketideUnit>();
		this.sequenceOfSMILES = new ArrayList<String>();
	}

	/**
	 * @return the sequenceOfSMILES
	 */
	public ArrayList<String> getSequenceOfSMILES() {
		return sequenceOfSMILES;
	}

	/**
	 * @param sequenceOfSMILES the sequenceOfSMILES to set
	 */
	public void addSequenceOfSMILES(String SMILES) {
		this.sequenceOfSMILES.add(SMILES);
	}

	/**
	 * @return the workingPolyketideUnits
	 */
	public ArrayList<PolyketideUnit> getWorkingPolyketideUnits() {
		return workingPolyketideUnits;
	}

	/**
	 * @param workingPolyketideUnits the workingPolyketideUnits to set
	 */
	public void addWorkingPolyketideUnit(PolyketideUnit workingPolyketideUnit) {
		this.workingPolyketideUnits.add(workingPolyketideUnit);
	}
	
	/**
	 * @param the last workingPolyketideUnits to be removed
	 */
	public PolyketideUnit removeLast(){
		return this.workingPolyketideUnits.remove(this.workingPolyketideUnits.size()-1);
	}
	
	/**
	 * @return Substring of the last working SMILES. This is used
	 * 		after importing a saved synthesis.
	 * */
	public String getWorkingSMILES(){
		if(this.getSequenceOfSMILES().size() > 1){

			String temp = this.getSequenceOfSMILES().get(this.getSequenceOfSMILES().size()-1);
			
			return temp.substring(7);
		}else{
			return "";
		}
	}

	/**
	 * @return the lastUnit
	 */
	public PolyketideUnit getLastUnit() {
		return lastUnit;
	}

	/**
	 * @param lastUnit the lastUnit to set
	 */
	public void setLastUnit(PolyketideUnit lastUnit) {
		this.lastUnit = lastUnit;
	}

	public boolean isLimited() {
		
		return (this.workingPolyketideUnits.size() > LIMIT -1 );
	}

	
}
