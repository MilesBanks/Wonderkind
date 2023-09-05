// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Claw;
//import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;


// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PlayOneCube extends SequentialCommandGroup {
  public PlayOneCube(Elevator m_Elevator, Claw m_Claw) {
    addCommands(
      m_Claw.clawCloseCommand(0.2).withTimeout(1),
      m_Elevator.elevatorForwardCommand(0.2).withTimeout(3.5),
      m_Claw.clawOpenCommand(0.2).withTimeout(1),
      m_Elevator.elevatorBackwordsCommand(0.2).withTimeout(2)
    );
  }
}
