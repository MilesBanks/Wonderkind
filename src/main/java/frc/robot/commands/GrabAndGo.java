// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.ElevatorProfiledPID;


// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class GrabAndGo extends SequentialCommandGroup {
  public GrabAndGo(Drivetrain m_Drivetrain, Elevator m_Elevator, Claw m_Claw, ElevatorProfiledPID m_ElevatorProfiledPID) {
    addCommands(
      // Remove Driver Input
      m_Drivetrain.driveForwardCommand(0).withTimeout(0.1),
      // Grab cone
      m_Claw.clawShiftRightCommand(Constants.SpeedConstants.kClawShiftSpeed).withTimeout(1.00),
      // Lift cone up
      Commands.runOnce(
        () -> {
          m_ElevatorProfiledPID.setGoal(42.00);
          m_ElevatorProfiledPID.enable();
        },
        m_Elevator),
      // Wait Buffer
      new WaitCommand(0.5),
      // Drive Back
      m_Drivetrain.driveForwardCommand(0.25).withTimeout(0.5),
      // Arm Down
      Commands.runOnce(
        () -> {
          m_ElevatorProfiledPID.setGoal(1.0);
          m_ElevatorProfiledPID.enable();
        },
        m_Elevator)
      );
  }
}
