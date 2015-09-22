package com.seovic.maven.plugins.npm;

import org.apache.maven.plugins.annotations.Mojo;

/**
 * An 'npm install' mojo.
 *
 * @author Aleksandar Seovic  2015.09.21
 */
@Mojo(name = "install", threadSafe = true)
public class InstallMojo extends ExecMojo
    {
    public InstallMojo()
        {
        super("install");
        }
    }
