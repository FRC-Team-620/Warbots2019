package org.usfirst.frc620.Warbots2019.utility;

public class Angle implements Comparable<Angle>
{
	/**
	 * <p>
	 * Commonly used angles for convenience
	 * </p>
	 * 
	 * ZERO: 0 degrees
	 * <br>
	 * RIGHT: 90 degrees
	 * <br>
	 * STRAIGHT: 180 degrees
	 * <br>
	 * INVERSE_RIGHT: -90 or 270 degrees
	 */
	public final static Angle ZERO = new Angle(0), RIGHT = new Angle(.25), REVERSE = new Angle(.5), LEFT = new Angle(-.25);

	/**
	 * This is the actual value of the angle, stored internally in "turns" --
	 * one turn is 360 degrees or 2pi radians
	 */
	private final double value;

	/**
	 * <p>
	 * Creates an angle with the given measurement in turns.
	 * </p>
	 * 
	 * <p>
	 * Users of the class should use the factory method for their preferred unit
	 * of angle measurement instead of
	 * this constructor.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param value
	 *            The value of the angle, in turns
	 *            </p>
	 */
	private Angle(double value)
	{
		this.value = value;
	}

	/**
	 * <p>
	 * An internal factory method for creating angles in different units. It
	 * automatically wraps the
	 * value so that it is between -1/2 and 1/2 turns.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param val
	 *            The value of the angle to be created, measured in whatever
	 *            unit is specified by k.
	 *            This can be reflex, negative, or multiple loops around, and
	 *            this method will automatically convert
	 *            it to turns for internal storage and wrap it between -1/2 and
	 *            1/2
	 *            </p>
	 * 
	 *            <p>
	 * @param k
	 *            The unit to convert to. More specifically, this is the measure
	 *            of one complete turn in that
	 *            unit. For example, to obtain an angle of measure n degrees,
	 *            one would call Angle.from(n, 360). To
	 *            use radians, one would call Angle.from(n, 2 * Math.PI). This
	 *            method assumes a linear mapping from
	 *            the given unit to turns, so nonlinear measures of angle, such
	 *            as slope, will have to provide their
	 *            own means of conversion.
	 *            </p>
	 * 
	 *            <p>
	 * @return An angle object representing the given value, as measured in the
	 *         given unit. Note that no
	 *         information about the unit is retained; this angle's measure can
	 *         be retrieved in any unit, so instances
	 *         of the Angle class should be thought of as representing a certain
	 *         amount of arc rather than a measure
	 *         in any specific unit. More specifically, from(90, 360) will
	 *         produce an angle measuring 90 degrees, which
	 *         is exactly identical to the result of calling from(Math.PI / 2, 2
	 *         * Math.PI) to produce an angle measuring
	 *         pi/2 radians or from(0.25, 1) to produce an angle measure 0.25
	 *         turns.
	 *         </p>
	 */
	private static Angle from(double val, double k)
	{
		val /= k;

		// This code takes care of all of the weird modulo stuff to wrap the angle between -1/2 and 1/2 turns.
        // If you don't question how it works, it can never hurt you
        val += .5;
        val -= (int) val;
        val -= .5;
        if (val < 0) 
            val += 1;
		
		return new Angle(val);
	}

	/**
	 * <p>
	 * Converts the internal angle into the unit specified by k. Since the
	 * internal angle is wrapped between
	 * -1/2 and 1/2 turns on initialization, this method should always return a
	 * result between -k/2 and k/2.
	 * </p>
	 * 
	 * <p>
	 * See {@link Angle#toUnsigned(double)}
	 * </p>
	 * 
	 * <p>
	 * I know this method may not seem necessary, but if we ever wish to modify
	 * the internal structure of
	 * Angle to store angles in a different unit or wrap them on retrieval
	 * instead of on initialization,
	 * we'll be very happy we used this method, so we don't have to go through
	 * and manually change every
	 * one of the methods below.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param k
	 *            The unit to convert to. More specifically, this is the measure
	 *            of one complete turn in that
	 *            unit. For example, to measure the angle in degrees, one would
	 *            call to(360). To use radians, one would
	 *            call to(2 * Math.PI). This method assumes a linear mapping
	 *            from the given unit to turns, so nonlinear
	 *            measures of angle, such as slope, will have to provide their
	 *            own means of conversion.
	 *            </p>
	 * 
	 *            <p>
	 * @return The measure of this angle in the unit specified by k
	 *         </p>
	 */
	private double to(double k)
	{
		return value * k;
	}

