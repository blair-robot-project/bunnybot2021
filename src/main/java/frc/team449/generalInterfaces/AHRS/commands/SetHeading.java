package frc.team449.generalInterfaces.AHRS.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team449.generalInterfaces.AHRS.SubsystemAHRS;
import io.github.oblarg.oblog.annotations.Log;
import org.jetbrains.annotations.NotNull;

/** Set the heading of the AHRS. */
@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
public class SetHeading extends InstantCommand {

  /** The subsystem to execute this command on. */
  @NotNull @Log.Exclude private final SubsystemAHRS subsystem;

  /** The heading to set, in degrees. */
  private final double newHeading;

  /**
   * Default constructor.
   *
   * @param subsystem The subsystem to execute this command on.
   * @param newHeading The heading to set, in degrees. Defaults to 0.
   */
  @JsonCreator
  public SetHeading(
      @NotNull @JsonProperty(required = true) SubsystemAHRS subsystem, double newHeading) {
    this.subsystem = subsystem;
    this.newHeading = newHeading;
  }

  /** Log on init. */
  @Override
  public void initialize() {
    Shuffleboard.addEventMarker(
        "SetHeading init.", this.getClass().getSimpleName(), EventImportance.kNormal);
    // Logger.addEvent("SetHeading init.", this.getClass());
  }

  /** Set the heading. */
  @Override
  public void execute() {
    subsystem.setHeading(newHeading);
  }

  /** Log on exit. */
  @Override
  public void end(boolean interrupted) {
    Shuffleboard.addEventMarker(
        "SetHeading end.", this.getClass().getSimpleName(), EventImportance.kNormal);
    // Logger.addEvent("SetHeading end.", this.getClass());
  }
}
