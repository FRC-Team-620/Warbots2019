/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.utility;

import java.util.function.Consumer;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * Add your docs here.
 */
public class TableProperty
{
    public static enum PropertyType
    {
        Double, String, Boolean
    }

    private PropertyType type;
    private String name;
    private Supplier<Object> getter;
    private Consumer<Object> setter;

    private boolean write;
    private Object cachedValue;

    public TableProperty(PropertyType type, String name, Supplier<Object> getter, Consumer<Object> setter)
    {
        this.type = type;
        this.name = name;
        this.getter = getter;
        this.setter = setter;

        write = false;
    }

    public PropertyType getType()
    {
        return this.type;
    }

    public String getName()
    {
        return name;
    }

    public synchronized void update()
    {
        if (write)
        {
            setter.accept(cachedValue);
            write = false;
        }
        else
            cachedValue = getter.get();
    }

    public Object get()
    {
        if (cachedValue == null)
            switch(type) {
                case String:
                    cachedValue = "";
                    break;
                case Double:
                    cachedValue = 0.0;
                    break;
                case Boolean:
                    cachedValue = false;
                    break;
            }
        return cachedValue;
    }

    public synchronized void set(Object value)
    {
        cachedValue = value;
        write = true;
    }

    public void addTo(SendableBuilder builder)
    {
        switch(type) {
            case String:
                builder.addStringProperty(name, () -> (String) this.get(), value -> this.set(value));
                break;
            case Double:
                builder.addDoubleProperty(name, () -> ((Number) this.get()).doubleValue(), value -> this.set(value));
                break;
            case Boolean:
                builder.addBooleanProperty(name, () -> (Boolean) this.get(), value -> this.set(value));
                break;
        }
    }

    @Override
    public boolean equals(Object obj) 
    {
        if(obj instanceof TableProperty)
            return name.equals(((TableProperty) obj).name);
        return false;
    }

    @Override
    public int hashCode() 
    {
        return name.hashCode();
    }
}
