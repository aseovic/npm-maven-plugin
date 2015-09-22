package com.seovic.maven.plugins.npm;

import org.apache.maven.plugins.annotations.Mojo;

/**
 * An 'npm stop' mojo.
 *
 * @author Aleksandar Seovic  2015.09.21
 */
@Mojo(name = "stop", threadSafe = true)
public class StopMojo
        extends ExecMojo
    {
    public StopMojo()
        {
        super("stop");
        }
    }
