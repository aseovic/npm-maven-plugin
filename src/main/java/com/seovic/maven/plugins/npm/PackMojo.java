package com.seovic.maven.plugins.npm;

import org.apache.maven.plugins.annotations.Mojo;

/**
 * An 'npm pack' mojo.
 *
 * @author Aleksandar Seovic  2015.09.21
 */
@Mojo(name = "pack", threadSafe = true)
public class PackMojo
        extends ExecMojo
    {
    public PackMojo()
        {
        super("pack");
        }
    }
