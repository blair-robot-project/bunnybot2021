package frc.team449.drive.unidirectional.commands.AHRS;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team449.drive.unidirectional.DriveUnidirectional;
import frc.team449.generalInterfaces.AHRS.SubsystemAHRS;
import frc.team449.generalInterfaces.AHRS.commands.PIDAngleCommand;
import frc.team449.oi.unidirectional.tank.OITank;
import frc.team449.other.Debouncer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Drives straight using the NavX gyro to keep a constant alignment. */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class NavXDriveStraight<T extends Subsystem & DriveUnidirectional & SubsystemAHRS>
    extends PIDAngleCommand {

  /** The drive subsystem to give output to. */
  @NotNull protected final T subsystem;

  /** The tank OI to get input from. */
  @NotNull private final OITank oi;

  /** Whether to use the left joystick to drive straight. */
  private final boolean useLeft;

  /**
   * Default constructor.
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
   * @param oi The tank OI to take input from.
   * @param useLeft Which joystick to use to get the forward component to drive straight. True for
   *     left, false for right.
   */
  @JsonCreator
  public NavXDriveStraight(
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
      @NotNull @JsonProperty(required = true) T subsystem,
      @NotNull @JsonProperty(required = true) OITank oi,
      @JsonProperty(required = true) boolean useLeft) {
    super(
        absoluteTolerance,
        onTargetBuffer,
        minimumOutput,
        maximumOutput,
        loopTimeMillis,
        deadband,
        inverted,
        subsystem,
        kP,
        kI,
        kD);
    this.oi = oi;
    this.subsystem = subsystem;
    this.useLeft = useLeft;
    // This is likely to need to interrupt the DefaultCommand and therefore should require its
    // subsystem.
    addRequirements(subsystem);
  }

  /** Set the setpoint of the angle PID. */
  @Override
  public void initialize() {
    this.setSetpoint(subsystem.getHeadingCached());
  }

  /** Give output to the drive based on the output of the PID loop. */
  @Override
  public void execute() {
    // Process the PID output with deadband, minimum output, etc.
    double output = this.getOutput();

    // Set throttle to the specified stick.
    subsystem.setOutput(
        oi.getLeftRightOutputCached()[0] - output, oi.getLeftRightOutputCached()[1] + output);
  }

  /**
   * Never finishes.
   *
   * @return false
   */
  @Override
  public boolean isFinished() {
    return false;
  }

  /** Log when this command ends */
  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      Shuffleboard.addEventMarker(
          "NavXDriveStraight interrupted!",
          this.getClass().getSimpleName(),
          EventImportance.kNormal);
    }
    subsystem.fullStop();
    Shuffleboard.addEventMarker(
        "NavXDriveStraight end", this.getClass().getSimpleName(), EventImportance.kNormal);
  }
}
