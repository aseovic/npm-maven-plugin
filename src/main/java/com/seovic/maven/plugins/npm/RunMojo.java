package com.seovic.maven.plugins.npm;


import org.apache.commons.exec.CommandLine;

import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;


/**
 * An 'npm run' mojo.
 *
 * @author Aleksandar Seovic  2015.09.21
 */
@Mojo(name = "run", threadSafe = true)
public class RunMojo
        extends ExecMojo
    {
    // ---- parameters -------------------------------------------------------

    @Parameter(defaultValue = "${mojoExecution}", readonly = true, required = true)
    private MojoExecution execution;

    /**
     * The npm script to run.
     */
    @Parameter(property = "npm.script")
    private String script;

    /**
     * Whether you should skip while running in the test phase (default is false)
     */
    @Parameter(property = "skipTests", required = false, defaultValue = "false")
    protected boolean skipTests;

    // ---- constructors ----------------------------------------------------

    /**
     * Default constructor.
     */
    public RunMojo()
        {
        super("run");
        }

    /**
     * Construct RunMojo instance.
     *
     * @param sScript  the npm script to run
     */
    protected RunMojo(String sScript)
        {
        super("run");
        script = sScript;
        }

    // ---- Mojo interface --------------------------------------------------

    @Override
    public void execute()
            throws MojoExecutionException, MojoFailureException
        {
        boolean fExplicit = this.script != null;
        String  script    = fExplicit
                            ? this.script
                            : execution.getLifecyclePhase();

        String SCRIPT_NOT_FOUND = "Script '" + script + "' is not defined in package.json";

        Package pkg = getPackage();
        if (fExplicit && !pkg.hasScript(script))
            {
            getLog().error(SCRIPT_NOT_FOUND);
            throw new MojoFailureException(SCRIPT_NOT_FOUND);
            }
        else if (pkg.hasScript(script))
            {
            if (!fExplicit && script.endsWith("test") && skipTests)
                {
                getLog().info("Tests are skipped.");
                }
            else
                {
                CommandLine cmdLine = new CommandLine(getNpmCommand());
                addCommand(cmdLine);
                cmdLine.addArgument(script);
                addArguments(cmdLine);

                execute(cmdLine);
                }
            }
        else
            {
            getLog().info(SCRIPT_NOT_FOUND);
            }
        }
    }
