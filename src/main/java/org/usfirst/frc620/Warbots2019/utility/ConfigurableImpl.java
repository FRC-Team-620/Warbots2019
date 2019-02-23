

package org.usfirst.frc620.Warbots2019.utility;

import java.util.ArrayList;

public class ConfigurableImpl implements Configurable 
{

    private ArrayList<Element> configElements;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */

    public ConfigurableImpl()
    {
        configElements = new ArrayList<Element>();
    }
    
    public void addElement(Element e)
    {
        configElements.add(e);
    }

    /**
     * Implementations of everything in the Configurable interface
     */

    /**
     *  returns an ArrayList filled with the name of every element inside of ArrayList<> configElements
     *  in other words, returns a list filled with everything posisble in configuration settings
     *  @return ArrayList<String> "names" names
     */
    public ArrayList<String> getNames()
    {   
        ArrayList<String> names = new ArrayList<String>();
        for(int i = 0; i < configElements.size(); i++)
        {
            names.add(configElements.get(i).name);
        }
        return names;
    }

    /**
     * returns a list with the possible values for a config element s 
     * @return ArrayList<String> "possible values" posValues
     */
    public ArrayList<String> getPossibleValuesForName(String s)
    {
        ArrayList<String> posValues = new ArrayList<String>();
        for(int i = 0; i < configElements.size(); i++)
        {
            if(s.equals(configElements.get(i).name))
            {
                posValues = configElements.get(i).possibleValues;
                break;
            }
        }
        return posValues;
    }
    
    /**
     * returns the comment present for config element s
     * @return String comment
     */
    public String getCommentForName(String s)
    {
        String comment = new String();
        for(int i = 0; i < configElements.size(); i++)
        {
            if(s.equals(configElements.get(i).name))
            {
                comment = configElements.get(i).comment;
                break;
            }
        }
        return comment;
    }
}
