package frc.robot.subsystems;

import java.util.List;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
// import frc.robot.LimelightHelpers.LimelightResults;

public class VisionSubsystem extends SubsystemBase {
    private NetworkTable LLTable;

    public VisionSubsystem() {
        this.LLTable = NetworkTableInstance.getDefault().getTable(VisionConstants.TABLE_KEY);
    }

    public Pose2d getPose2d() {
        return LimelightHelpers.getBotPose2d(VisionConstants.NAME);
    }

    public Command autoAlignFollowPath() {
        // LimelightResults llresults = LimelightHelpers.getLatestResults(VisionConstants.NAME);

        // llresults.targetingResults
        // if (llresults.targets_Fiducials.length() > 1) {}

        /*
         * Thought process:
         * 1.
         * LLPipeline: Notes
         * Get the position of the robot
         * 
         * 
         * 
         */

        PathPlannerPath path = new PathPlannerPath(null, Constants.PATH_CONSTRAINTS, null);

        if (LimelightHelpers.getFiducialID(VisionConstants.NAME) == 4) {
            List<Translation2d> bezierPoints = PathPlannerPath.bezierFromPoses(
                    getPose2d(),
                    new Pose2d(14.81, 5.48, Rotation2d.fromDegrees(0)));

            path = new PathPlannerPath(bezierPoints, Constants.PATH_CONSTRAINTS,
                    new GoalEndState(0, Rotation2d.fromDegrees(0)));
        }

        return AutoBuilder.followPath(path);
    }
}
