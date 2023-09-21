// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {

  private final CANSparkMax m_ClawLeft = new CANSparkMax(Constants.CAN_ID_Constants.kLeftClawMotorID, MotorType.kBrushless);
  private final CANSparkMax m_ClawRight = new CANSparkMax(Constants.CAN_ID_Constants.kRightClawMotorID, MotorType.kBrushless); 
  private double m_ClawLeftPosition;
  private double m_ClawRightPosition;
  private double m_ClawLeftSpeed;
  private double m_ClawRightSpeed;
  private double m_ClawLeftVelocity;
  private double m_ClawRightVelocity;

  /** Creates a new Claw. */
  public Claw() {
    m_ClawLeft.getEncoder().setPosition(0);
    m_ClawRight.getEncoder().setPosition(0);
  }   

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_ClawLeftSpeed = m_ClawLeft.get();
    m_ClawRightSpeed = m_ClawRight.get();
    m_ClawLeftPosition = m_ClawLeft.getEncoder().getPosition();
    m_ClawRightPosition = m_ClawRight.getEncoder().getPosition();
    m_ClawLeftVelocity = m_ClawLeft.getEncoder().getVelocity();
    m_ClawRightVelocity = m_ClawRight.getEncoder().getVelocity();

    if(m_ClawLeftPosition <= 0.5){
      if(m_ClawLeftSpeed < 0)
        m_ClawLeft.set(0);
    }

    if(m_ClawRightPosition <= 0.5) {
      if(m_ClawRightSpeed < 0)
        m_ClawRight.set(0);
    }

    SmartDashboard.putNumber(("Left Claw Position"), m_ClawLeftPosition);
    SmartDashboard.putNumber(("Right Claw Position"), m_ClawRightPosition);
    SmartDashboard.putNumber(("Left Claw Velocity"), m_ClawLeftVelocity);
    SmartDashboard.putNumber(("Right Claw Velocity"), m_ClawRightVelocity);
    SmartDashboard.putNumber(("Left Claw Speed"), m_ClawLeftSpeed);
    SmartDashboard.putNumber(("Right Claw Speed"), m_ClawRightSpeed);
  
    if (m_ClawLeftPosition + m_ClawRightPosition >= 88 ){
      if (m_ClawLeftSpeed + m_ClawRightSpeed >= Constants.SpeedConstants.kClawCloseSpeed) {
        m_ClawLeft.set(0);
        m_ClawRight.set(0);
      }
    }
  }

  public void clawClose(double speed){
    m_ClawLeft.set(-speed);
    m_ClawRight.set(speed);
  }

  public Command clawCloseCommand(double speed){
    return new StartEndCommand(() -> this.clawClose(speed), () -> this.clawClose(0.0), this);
  }

  public void clawOpen(double speed){
    m_ClawLeft.set(speed);
    m_ClawRight.set(-speed);
  }

  public Command clawOpenCommand(double speed){
    return new StartEndCommand(() -> this.clawOpen(speed), () -> this.clawOpen(0.0), this);
  }

  public void clawShiftRight (double speed) {
    m_ClawLeft.set(speed);
    m_ClawRight.set(speed);
  }

  public Command clawShiftRightCommand(double speed){
    return new StartEndCommand(() -> this.clawShiftRight(speed), () -> this.clawShiftRight(0.0), this);
  }

  public void clawShiftLeft(double speed) {
    m_ClawLeft.set(-speed);
    m_ClawRight.set(-speed); //added negative
  }

  public Command clawShiftLeftCommand(double speed){
    return new StartEndCommand(() -> this.clawShiftLeft(speed), () -> this.clawShiftLeft(0.0), this);
  }

  public void clawStop(){
    m_ClawLeft.set(0);
    m_ClawRight.set(0);
  }

  public double[] getEncoders(){
    return new double[]{m_ClawLeft.getEncoder().getPosition(), m_ClawRight.getEncoder().getPosition()};
  }

  public double[] getCurrents(){
    return new double[]{m_ClawLeft.getOutputCurrent(), m_ClawRight.getOutputCurrent()};
  }
  
}
