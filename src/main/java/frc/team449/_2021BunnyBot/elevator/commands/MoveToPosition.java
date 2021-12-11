package frc.team449._2021BunnyBot.elevator.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team449._2021BunnyBot.elevator.OneMotorPulleyElevator;
import org.jetbrains.annotations.NotNull;

public class MoveToPosition extends CommandBase {
  private final OneMotorPulleyElevator.ElevatorPosition position;
  private final OneMotorPulleyElevator elevator;
  private final double startTime = System.currentTimeMillis();

  public MoveToPosition(
      @NotNull OneMotorPulleyElevator.ElevatorPosition position,
      @NotNull OneMotorPulleyElevator elevator) {
    this.addRequirements(elevator);
    this.elevator = elevator;
    this.position = position;
  }

  /** Moves to designated position for command */
  @Override
  public void execute() {
    System.out.println("Moving to " + position + " position.");
    elevator.moveToPosition(position, System.currentTimeMillis() - startTime);
  }
  /** Some tolerance, stops if elevator is within .01 meters of the setpoint */
  @Override
  public boolean isFinished() {
    return Math.abs(elevator.getPosition().distanceFromBottom - position.distanceFromBottom) < 0.01; //1 centimetre threshold
  }
}