	/**
	 * <p>
	 * Converts the internal angle to the unit specified by k but measures
	 * between 0 and k rather than
	 * between -k/2 and k/2. For instance, {@link Angle#INVERSE_RIGHT}.to(360)
	 * would return
	 * -90, but {@link Angle#INVERSE_RIGHT}.toUnsigned(360) would return 270.
	 * </p>
	 * 
	 * <p>
	 * See {@link Angle#to(double)}
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param k
	 *            The unit to convert to. More specifically, this is the measure
	 *            of one complete turn in that
	 *            unit. For example, to measure the angle in degrees, one would
	 *            call to(360). To use radians, one would
	 *            call to(2 * Math.PI). This method assumes a linear mapping
	 *            from the given unit to turns, so nonlinear
	 *            measures of angle, such as slope, will have to provide their
	 *            own means of conversion.
	 *            </p>
	 * 
	 *            <p>
	 * @return The measure of this angle in the unit specified by k wrapped
	 *         between 0 and k
	 *         </p>
	 */
	private double toUnsigned(double k)
	{
		//Scales by k and adds one complete turn to negative values to get the reflex angle
		return value * k + (value < 0 ? k : 0);
	}

	/**
	 * <p>
	 * Factory method for creating an angle from a measure in degrees
	 * </p>
	 * 
	 * <p>
	 * There are 360 degrees in one complete turn.
	 * </p>
	 * 
	 * <p>
	 * Instances of Angle do not retain units. The different factory methods
	 * refer only to the unit in which
	 * the initial value is given. For instance, if a sensor yields a value in
	 * degrees, one should create an
	 * Angle object from that value with fromDegrees(), but the resulting Angle
	 * instance can be read in any
	 * unit and is indistinguishable from one created by any of the other
	 * factory methods.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param degrees
	 *            The measure of the angle to be created, in degrees
	 *            </p>
	 * 
	 *            <p>
	 * @return An instance of Angle with the given measure (in degrees)
	 *         </p>
	 */
	public static Angle fromDegrees(double degrees)
	{
		return from(degrees, 360);
	}

	/**
	 * <p>
	 * Factory method for creating an angle from a measure in radians
	 * </p>
	 * 
	 * <p>
	 * There are 2pi radians in one complete turn.
	 * </p>
	 * 
	 * <p>
	 * Instances of Angle do not retain units. The different factory methods
	 * refer only to the unit in which
	 * the initial value is given. For instance, if a sensor yields a value in
	 * radians, one should create an
	 * Angle object from that value with fromRadians(), but the resulting Angle
	 * instance can be read in any
	 * unit and is indistinguishable from one created by any of the other
	 * factory methods.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param radians
	 *            The measure of the angle to be created, in radians
	 *            </p>
	 * 
	 *            <p>
	 * @return An instance of Angle with the given measure (in radians)
	 *         </p>
	 */
	public static Angle fromRadians(double radians)
	{
		return from(radians, 2 * Math.PI);
	}

