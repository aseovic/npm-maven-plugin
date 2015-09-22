package com.seovic.maven.plugins.npm;

import org.apache.maven.plugins.annotations.Mojo;

/**
 * An 'npm start' mojo.
 *
 * @author Aleksandar Seovic  2015.09.21
 */
@Mojo(name = "start", threadSafe = true)
public class StartMojo
        extends ExecMojo
    {
    public StartMojo()
        {
        super("start");
        }
    }
