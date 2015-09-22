package com.seovic.maven.plugins.npm;

import org.apache.maven.plugins.annotations.Mojo;

/**
 * An 'npm prune' mojo.
 *
 * @author Aleksandar Seovic  2015.09.21
 */
@Mojo(name = "prune", threadSafe = true)
public class PruneMojo
        extends ExecMojo
    {
    public PruneMojo()
        {
        super("prune");
        }
    }
