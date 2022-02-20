package dev._2lstudios.jelly.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev._2lstudios.jelly.commands.CommandExecutionTarget;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Command {
    public String name();

    public String description() default "";

    public String permission() default "";

    public String usage() default "";

    public Class<?>[] arguments() default {};

    public String[] aliases() default {};

    public int minArguments() default Integer.MIN_VALUE;

    public boolean async() default false;

    public boolean silent() default false;

    public CommandExecutionTarget target() default CommandExecutionTarget.BOTH;
}