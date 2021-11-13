package frc.team449._2021BunnyBot.elevator.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team449._2021BunnyBot.elevator.OneMotorPulleyElevator;
import frc.team449._2021BunnyBot.elevator.OneMotorPulleyElevator.ElevatorPosition;

public class RaiseElevator extends CommandBase {
  private final OneMotorPulleyElevator elevator;

  public RaiseElevator(OneMotorPulleyElevator elevator) {
    addRequirements(elevator);
    this.elevator = elevator;
  }
  /** Moves the elevator one above the current level of elevation */
  @Override
  public void execute() {
    switch (elevator.getPosition()) {
        // Case TOP does not exist because we cannot move above the top of the elevator
      case UPPER:
        elevator.moveToPosition(ElevatorPosition.TOP);
        break;
      case LOWER:
        elevator.moveToPosition(ElevatorPosition.UPPER);
        break;
      case BOTTOM:
        elevator.moveToPosition(ElevatorPosition.LOWER);
        break;
    }
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
