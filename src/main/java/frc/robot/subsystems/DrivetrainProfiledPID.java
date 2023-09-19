// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;

public class DrivetrainProfiledPID extends ProfiledPIDSubsystem {
  /** Creates a new DrivetrainProfiledPID. */
  public DrivetrainProfiledPID() {
    super(
        // The ProfiledPIDController used by the subsystem
        new ProfiledPIDController(
            0.1,
            0,
            0,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(200, 100)));
  }

  // Example of creating new object of class
  //private Drivetrain newobject = new Drivetrain();

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    // Use the output (and optionally the setpoint) here
    Drivetrain.setmotor(output);
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return Drivetrain.getDrivetrainPosition();
    //return 0;
  }
}
