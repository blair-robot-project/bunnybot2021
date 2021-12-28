package frc.team449;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.team449.generalInterfaces.MotorContainer;
import frc.team449.jacksonWrappers.MappedJoystick;
import frc.team449.jacksonWrappers.PDP;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** The Jackson-compatible object representing the entire robot. */
@JsonIgnoreProperties({"CONSTANTS", "NAVIGATION"})
public class RobotMap {
  @SuppressWarnings("FieldCanBeLocal")
  @NotNull
  private final List<Subsystem> subsystems;

  @NotNull private final MotorContainer motors = MotorContainer.getInstance();

  /** A runnable that updates cached variables. */
  @NotNull private final Runnable updater;

  @NotNull private final CommandContainer commands;

  @SuppressWarnings("FieldCanBeLocal")
  @NotNull
  private final PDP pdp;

  /** Whether the camera server should be run. */
  private final boolean useCameraServer;

  /**
   * Default constructor.
   *
   * @param subsystems The robot's subsystems.
   * @param pdp The PDP
   * @param updater A runnable that updates cached variables.
   * @param commands A container to hold all of the robot's commands.
   * @param useCameraServer Whether the camera server should be run. Defaults to false.
   */
  @JsonCreator
  public RobotMap(
      @NotNull @JsonProperty(required = true) @JsonInclude(content = JsonInclude.Include.NON_NULL)
          final List<Subsystem> subsystems,
      @NotNull @JsonProperty(required = true) final PDP pdp,
      @NotNull @JsonProperty(required = true) final Runnable updater,
      @NotNull @JsonProperty(required = true) final CommandContainer commands,
      @Nullable final List<MappedJoystick> joysticks,
      final boolean useCameraServer) {
    this.updater = updater;
    this.pdp = pdp;
    this.useCameraServer = useCameraServer;
    this.subsystems = subsystems;
    this.commands = commands;
  }

  /** @return The commands to be run when first enabled in autonomous mode. */
  @Nullable
  public Iterator<Command> getAutoStartupCommands() {
    if (this.commands.getAutoStartupCommand() == null) {
      return null;
    }
    return this.commands.getAutoStartupCommand().iterator();
  }

  /** @return The commands to be run when first enabled in teleoperated mode. */
  @Nullable
  public Iterator<Command> getTeleopStartupCommands() {
    if (this.commands.getTeleopStartupCommand() == null) {
      return null;
    }
    return this.commands.getTeleopStartupCommand().iterator();
  }

  @Nullable
  public Iterator<Command> getTestStartupCommands() {
    if (this.commands.getTestStartupCommand() == null) {
      return null;
    }
    return this.commands.getTestStartupCommand().iterator();
  }

  /** @return The commands to be run when first enabled. */
  @Nullable
  public Iterator<Command> getRobotStartupCommands() {
    if (this.commands.getRobotStartupCommand() == null) {
      return null;
    }
    return this.commands.getRobotStartupCommand().iterator();
  }

  /** @return A runnable that updates cached variables. */
  @NotNull
  public Runnable getUpdater() {
    return this.updater;
  }

  /** @return Whether the camera server should be run. */
  public boolean useCameraServer() {
    return this.useCameraServer;
  }
}
