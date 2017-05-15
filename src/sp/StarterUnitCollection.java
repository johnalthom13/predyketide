/**
 * @author John Althom A. Mendoza
 * 
 * Class to retrieve the known starter units from the xml file included in the
 * 		application.
 * 
 * */

package sp;

import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.XML_Utils;


public class StarterUnitCollection
{


    public StarterUnitCollection()
    {

        try
        {
            InputStream starterXML = StarterUnitCollection.class.getResourceAsStream("/starterUnits.xml");
            Document doc = XML_Utils.readXml(starterXML);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("aStarterUnit");

            for (int temp = 0; temp < nList.getLength(); temp++)
            {

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {

                    Element eElement = (Element) nNode;


                    Predyketide.starterUnits.add(new PolyketideUnit(getTagValue("name", eElement),
                    		getTagValue("SMILES-forward", eElement),
                    		getTagValue("image", eElement)));


                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String getTagValue(String sTag, Element eElement)
    {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();

        Node nValue = (Node) nlList.item(0);

        return nValue.getNodeValue();
    }
}
