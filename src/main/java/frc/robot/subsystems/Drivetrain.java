// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import javax.swing.text.Position;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.DrivetrainPID;

public class Drivetrain extends SubsystemBase {
  private final static CANSparkMax LeftFrontMotor = new CANSparkMax(Constants.CAN_ID_Constants.kLeftFrontMotorID, MotorType.kBrushless);
  private final static CANSparkMax LeftBackMotor = new CANSparkMax(Constants.CAN_ID_Constants.kLeftBackMotorID, MotorType.kBrushless);
  private final static CANSparkMax RightFrontMotor = new CANSparkMax(Constants.CAN_ID_Constants.kRightFrontMotorID, MotorType.kBrushless);
  private final static CANSparkMax RightBackMotor = new CANSparkMax(Constants.CAN_ID_Constants.kRightBackMotorID, MotorType.kBrushless);

  private final RelativeEncoder leftRelativeEncoder = LeftFrontMotor.getEncoder();
  private final RelativeEncoder righRelativeEncoder = RightFrontMotor.getEncoder();
//we can add back encoders but we arn't using them.
  private final MotorControllerGroup LeftMotorGroup = new MotorControllerGroup(LeftFrontMotor, LeftBackMotor);
  private final MotorControllerGroup RightMotorGroup = new MotorControllerGroup(RightFrontMotor, RightBackMotor);

  private final DifferentialDrive Drive = new DifferentialDrive(LeftMotorGroup, RightMotorGroup);

  private static double leftMotorPosition;
  private static double rightMotorPosition;
  private static double leftMotorSpeed;
  private static double rightMotorSpeed;
  private static double leftMotorVelocity;
  private static double rightMotorVelocity;
  
  /** Creates a new Drivetrain. */
  public Drivetrain() {

    LeftMotorGroup.setInverted(true); // Invert left motor group
    RightMotorGroup.setInverted(false);

    LeftFrontMotor.getEncoder().setPosition(0); // Set default position = 0
    RightFrontMotor.getEncoder().setPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    leftMotorPosition = LeftFrontMotor.getEncoder().getPosition();
    rightMotorPosition = -RightFrontMotor.getEncoder().getPosition(); // right speed negative
    leftMotorSpeed = LeftFrontMotor.get();
    rightMotorSpeed = RightFrontMotor.get();
    leftMotorVelocity = LeftFrontMotor.getEncoder().getVelocity(); 
    rightMotorVelocity = -RightFrontMotor.getEncoder().getVelocity(); // right velocity negative
  
    SmartDashboard.putNumber(("leftMotor Position"), leftMotorPosition);
    SmartDashboard.putNumber(("rightMotor Position"), rightMotorPosition);
    SmartDashboard.putNumber(("leftMotor Velocity"), leftMotorVelocity);
    SmartDashboard.putNumber(("rightMotor Velocity"), rightMotorVelocity);
    SmartDashboard.putNumber(("leftMotor Speed"), leftMotorSpeed);
    SmartDashboard.putNumber(("rightMotor Speed"), rightMotorSpeed);
  }

    public void OurDrive(double FWD, double ROT){
      Drive.arcadeDrive(FWD, ROT);
    }

    public void ResetEncoders(){
      leftRelativeEncoder.setPosition(0);
      righRelativeEncoder.setPosition(0);
    }
    public void driveForwardCommand(double speed){
      LeftMotorGroup.set(speed);
      RightMotorGroup.set(speed);
    }
  
    public Command driveForward(double speed){
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

    public static void setmotor(Double output) {
      LeftFrontMotor.set(output);
      RightFrontMotor.set(-output); // right speed has to be negative when not in rightmotorgroup
      LeftBackMotor.set(output);
      RightBackMotor.set(-output);
    }

    public void cancelDrivePID(Boolean output) {
      DrivetrainPID.stopDrivePID = output;
    }

    public Command cancelDrivePIDCommand() {
      return new StartEndCommand(() -> this.cancelDrivePID(false), () -> this.cancelDrivePID(false), this);
    }
    
}
