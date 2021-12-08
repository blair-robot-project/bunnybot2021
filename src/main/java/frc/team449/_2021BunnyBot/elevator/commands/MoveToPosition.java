package frc.team449._2021BunnyBot.elevator.commands;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team449._2021BunnyBot.elevator.OneMotorPulleyElevator;
import org.jetbrains.annotations.NotNull;

public class MoveToPosition extends CommandBase {
  private final OneMotorPulleyElevator.ElevatorPosition position;
  private final OneMotorPulleyElevator elevator;
  private final ProfiledPIDController controller;

  public MoveToPosition(
      @NotNull OneMotorPulleyElevator.ElevatorPosition position,
      @NotNull OneMotorPulleyElevator elevator) {
    this.elevator = elevator;
    this.position = position;
    controller = elevator.getController();
  }

  /** Moves to designated position for command */
  @Override
  public void execute() {
    elevator.setRawOutput(
        controller.calculate(elevator.getRawPosition(), position.distanceFromBottom));
  }
  /** Some tolerance, stops if elevator is within .01 meters of the setpoint */
  @Override
  public boolean isFinished() {
    return Math.abs(elevator.getRawPosition() - position.distanceFromBottom) < .01;
  }
}
