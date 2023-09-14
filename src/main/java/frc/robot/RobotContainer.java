// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DrivetrainPID;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ElevatorPID;
import frc.robot.commands.PlayOneCube;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.ExampleSubsystem;
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
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Drivetrain m_Drivetrain = new Drivetrain();
  private final Claw m_Claw = new Claw();
  private final Elevator m_Elevator = new Elevator();


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
          m_Drivetrain.OurDrive(m_driverController.getLeftY(), m_driverController.getRightX()),m_Drivetrain));
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
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());

    m_coDriverController.povUp().whileTrue(m_Elevator.elevatorForwardCommand(Constants.SpeedConstants.kUpElevatorSpeed));
    m_coDriverController.povDown().whileTrue(m_Elevator.elevatorBackwordsCommand(Constants.SpeedConstants.kDownElevatorSpeed));

    m_coDriverController.rightBumper().whileTrue(m_Claw.clawCloseCommand(Constants.SpeedConstants.kClawCloseSpeed));
    m_coDriverController.leftBumper().whileTrue(m_Claw.clawOpenCommand(Constants.SpeedConstants.kClawOpenSpeed)); 

    m_coDriverController.rightTrigger().whileTrue(m_Claw.clawShiftRightCommand(Constants.SpeedConstants.kClawShiftSpeed));
    m_coDriverController.leftTrigger().whileTrue(m_Claw.clawShiftLeftCommand(Constants.SpeedConstants.kClawShiftSpeed));

    // PID values
    m_coDriverController.a().toggleOnTrue(new ElevatorPID(5.00));
    m_coDriverController.b().toggleOnTrue(new ElevatorPID(30.00));
    m_coDriverController.y().toggleOnTrue(new ElevatorPID(50.00));
    // make the button automatic so you can do: A->Y->B. Without pressing a button to disable/enable PID
    // make it so the elevator doesn't slowly creep down as well
    // have manual control locked behind a toggle button that also toggles off PID
    m_coDriverController.x().whileTrue(m_Elevator.elevatorCancelCommand());

    m_coDriverController.povRight().toggleOnTrue(new DrivetrainPID(10.00)); // robot moves to position 10. PID active forever
    m_coDriverController.povLeft().whileTrue(m_Drivetrain.cancelDrivePIDCommand()); // cancels PID
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new PlayOneCube(m_Drivetrain, m_Elevator, m_Claw);
  }
}
