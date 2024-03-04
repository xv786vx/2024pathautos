package frc.robot.commands.subsystems.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AngleSubystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.types.ElevatorSetpoint;

public class MoveSetpoint extends Command {
    private final ElevatorSubsystem elevator;
    private final AngleSubystem angle;

    private final ElevatorSetpoint setpoint;

    public MoveSetpoint(ElevatorSubsystem elevator, AngleSubystem angle, ElevatorSetpoint setpoint) {
        this.elevator = elevator;
        this.angle = angle;
        this.setpoint = setpoint;

        addRequirements(elevator, angle);
    }

    @Override
    public void initialize() {
        angle.setTargetAngle(1);
    }

    @Override
    public void execute() {
        if (angle.isAtTargetAngle()) {
            switch (setpoint) {
                default:
                case max:
                elevator.setElevatorPosition(1);
                break;
                case min:    
                elevator.setElevatorPosition(0);
                break;
            }
        }
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
    }
}