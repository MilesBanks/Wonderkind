// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {

  private CANSparkMax Elevator = new CANSparkMax(Constants.CAN_ID_Constants.kElevatorMotorID, MotorType.kBrushless);
  private final RelativeEncoder elevatorEncoder = Elevator.getEncoder();
  /** Creates a new Elevator. */

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void elevatorForward(double speed, int position){
    Elevator.set(speed);
      if (elevatorEncoder.getPosition() >= position)
        elevatorStop();
  }

  public void elevatorForward(double speed){
    Elevator.set(speed);
  }

  // elevatorForwardCommand with position.
  //public Command elevatorForwardCommand(double speed, int position){
  //  return new StartEndCommand(() -> this.elevatorForward(speed), () -> this.elevatorForward(0), this);
  //}

  public Command elevatorForwardCommand(double speed){
    return new StartEndCommand(() -> this.elevatorForward(speed), () -> this.elevatorForward(0), this);
  }

  public void elevatorBackwords(double speed, int position){
    Elevator.set(-speed);
      if (elevatorEncoder.getPosition() <= position)
        elevatorStop();
  }

  public void elevatorBackwords(double speed){

    Elevator.set(-speed);
  }

  public Command elevatorBackwordsCommand(double speed){
    return new StartEndCommand(() -> this.elevatorBackwords(speed), () -> this.elevatorBackwords(0.0), this);

  }

  public void elevatorStop(){
    Elevator.set(0);
  }

  public double getEncoder(){
    return elevatorEncoder.getPosition();
  }

}
