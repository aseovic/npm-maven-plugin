package com.seovic.maven.plugins.npm;

import org.apache.maven.plugins.annotations.Mojo;

/**
 * An 'npm update' mojo.
 *
 * @author Aleksandar Seovic  2015.09.21
 */
@Mojo(name = "update", threadSafe = true)
public class UpdateMojo
        extends ExecMojo
    {
    public UpdateMojo()
        {
        super("update");
        }
    }
