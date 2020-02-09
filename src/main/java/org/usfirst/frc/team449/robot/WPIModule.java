package org.usfirst.frc.team449.robot;

import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import org.usfirst.frc.team449.robot.mixIn.BooleanSupplierMixin;
import org.usfirst.frc.team449.robot.mixIn.CommandMixIn;
import org.usfirst.frc.team449.robot.mixIn.SubsystemMixIn;

import java.util.function.BooleanSupplier;

/**
 * A Jackson {@link com.fasterxml.jackson.databind.Module} for adding mix-in annotations to some WPI classes.
 */
public class WPIModule extends SimpleModule {

    /**
     * Default constructor
     */
    public WPIModule() {
        super("WPIModule");
    }

    /**
     * Mixes in some mix-ins to the given context.
     *
     * @param context the context to set up
     */
    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.setMixInAnnotations(BooleanSupplier.class, BooleanSupplierMixin.class);
        context.setMixInAnnotations(Command.class, CommandMixIn.class);
        context.setMixInAnnotations(Subsystem.class, SubsystemMixIn.class);
    }
}
