package com.seovic.maven.plugins.npm;

import org.apache.maven.plugins.annotations.Mojo;

/**
 * An 'npm test' mojo.
 *
 * @author Aleksandar Seovic  2015.09.21
 */
@Mojo(name = "test", threadSafe = true)
public class TestMojo
        extends ExecMojo
    {
    public TestMojo()
        {
        super("test");
        }
    }
