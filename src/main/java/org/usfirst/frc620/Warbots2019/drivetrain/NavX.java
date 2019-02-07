/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc620.Warbots2019.drivetrain;

import java.util.function.Supplier;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc620.Warbots2019.utility.Angle;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;

/**
 * Add your docs here.
 */

public class NavX {

    AHRS navxBoard;

    public enum Port //Wraps three different navx port enums into one
    {
        SPIOnboardCS0(() -> new AHRS(SPI.Port.kOnboardCS0)),
        SPIOnboardCS1(() -> new AHRS(SPI.Port.kOnboardCS1)), 
        SPIOnboardCS2(() -> new AHRS(SPI.Port.kOnboardCS2)), 
        SPIOnboardCS3(() -> new AHRS(SPI.Port.kOnboardCS3)), 
        SPIMXP(() -> new AHRS(SPI.Port.kMXP)),
        I2COnboard(() -> new AHRS(I2C.Port.kOnboard)),
        I2CMXP(() -> new AHRS(I2C.Port.kMXP)),
        SerialOnboard(() -> new AHRS(SerialPort.Port.kOnboard)),
        SerialMXP(() -> new AHRS(SerialPort.Port.kMXP)),
        SerialUSB(() -> new AHRS(SerialPort.Port.kUSB)),
        SerialUSB1(() -> new AHRS(SerialPort.Port.kUSB1)),
        SerialUSB2(() -> new AHRS(SerialPort.Port.kUSB2));

        private Supplier<AHRS> navxBoardConstructor;

        private Port(Supplier<AHRS> navxBoardConstructor)
        {
            this.navxBoardConstructor = navxBoardConstructor;
        }

        public AHRS constructNavxBoard()
        {
            return navxBoardConstructor.get();
        }
    }

    public NavX(Port port)
    {
        navxBoard = port.constructNavxBoard();
    }

    public Angle getAngle() {
        double yaw = navxBoard.getAngle();
        System.out.println("Yaw: " + yaw);
		return Angle.fromDegrees(yaw);
    }

    public void reset()
    {
        navxBoard.reset();
    }
}

