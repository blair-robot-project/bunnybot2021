package frc.team449.drive.unidirectional.commands.AHRS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team449.drive.unidirectional.DriveUnidirectional;
import frc.team449.generalInterfaces.AHRS.SubsystemAHRS;
import frc.team449.other.Debouncer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Rotates the robot back and forth in order to dislodge any stuck balls. */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class JiggleRobot<T extends Subsystem & DriveUnidirectional & SubsystemAHRS>
    extends SequentialCommandGroup {

  /**
   * Instantiate the CommandGroup
   *
   * @param onTargetBuffer A buffer timer for having the loop be on target before it stops running.
   *     Can be null for no buffer.
   * @param absoluteTolerance The maximum number of degrees off from the target at which we can be
   *     considered within tolerance.
   * @param minimumOutput The minimum output of the loop. Defaults to zero.
   * @param maximumOutput The maximum output of the loop. Can be null, and if it is, no maximum
   *     output is used.
   * @param loopTimeMillis The time, in milliseconds, between each loop iteration. Defaults to 20
   *     ms.
   * @param deadband The deadband around the setpoint, in degrees, within which no output is given
   *     to the motors. Defaults to zero.
   * @param inverted Whether the loop is inverted. Defaults to false.
   * @param kP Proportional gain. Defaults to zero.
   * @param kI Integral gain. Defaults to zero.
   * @param kD Derivative gain. Defaults to zero.
   * @param subsystem The drive to execute this command on.
   */
  @JsonCreator
  public JiggleRobot(
      @JsonProperty(required = true) double absoluteTolerance,
      @Nullable Debouncer onTargetBuffer,
      double minimumOutput,
      @Nullable Double maximumOutput,
      @Nullable Integer loopTimeMillis,
      double deadband,
      boolean inverted,
      int kP,
      int kI,
      int kD,
      @NotNull @JsonProperty(required = true) T subsystem) {
    addCommands(
        new NavXTurnToAngleRelative<>(
            absoluteTolerance,
            onTargetBuffer,
            minimumOutput,
            maximumOutput,
            loopTimeMillis,
            deadband,
            inverted,
            kP,
            kI,
            kD,
            10,
            subsystem,
            3),
        new NavXTurnToAngleRelative<>(
            absoluteTolerance,
            onTargetBuffer,
            minimumOutput,
            maximumOutput,
            loopTimeMillis,
            deadband,
            inverted,
            kP,
            kI,
            kD,
            -10,
            subsystem,
            3));
  }
}
