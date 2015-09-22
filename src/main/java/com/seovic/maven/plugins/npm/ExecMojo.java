package com.seovic.maven.plugins.npm;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Base class for individual npm command mojos.
 *
 * @author Aleksandar Seovic  2015.09.21
 */
@Mojo(name = "exec", threadSafe = true)
public class ExecMojo
        extends AbstractMojo
    {
    // ---- parameters -------------------------------------------------------

    /**
     * The npm command to execute, such as 'install', 'test', etc. Required.
     */
    @Parameter(property = "npm.command")
    private String command;

    /**
     * The arguments to pass to npm command. Optional.
     */
    @Parameter(property = "npm.args")
    private String[] args;

    /**
     * The working directory. Optional. If not specified, basedir will be used.
     */
    @Parameter(property = "npm.workingDir", defaultValue = "${basedir}")
    private File workingDir;

    // ---- constructors ----------------------------------------------------

    /**
     * Default constructor.
     */
    public ExecMojo()
        {
        }

    /**
     * Construct ExecMojo instance.
     *
     * @param sCmd  the npm command to execute, such as 'install', 'test', etc.
     */
    protected ExecMojo(String sCmd)
        {
        command = sCmd;
        }

    // ---- Mojo interface --------------------------------------------------

    @Override
    public void execute()
            throws MojoExecutionException, MojoFailureException
        {
        if (command == null)
            {
            throw new MojoExecutionException("The npm command to execute must be set");
            }

        CommandLine cmdLine = new CommandLine("npm");
        addCommand(cmdLine);
        addArguments(cmdLine);

        execute(cmdLine);
        }

    // ---- helpers ---------------------------------------------------------

    protected void execute(CommandLine cmdLine)
            throws MojoFailureException, MojoExecutionException
        {
        try
            {
            DefaultExecutor executor = new DefaultExecutor();
            executor.setWorkingDirectory(workingDir);
            executor.setStreamHandler(new PumpStreamHandler(System.out, System.err, System.in));

            executor.execute(cmdLine);
            }
        catch (ExecuteException e)
            {
            throw new MojoFailureException("npm failure", e);
            }
        catch (IOException e)
            {
            throw new MojoExecutionException("Error executing NPM", e);
            }
        }

    protected void addCommand(CommandLine cmdLine)
        {
        cmdLine.addArgument(command);
        }

    protected void addArguments(CommandLine cmdLine)
        {
        cmdLine.addArguments(args);
        }

    protected Package getPackage()
            throws MojoExecutionException
        {
        File file = new File(workingDir, "package.json");
        if (!file.exists())
            {
            throw new MojoExecutionException(file.getAbsolutePath() + " does not exist");
            }
        try
            {
            return Package.parse(file);
            }
        catch (IOException e)
            {
            throw new MojoExecutionException("Unable to parse " + file.getAbsolutePath(), e);
            }
        }
    }
