package org.usfirst.frc620.Warbots2019.utility;

import java.util.ArrayList;

/**
 * Configurable is an interface that different objects can implement
 * when they want to advertise the configuration data they care about.
 * A Configurable is then added to a list and passed into the ControlReader
 * so that ControlReader can dump a file of all the possible configuration
 * values that are possible in the system.
 */
public interface Configurable
{
    /**
     * Utility set of things that constitute a description of a configurable
     * item.
     */
    public class Element
    {
        public Element(String n, String c, ArrayList<String> pv)
        {
            name = n;
            comment = c;
            possibleValues = pv;
        }
        public String name;
        public String comment;
        public ArrayList<String> possibleValues;
    }

    /**
     * Provides a list of the names of the configuration settings.
     * @return
     */
    abstract public ArrayList<String> getNames();

    /**
     * Returns a comments string for a named configuration name. This is
     * used to describe to the human what the configuration setting does.
     * @param name
     * @return
     */
    abstract public String getCommentForName(String name);

    /**
     * Return a string showing units and/or possible values/range of values,
     * or null if none for the configuration name.
     * @param name
     * @return ArrayList<String>
     */
    abstract public ArrayList<String> getPossibleValuesForName(String name);
}