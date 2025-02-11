
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.CloseClawCommand;
import frc.robot.Commands.OpenClawCommand;
import frc.robot.Subsystem.ClawSubsystem;
import frc.robot.Subsystem.ObstructionSensor;
import frc.robot.Constants;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final OI oi = new OI();
    private final ClawSubsystem claw = new ClawSubsystem();
    // private final ArmPivotSubsystem arm = new ArmPivotSubsystem();
    private final ObstructionSensor sensor = new ObstructionSensor(1);

    private Trigger clawObstructedTrigger;
    private double lastClawOpenTime = Double.NEGATIVE_INFINITY;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        clawObstructedTrigger = new Trigger(() -> {
            return sensor.obstructed() 
                && Timer.getFPGATimestamp() > lastClawOpenTime + Constants.Timing.CLAW_DELAY_AFTER_OPEN;
        });

        clawObstructedTrigger.onTrue(new CloseClawCommand(claw));

        oi.getButton(0, Constants.Buttons.B_BUTTON).onTrue(
            new OpenClawCommand(claw)
            .andThen(() -> {
                lastClawOpenTime = Timer.getFPGATimestamp();
            }, claw)
        );

        oi.getButton(0, Constants.Buttons.A_BUTTON).onTrue(
            new CloseClawCommand(claw)
        );
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return null;
    }

    public void periodic(){
        SmartDashboard.putNumber("TestKey: System Time", System.currentTimeMillis());

        SmartDashboard.putBoolean("ClawObstructedTriggerValue", clawObstructedTrigger.getAsBoolean());
    }
}
