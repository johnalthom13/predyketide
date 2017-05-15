package utils;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.Mol2Writer;
import org.openscience.cdk.smiles.SmilesParser;

import com.ibm.icu.util.StringTokenizer;

import sp.PolyketideUnit;
import sp.PredyketideCompoundResult;
import sp.PredyketideWorkspaceVars;


public class FileManipulation {

	static final String DELIMITER = "|"; 
	
	public static void exportSynthesisGUI(PredyketideWorkspaceVars workspaceVariables){
		
		JFileChooser saveSynthesis = new JFileChooser();
		saveSynthesis.setDialogType(JFileChooser.SAVE_DIALOG);
	    FileFilter filter = new FileNameExtensionFilter("pks files", "pksf");
	    saveSynthesis.addChoosableFileFilter(filter);
	    
	   	    
	    int ret = saveSynthesis.showDialog(null, "Export synthesis file");

	    if (ret == JFileChooser.APPROVE_OPTION) {
	    	String filename = saveSynthesis.getSelectedFile().toString();
	    	exportSynthesis(workspaceVariables, filename);

	    	JOptionPane.showMessageDialog(null, "Successfully exported workspace as text file!");
	    }
	}
	

	public static PredyketideWorkspaceVars importSynthesis(){
		JFileChooser openSynthesis = new JFileChooser();
	    FileFilter filter = new FileNameExtensionFilter("pks files", "pksf");
	    openSynthesis.addChoosableFileFilter(filter);

	    int ret = openSynthesis.showDialog(null, "Import synthesis file");

	    if (ret == JFileChooser.APPROVE_OPTION) {
	      File file = openSynthesis.getSelectedFile();
	      try{
	           	Scanner scanner = new Scanner(file);
	            ArrayList<String> SMILES = new ArrayList<String>();
	            ArrayList<PolyketideUnit> PKS = new ArrayList<PolyketideUnit>();
	            
	            String next = "";
	            int counter = 0;
	            
	            
	            String currUnitStringRep = scanner.nextLine();
	            StringTokenizer tokenizer = new StringTokenizer(currUnitStringRep, DELIMITER);
        		
        		int module = Integer.parseInt(tokenizer.nextToken(DELIMITER));
        		String unitName = tokenizer.nextToken(DELIMITER).trim();
        		String unitSMILES = tokenizer.nextToken(DELIMITER).trim();
        		
        		PolyketideUnit lastUnit = new PolyketideUnit(unitName, unitSMILES);
        		lastUnit.setPKSModule(module);
        		
        		PolyketideUnit tempUnit = null;
	            //start of loop reading
	            while(scanner.hasNextLine() && !(next = scanner.nextLine().trim()).isEmpty()){
	            	
	            	
	            	if(counter % 2 == 0){
	            		//System.out.println(next.trim());
	            		SMILES.add(next.trim());
	            	}else{
	            		tokenizer = new StringTokenizer(next, DELIMITER);
	            		
	            		module = Integer.parseInt(tokenizer.nextToken(DELIMITER));
	            		unitName = tokenizer.nextToken(DELIMITER).trim();
	            		unitSMILES = tokenizer.nextToken(DELIMITER).trim();
	            		
	            		tempUnit = new PolyketideUnit(unitName, unitSMILES);
	            		tempUnit.setPKSModule(module);
	            		PKS.add(tempUnit);
	            		
	            		//System.out.println("mod: " + module + " uname: " + unitName + " SMILES: " + unitSMILES);
	            	}
	            	
	            	
	            	counter++;
	            }
	            scanner.close();
	            
	            PredyketideWorkspaceVars workspaceToReturn = new PredyketideWorkspaceVars(PKS, SMILES);
	            workspaceToReturn.setLastUnit(lastUnit);
	            
	            return workspaceToReturn;
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }catch(@SuppressWarnings("hiding") IOException e){
	            e.printStackTrace();
	        }
	    }
		return null;
	}
	

public static void exportImageGUI(JPanel synthesisMainPanel, int WIDTH, int HEIGHT) {
		
		JFileChooser saveImage = new JFileChooser();
		saveImage.setDialogType(JFileChooser.SAVE_DIALOG);
	    FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg");
	    saveImage.addChoosableFileFilter(filter);
	    
	    int ret = saveImage.showDialog(null, "Save workspace as image");

	    if (ret == JFileChooser.APPROVE_OPTION) {
	    	String filename = saveImage.getSelectedFile().toString();
	    	if(filename.indexOf(".jpg") < 0){
	    		filename += ".jpg";
	    	}
	    	
	    	exportImage(synthesisMainPanel, WIDTH, HEIGHT, filename);
	      
	    	JOptionPane.showMessageDialog(null, "Successfully exported workspace as image!");
	    }
	}

