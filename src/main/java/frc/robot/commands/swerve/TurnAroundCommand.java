package frc.robot.commands.swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DrivebaseConstants.TurnAroundPIDConstants;
import frc.robot.subsystems.SwerveSubsystem;

public class TurnAroundCommand extends Command {
    private final SwerveSubsystem swerve;
    private final PIDController pid = new PIDController(TurnAroundPIDConstants.kP, TurnAroundPIDConstants.kI, TurnAroundPIDConstants.kD);

    public TurnAroundCommand(SwerveSubsystem swerve) {
        this.swerve = swerve;

        addRequirements(swerve);
    }
    
    @Override
    public void initialize() {
        double heading = swerve.getHeading().getDegrees();

        pid.setSetpoint(heading > 180 ? heading - 180 : heading + 180);
        pid.setTolerance(TurnAroundPIDConstants.TOLERANCE);
        pid.enableContinuousInput(0, 360);
    }

    @Override
    public void execute() {
        double output = pid.calculate(swerve.getHeading().getDegrees());
        swerve.drive(new Translation2d(0, 0), output, false);
    }

    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }

    @Override
    public void end(boolean interrupted) {
        swerve.drive(new ChassisSpeeds(0, 0, 0));
    }
}
