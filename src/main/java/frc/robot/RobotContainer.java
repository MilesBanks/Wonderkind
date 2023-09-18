// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.GrabAndGo;
import frc.robot.commands.PlayOneCube;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.DrivetrainProfiledPID;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.ElevatorProfiledPID;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_Drivetrain = new Drivetrain();
  private final Claw m_Claw = new Claw();
  private final Elevator m_Elevator = new Elevator();

  // Declare ElevatorProfiledPID and DrivetrainProfiledPID
  // Only public and static so Robot.java can access it to disable the PID controllers
  // When the robot switches form auton to teleop
  public static ElevatorProfiledPID m_ElevatorProfiledPID = new ElevatorProfiledPID();
  public static DrivetrainProfiledPID m_DrivetrainProfiledPID = new DrivetrainProfiledPID();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_coDriverController =
      new CommandXboxController(Constants.OperatorConstants.kCoDriverControllerPort);
    
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();

    m_Drivetrain.setDefaultCommand(
    Commands.run(
      () ->
          m_Drivetrain.OurDrive(m_driverController.getLeftY(), m_driverController.getRightX(), Elevator.slowMow), m_Drivetrain));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    m_coDriverController.povUp().whileTrue(m_Elevator.elevatorForwardCommand(Constants.SpeedConstants.kUpElevatorSpeed));
    m_coDriverController.povDown().whileTrue(m_Elevator.elevatorBackwordsCommand(Constants.SpeedConstants.kDownElevatorSpeed));

    m_coDriverController.rightBumper().whileTrue(m_Claw.clawCloseCommand(Constants.SpeedConstants.kClawCloseSpeed));
    m_coDriverController.leftBumper().whileTrue(m_Claw.clawOpenCommand(Constants.SpeedConstants.kClawOpenSpeed)); 

    m_coDriverController.rightTrigger().whileTrue(m_Claw.clawShiftRightCommand(Constants.SpeedConstants.kClawShiftSpeed));
    m_coDriverController.leftTrigger().whileTrue(m_Claw.clawShiftLeftCommand(Constants.SpeedConstants.kClawShiftSpeed));
    
    // Each button changes setGoal of the instance of class ElevatorProfiledPID we defined earlier
    // instead of making a new ElevatorPID object, change position of the single object
    m_coDriverController.a().onTrue(Commands.runOnce(
      () -> {
        m_ElevatorProfiledPID.setGoal(1.00);
        m_ElevatorProfiledPID.enable();
      },
      m_Elevator));
    m_coDriverController.b().onTrue(Commands.runOnce(
      () -> {
        m_ElevatorProfiledPID.setGoal(41.50);
        m_ElevatorProfiledPID.enable();
      },
      m_Elevator));
    m_coDriverController.x().onTrue(Commands.runOnce(
      () -> {
        m_ElevatorProfiledPID.setGoal(36.00);
        m_ElevatorProfiledPID.enable();
      },
      m_Elevator));
    m_coDriverController.y().onTrue(Commands.runOnce(
      () -> {
        m_ElevatorProfiledPID.setGoal(57.50);
        m_ElevatorProfiledPID.enable();
      },
      m_Elevator));
    
    // Button 7 disables PID for manual elevator input
    m_coDriverController.button(7).onTrue(Commands.runOnce(
      () -> {
        m_ElevatorProfiledPID.disable();
      },
      m_Elevator));

    m_coDriverController.povLeft().onTrue(new GrabAndGo(m_Drivetrain, m_Elevator, m_Claw, m_ElevatorProfiledPID));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new PlayOneCube(m_Drivetrain, m_Elevator, m_Claw, m_ElevatorProfiledPID, m_DrivetrainProfiledPID);
  }
}