	/**
	 * <p>
	 * Factory method for creating an angle from a measure in turns
	 * </p>
	 * 
	 * <p>
	 * 1 turn is equivalent to one complete rotation, or 360 degrees.
	 * </p>
	 * 
	 * <p>
	 * Instances of Angle do not retain units. The different factory methods
	 * refer only to the unit in which
	 * the initial value is given. For instance, if a sensor yields a value in
	 * turns, one should create an
	 * Angle object from that value with fromTurns(), but the resulting Angle
	 * instance can be read in any
	 * unit and is indistinguishable from one created by any of the other
	 * factory methods.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param turns
	 *            The measure of the angle to be created, in turns
	 *            </p>
	 * 
	 *            <p>
	 * @return An instance of Angle with the given measure (in turns)
	 *         </p>
	 */
	public static Angle fromTurns(double turns)
	{
		return from(turns, 1);
	}

	/**
	 * <p>
	 * Factory method for creating an angle from a measure in gradians
	 * </p>
	 * 
	 * <p>
	 * There are 400 gradians in one complete turn.
	 * </p>
	 * 
	 * <p>
	 * Instances of Angle do not retain units. The different factory methods
	 * refer only to the unit in which
	 * the initial value is given. For instance, if a sensor yields a value in
	 * gradians, one should create an
	 * Angle object from that value with fromGradians(), but the resulting Angle
	 * instance can be read in any
	 * unit and is indistinguishable from one created by any of the other
	 * factory methods.
	 * </p>
	 * 
	 * <p>
	 * 
	 * @param gradians
	 *            The measure of the angle to be created, in gradians
	 *            </p>
	 * 
	 *            <p>
	 * @return An instance of Angle with the given measure (in gradians)
	 *         </p>
	 */
	public static Angle fromGradians(double gradians)
	{
		return from(gradians, 400);
	}

	//TODO: document the rest of these methods
	
	public static Angle fromSlope(double m)
	{
		return new Angle(Math.atan(m) / (2 * Math.PI));
	}

	public static Angle fromRiseAndRun(double rise, double run)
	{
		return new Angle(Math.atan2(rise, run) / (2 * Math.PI));
	}

	public double measureDegrees()
	{
		return to(360);
	}

	public double measureDegreesUnsigned()
	{
		return toUnsigned(360);
	}

	public double measureRadians()
	{
		return to(2 * Math.PI);
	}

	public double measureRadiansUnsigned()
	{
		return toUnsigned(2 * Math.PI);
	}

	public double measureTurns()
	{
		return to(1);
	}

	public double measureTurnsUnsigned()
	{
		return toUnsigned(1);
	}

	public double measureGradians()
	{
		return to(400);
	}

	public double measureGradiansUnsigned()
	{
		return toUnsigned(400);
	}

	public double measureSlope()
	{
		return Math.tan(measureRadians());
	}

	public static Angle difference(Angle a, Angle b)
	{
		return from(a.value - b.value, 1);
	}

	public Angle minus(Angle other)
	{
		return difference(this, other);
	}

	public static Angle sum(Angle a, Angle b)
	{
		return from(a.value + b.value, 1);
	}

	public Angle plus(Angle other)
	{
		return sum(this, other);
	}

	public static Angle product(Angle a, double scalar)
	{
		return new Angle(a.value * scalar);
	}
	
	public Angle times(double scalar)
	{
		return product(this, scalar);
	}
	
	public double sin()
	{
		return Math.sin(measureRadians());
	}
	
	public double cos()
	{
		return Math.cos(measureRadians());
	}
	
	public double tan()
	{
		return Math.tan(measureRadians());
	}
	
	public Angle absoluteValue()
	{
		return new Angle(Math.abs(value));
	}
	
	@Override
	public String toString()
	{
		return Math.round(measureDegrees() * 100000) / 100000. + " degrees";
	}

	@Override
	public boolean equals(Object other)
	{
		if (other instanceof Angle) 
			return value == ((Angle) other).value;
		return false;
	}

	@Override
	public int hashCode()
	{
		return Double.hashCode(value);
	}

	@Override
	public int compareTo(Angle o)
	{
		return Double.compare(value, o.value);
	}
}