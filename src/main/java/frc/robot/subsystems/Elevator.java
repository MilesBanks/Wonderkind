// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Elevator extends SubsystemBase {

  private final static CANSparkMax m_Elevator = new CANSparkMax(Constants.CAN_ID_Constants.kElevatorMotorID, MotorType.kBrushless);
  private final RelativeEncoder elevatorEncoder = m_Elevator.getEncoder(); 

  private double m_ElevatorSpeed; 
  private double m_ElevatorPosition; // max: 57.6 to 57.3
  private double m_ElevatorVelocity;
  /** Creates a new Elevator. */
  public Elevator() {
    // this is a constructer we added
    m_Elevator.getEncoder().setPosition(0);
  }

  public static double slowMo = 1.0; // Divider for robot speed

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_ElevatorSpeed = m_Elevator.get();
    m_ElevatorPosition = m_Elevator.getEncoder().getPosition();
    m_ElevatorVelocity = m_Elevator.getEncoder().getVelocity(); 

    SmartDashboard.putNumber(("Elevator Position"), m_ElevatorPosition);
    SmartDashboard.putNumber(("Elevator Velocity"), m_ElevatorVelocity);
    SmartDashboard.putNumber(("Elevator Speed"), m_ElevatorSpeed);

    slowMo = 1.0 + (m_ElevatorPosition/23);
  }

  public void elevatorUp(double speed, int position){
    m_Elevator.set(speed);
      if (elevatorEncoder.getPosition() >= position)
        elevatorStop();
  }

  public void elevatorUp(double speed){
    m_Elevator.set(Constants.SpeedConstants.kUpElevatorSpeed);
  }

  public Command elevatorUpCommand(double speed){
    return new StartEndCommand(() -> this.elevatorUp(speed), () -> this.elevatorUp(0), this);
  }

  public void elevatorDown(double speed, int position){
    m_Elevator.set(-speed);
      if (elevatorEncoder.getPosition() <= position)
        elevatorStop();
  }

  public void elevatorDown(double speed){
    m_Elevator.set(-Constants.SpeedConstants.kDownElevatorSpeed);
  }

  public Command elevatorDownCommand(double speed){
    return new StartEndCommand(() -> this.elevatorDown(speed), () -> this.elevatorDown(0.0), this);
  }

  public void elevatorStop(){
    m_Elevator.set(0);
  }

  public double getEncoder(){
    return elevatorEncoder.getPosition();
  }

  public static double getElevatorPosition() {
    return m_Elevator.getEncoder().getPosition();
  }

  // for NEWElevatorPID
  public static void setmotor(Double output) {
    m_Elevator.set(output);
  }

}
