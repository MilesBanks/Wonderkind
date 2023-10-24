// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  
  //private final static CANSparkMax LeftFrontMotor = new CANSparkMax(Constants.CAN_ID_Constants.kLeftFrontMotorID, MotorType.kBrushless);
  //private final static CANSparkMax LeftBackMotor = new CANSparkMax(Constants.CAN_ID_Constants.kLeftBackMotorID, MotorType.kBrushless);
  //private final static CANSparkMax RightFrontMotor = new CANSparkMax(Constants.CAN_ID_Constants.kRightFrontMotorID, MotorType.kBrushless);
  //private final static CANSparkMax RightBackMotor = new CANSparkMax(Constants.CAN_ID_Constants.kRightBackMotorID, MotorType.kBrushless);

  private final static WPI_TalonSRX LeftFrontMotor = new WPI_TalonSRX(Constants.CAN_ID_Constants.kLeftFrontMotorID);
  private final static WPI_TalonSRX LeftBackMotor = new WPI_TalonSRX(Constants.CAN_ID_Constants.kLeftBackMotorID);
  private final static WPI_TalonSRX RightFrontMotor = new WPI_TalonSRX(Constants.CAN_ID_Constants.kRightFrontMotorID);
  private final static WPI_TalonSRX RightBackMotor = new WPI_TalonSRX(Constants.CAN_ID_Constants.kRightBackMotorID);

  ///private final RelativeEncoder leftRelativeEncoder = LeftFrontMotor.getEncoder();
  ///private final RelativeEncoder righRelativeEncoder = RightFrontMotor.getEncoder();
  //we can add back encoders but we arn't using them.
  private final MotorControllerGroup LeftMotorGroup = new MotorControllerGroup(LeftFrontMotor, LeftBackMotor);
  private final MotorControllerGroup RightMotorGroup = new MotorControllerGroup(RightFrontMotor, RightBackMotor);

  private final DifferentialDrive Drive = new DifferentialDrive(LeftMotorGroup, RightMotorGroup);

  private static double leftMotorPosition;
  private static double rightMotorPosition;
  private static double leftMotorVelocity;
  private static double rightMotorVelocity;

  public static boolean isArcadeDrive;
  
  /** Creates a new Drivetrain. */
  public Drivetrain() {
    LeftMotorGroup.setInverted(true); // Invert left motor group
    RightMotorGroup.setInverted(false);

    //Can't we just invert encoders here instead of flipping them with negative signs down below?
    //LeftFrontMotor.getEncoder().setInverted(true);
    //RightFrontMotor.getEncoder().setInverted(false);

    //Convert encoder postion from revolutions to Meters
    
    //Convert encoder velocity from RPM to Meters/second

    LeftFrontMotor.setSelectedSensorPosition(0); // Set default position = 0
    RightFrontMotor.setSelectedSensorPosition(0);

    Drive.setSafetyEnabled(false); // Disables safety for the DifferentialDrive
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    leftMotorPosition = (LeftFrontMotor.getSelectedSensorPosition()*Constants.DrivetrainConstants.TicksToMeters);
    rightMotorPosition = (-RightFrontMotor.getSelectedSensorPosition()*Constants.DrivetrainConstants.TicksToMeters); // right speed negative
    leftMotorVelocity = (LeftFrontMotor.getSelectedSensorVelocity()*Constants.DrivetrainConstants.TicksToMeters); 
    rightMotorVelocity = (-RightFrontMotor.getSelectedSensorVelocity()*Constants.DrivetrainConstants.TicksToMeters); // right velocity negative
  
    SmartDashboard.putNumber(("leftMotor Position"), leftMotorPosition);
    SmartDashboard.putNumber(("rightMotor Position"), rightMotorPosition);
    SmartDashboard.putNumber(("leftMotor Velocity"), leftMotorVelocity);
    SmartDashboard.putNumber(("rightMotor Velocity"), rightMotorVelocity);

    if (isArcadeDrive == false) { // call Drive.feed() when we need to
      Drive.feed();
    }
  }

  public void OurDrive(double FWD, double ROT, double slowMo, double rotationSlowMo){
    Drive.arcadeDrive(FWD/slowMo, (ROT/1.25)/(rotationSlowMo));
  }

  public void ResetEncoders(){
    //leftRelativeEncoder.setPosition(0);
    //righRelativeEncoder.setPosition(0);

  LeftFrontMotor.setSelectedSensorPosition(0);
  LeftBackMotor.setSelectedSensorPosition(0);
  RightFrontMotor.setSelectedSensorPosition(0);
  RightBackMotor.setSelectedSensorPosition(0);


  }

  public void driveForward(double speed){
    LeftMotorGroup.set(speed);
    RightMotorGroup.set(speed);
  }
  
  public Command driveForwardCommand(double speed){
    return new StartEndCommand(() -> this.driveForward(speed), () -> this.driveForward(0.0), this);
  }

  public void driveBackward(double speed){
    LeftMotorGroup.set(-speed);
    RightMotorGroup.set(-speed);
  }
  
  public Command driveBackwardCommand(double speed){
    return new StartEndCommand(() -> this.driveBackward(speed), () -> this.driveBackward(0.0), this);
  }

  public static double getDrivetrainPosition() {
    return (leftMotorPosition + rightMotorPosition)/2;
  }

  public static void setmotor(double output) {
    LeftFrontMotor.set(output);
    RightFrontMotor.set(-output); // right speed has to be negative when not in rightmotorgroup
    LeftBackMotor.set(output);
    RightBackMotor.set(-output);
  }
}
