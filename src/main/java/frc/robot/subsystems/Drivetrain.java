// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  private final CANSparkMax LeftFrontMotor = new CANSparkMax(Constants.CAN_ID_Constants.kLeftFrontMotorID, MotorType.kBrushless);
  private final CANSparkMax LeftBackMotor = new CANSparkMax(Constants.CAN_ID_Constants.kLeftBackMotorID, MotorType.kBrushless);
  private final CANSparkMax RightFrontMotor = new CANSparkMax(Constants.CAN_ID_Constants.kRightFrontMotorID, MotorType.kBrushless);
  private final CANSparkMax RightBackMotor = new CANSparkMax(Constants.CAN_ID_Constants.kRightBackMotorID, MotorType.kBrushless);

  private final RelativeEncoder leftRelativeEncoder = LeftFrontMotor.getEncoder();
  private final RelativeEncoder righRelativeEncoder = RightFrontMotor.getEncoder();
//we can add back encoders but we arn't using them.
  MotorControllerGroup LeftMotorGroup = new MotorControllerGroup(LeftFrontMotor, LeftBackMotor);
  MotorControllerGroup RightMotorGroup = new MotorControllerGroup(RightFrontMotor, RightBackMotor);

  private final DifferentialDrive Drive = new DifferentialDrive(LeftMotorGroup, RightMotorGroup);
  
  
  /** Creates a new Drivetrain. */
  public Drivetrain() {

    LeftFrontMotor.setInverted(true);
    RightFrontMotor.setInverted(false);
// add back if being bad
  }

    public void OurDrive(double FWD, double ROT){
      Drive.arcadeDrive(FWD, ROT);
    }

    public void ResetEncoders(){
      leftRelativeEncoder.setPosition(0);
      righRelativeEncoder.setPosition(0);
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
