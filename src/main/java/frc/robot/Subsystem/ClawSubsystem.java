package frc.robot.Subsystem;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClawSubsystem extends SubsystemBase {
    private final Solenoid m_solenoid;
    private boolean isOpen;

    public ClawSubsystem() {
        m_solenoid = new Solenoid(
            PneumaticsModuleType.CTREPCM, 
            Constants.IDs.CLAW_SOLENOID_CHANNEL
        );
        m_solenoid.set(false);
    }

    public void open() {
        isOpen = true;
        //m_solenoid.set(false);
    }

    public void close() {
        isOpen = false;
        //m_solenoid.set(true);
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Claw Open", isOpen);
    }
}
