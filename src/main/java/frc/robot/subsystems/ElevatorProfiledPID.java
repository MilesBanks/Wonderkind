// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;

public class ElevatorProfiledPID extends ProfiledPIDSubsystem {
  /** Creates a new test. */
  public ElevatorProfiledPID() {
    super(
        // The ProfiledPIDController used by the subsystem
        new ProfiledPIDController(
            0.1,
            0,
            0,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(250, 150)));
  }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    // Use the output (and optionally the setpoint) here
    Elevator.setmotor(output);
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return Elevator.getElevatorPosition();
    //return 0;
  }
}