	public static File exportImage(JPanel synthesisMainPanel, int WIDTH, int HEIGHT, String filename) {
	

	WIDTH += 150; //TODO fix bakit kapos
	
	File file = new File(filename);
	
    Dimension size = new Dimension(WIDTH, HEIGHT);
    BufferedImage image = (BufferedImage)synthesisMainPanel.createImage(size.width, size.height);
    Graphics g = image.getGraphics();
    synthesisMainPanel.paint(g);
    g.dispose();
    try   {
        ImageIO.write(image, "jpg", file);
      }
      catch (IOException e){
        e.printStackTrace();
      }
      
    return file;
    
	}


	public static ArrayList<IAtomContainer> importReferenceData() {
		JFileChooser openSynthesis = new JFileChooser();
	    FileFilter filter = new FileNameExtensionFilter("Reference text collection (.txt)", "txt");
	    openSynthesis.addChoosableFileFilter(filter);

	    int ret = openSynthesis.showDialog(null, "Import synthesis file");

	    if (ret == JFileChooser.APPROVE_OPTION) {
	      File file = openSynthesis.getSelectedFile();
	      
	      ArrayList<IAtomContainer> newRefData = new ArrayList<IAtomContainer>();
	      
	      try{
	          Scanner refDataReader = new Scanner(file);
	          SmilesParser smparser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
	          while(refDataReader.hasNext()){
	        	  String SMILES = refDataReader.nextLine().trim();
	        	  newRefData.add(smparser.parseSmiles(SMILES));
	          }
	          
	          return newRefData;
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }catch(@SuppressWarnings("hiding") IOException e){
	            e.printStackTrace();
	        } catch (InvalidSmilesException e) {
				e.printStackTrace();
			}
	    }
		return null;
	}
	
	
	public static File exportSynthesis(PredyketideWorkspaceVars workspaceVariables, String filename){
		
		PolyketideUnit lastUnit = workspaceVariables.getLastUnit();
				
		ArrayList<String> SMILES = workspaceVariables.getSequenceOfSMILES();
	    ArrayList<PolyketideUnit> PKSUnits = workspaceVariables.getWorkingPolyketideUnits();
	    
	    if(filename.indexOf(".pksf") < 0){
    		filename += ".pksf";
    	}
    	
    	File file = new File(filename);
    	try {
    		PrintWriter pw = new PrintWriter(file);
    		
    		/**
    		 * Writes as the first line, the last unit of the synthesis.
    		 * */
    		pw.println(lastUnit.getPKSModule() + DELIMITER + lastUnit.getUnitName() + DELIMITER + lastUnit.getForwardSMILES());
    	    
    		for(int j = 0; j < SMILES.size(); j++){
    	    	
    	    	pw.println(SMILES.get(j));
    	    	
    	    	if(j < PKSUnits.size()){
    	    		PolyketideUnit tempUnit = PKSUnits.get(j);
    	    		pw.println(tempUnit.getPKSModule() + DELIMITER + tempUnit.getUnitName() + DELIMITER +tempUnit.getForwardSMILES());
    	    	}
    	    }
    	    
    	    pw.close();
    	    pw.flush();
    	    return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(@SuppressWarnings("hiding") IOException e){
            e.printStackTrace();
        }
		return file;
      
	}
	
	public static void exportPredictionAsZip(PredyketideCompoundResult prediction, 
			PredyketideWorkspaceVars workspace, JPanel synthesisMainPanel, int WIDTH, int HEIGHT){
		
		JFileChooser savePrediction = new JFileChooser();
		savePrediction.setDialogType(JFileChooser.SAVE_DIALOG);
	    FileFilter filter = new FileNameExtensionFilter("Polyketide zip file", "pkzip");
	    savePrediction.addChoosableFileFilter(filter);
	    
	    int ret = savePrediction.showDialog(null, "Save this prediction");

	    if (ret == JFileChooser.APPROVE_OPTION) {
	    	String filename = savePrediction.getSelectedFile().toString();
	    	String fileNameTemplate = "prediction";
	    	if(filename.indexOf(".pk.zip") < 0){
	    		filename += ".pk.zip";
	    	}
	    	

	    	byte[] buffer = new byte[1024];
	    	
			/**
			 * 1. Synthesis panel pksf file.
			 * 2. Synthesis panel as image.
			 * 3. SMILES representation of prediction.
			 * 4. MolFile representation of prediction.
			 * 5. Descriptors of the prediction
			 * 6. Image of the prediction.
			 * */
			File[] sourceFiles = new File[6];
			sourceFiles[0] = exportSynthesis(workspace, fileNameTemplate+"_synthesis.pksf");
	    	sourceFiles[1] = exportImage(synthesisMainPanel, WIDTH, HEIGHT, fileNameTemplate+"_synthesis.jpg");
			sourceFiles[2] = exportAsSMILES(prediction.getResultSMILES(), fileNameTemplate+".smi");
			sourceFiles[3] = exportAsMolFile(prediction.getPredictedMolecule(), fileNameTemplate+".mol");
			try {
				sourceFiles[4] = exportPredictionDescriptor(prediction.getDescriptors(), fileNameTemplate+"_descr.txt");
			} catch (CDKException e1) {
				e1.printStackTrace();
			}
	    	sourceFiles[5] = exportPredictionAsImage((BufferedImage) prediction.getImage(), fileNameTemplate+".jpg");
	    	
	    	 try {
				FileOutputStream fout = new FileOutputStream(filename);
				ZipOutputStream zout = new ZipOutputStream(fout);
				
				for(int l = 0; l < sourceFiles.length; l++){
					FileInputStream fin = new FileInputStream(sourceFiles[l]);
					zout.putNextEntry(new ZipEntry(sourceFiles[l].getName()));
					  int length;
					  
                      while((length = fin.read(buffer)) > 0)
                      {
                         zout.write(buffer, 0, length);
                      }

                      /*
                       * After writing the file to ZipOutputStream, use
                       *
                       * void closeEntry() method of ZipOutputStream class to
                       * close the current entry and position the stream to
                       * write the next entry.
                       */

                       zout.closeEntry();

                       //close the InputStream
                       fin.close();
                       sourceFiles[l].delete();
				}
				
				 zout.close();
				 zout.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	
	    	JOptionPane.showMessageDialog(null, "Successfully exported prediction!");
	    }
		
	}


	private static File exportPredictionAsImage(BufferedImage image, String filename) {
		
		File file = new File(filename);
		 try   {
		        ImageIO.write(image, "jpg", file);
		      }
		      catch (IOException e){
		        e.printStackTrace();
		      }
		      
		return file;
	}


	private static File exportPredictionDescriptor(String descriptors, String file) {
		File temp = new File(file);
		try{
			PrintWriter pw = new PrintWriter(temp);
			pw.println(descriptors);
			pw.close();
			pw.flush();
		}catch(Exception e){
			
		}
		return temp;
	}


	private static File exportAsMolFile(IAtomContainer predictedMolecule, String file) {
		File temp = new File(file);
		try{
			Writer pw = new PrintWriter(temp);
			Mol2Writer molwriter = new Mol2Writer(pw);
			molwriter.writeMolecule(predictedMolecule);
			molwriter.close();
			pw.close();
			pw.flush();
		}catch(Exception e){
			
		}
		return temp;
	}


	private static File exportAsSMILES(String resultSMILES, String file) {
		File temp = new File(file);
		try{
			PrintWriter pw = new PrintWriter(temp);
			pw.println(resultSMILES);
			pw.close();
			pw.flush();
		}catch(Exception e){
			
		}
		return temp;
	}
	
}
