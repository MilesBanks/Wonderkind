// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  private final CANSparkMax LeftMotor = new CANSparkMax(Constants.CAN_ID_Constants.kLeftMotorID, MotorType.kBrushless);
  private final CANSparkMax RigthMotor = new CANSparkMax(Constants.CAN_ID_Constants.kRigthMotorID, MotorType.kBrushless);

  private final RelativeEncoder lefRelativeEncoder = LeftMotor.getEncoder();
  private final RelativeEncoder righRelativeEncoder = RigthMotor.getEncoder();

  private final DifferentialDrive Drive = new DifferentialDrive(LeftMotor, RigthMotor);


  /** Creates a new Drivetrain. */
  public Drivetrain() {

    LeftMotor.setInverted(true);
    RigthMotor.setInverted(false);

  }

    public void OurDrive(double FWD, double ROT){
      Drive.arcadeDrive(FWD, ROT);
    }

    public void ResetEncoders(){
      lefRelativeEncoder.setPosition(0);
      righRelativeEncoder.setPosition(0);
    }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
