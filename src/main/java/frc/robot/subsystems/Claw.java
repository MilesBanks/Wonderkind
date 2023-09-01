// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Claw extends SubsystemBase {
  private final CANSparkMax m_ClawLeft = new CANSparkMax(Constants.CAN_ID_Constants.kLeftClawMotorID, MotorType.kBrushless);
  private final CANSparkMax m_ClawRight = new CANSparkMax(Constants.CAN_ID_Constants.kRightClawMotorID, MotorType.kBrushless); //change CAN ID to 8

  /** Creates a new Claw. */
  public Claw() {
    m_ClawLeft.getEncoder().setPosition(0);
    m_ClawRight.getEncoder().setPosition(0);
  }
    

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(getEncoders()[0]  <=0){
      if(m_ClawLeft.get() < 0)
        m_ClawLeft.set(0);
    }

    if(getEncoders()[1] <=0) {
      if(m_ClawRight.get()<0)
        m_ClawRight.set(0);
    }
  }
  public void clawClose(double speed){
    m_ClawLeft.set(-speed);
    m_ClawRight.set(speed);
  }
  public void clawOpen(double speed){
    m_ClawLeft.set(speed);
    m_ClawRight.set(speed);
  }
  public void clawShiftRight (double speed) {
    m_ClawLeft.set(speed);
    m_ClawRight.set(speed);
  }
  public void clawShiftLeft(double speed) {
    m_ClawLeft.set(-speed);
    m_ClawRight.set(speed);
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
  


