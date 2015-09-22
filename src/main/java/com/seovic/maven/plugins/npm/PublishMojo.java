package com.seovic.maven.plugins.npm;

import org.apache.maven.plugins.annotations.Mojo;


/**
 * An 'npm publish' mojo.
 *
 * @author Aleksandar Seovic  2015.09.21
 */
@Mojo(name = "publish", threadSafe = true)
public class PublishMojo
        extends ExecMojo
    {
    public PublishMojo()
        {
        super("publish");
        }
    }
