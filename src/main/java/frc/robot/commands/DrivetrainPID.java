// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DrivetrainPID extends PIDCommand {
  public static boolean stopDrivePID = false;
  /** Creates a new NEWNEWElevatorPID. */
  public DrivetrainPID(double position) {
    super(
        // The controller that the command will use
        new PIDController(0.05, 0, 0),
        // This should return the measurement
        () -> Drivetrain.getDrivetrainPosition(),
        // This should return the setpoint (can also be a constant)
        () -> position,
        // This uses the output
        output -> {
          // Use the output here
          Drivetrain.setmotor(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return stopDrivePID; // true = stop PID. false = keep PID on
  }
}
