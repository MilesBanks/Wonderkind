// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kCoDriverControllerPort = 1;
  }

  public final class CAN_ID_Constants {
    public static final int kLeftFrontMotorID = 2;
    public static final int kLeftBackMotorID = 58439;
    public static final int kRightFrontMotorID = 1;
    public static final int kRightBackMotorID = 891;
    public static final int kElevatorMotorID = 5;
    public static final int kLeftClawMotorID = 6;
    public static final int kRightClawMotorID = 8;
  }
  public final class SpeedConstants {
    public static final double kUpElevatorSpeed = 0.8;
    public static final double kDownElevatorSpeed = 0.4;
    public static final double kClawOpenSpeed = 0.2;
    public static final double kClawCloseSpeed = 0.2;
    public static final double kClawShiftSpeed = 0.2;
    
}
}
