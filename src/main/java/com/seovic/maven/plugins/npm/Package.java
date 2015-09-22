package com.seovic.maven.plugins.npm;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Simple wrapper around package.json.
 *
 * @author Aleksandar Seovic  2015.09.22
 */
public class Package
        extends LinkedHashMap<String, Object>
    {
    // ---- factory method --------------------------------------------------

    /**
     * Parse package.json.
     *
     * @param file  the file to parse
     *
     * @return a new Package instance for the specified file
     *
     * @throws IOException  if an error occurs
     */
    public static Package parse(File file) throws IOException
        {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, Package.class);
        }

    // ---- accessors -------------------------------------------------------

    public String getName()
        {
        return (String) get("name");
        }

    public String getVersion()
        {
        return (String) get("version");
        }

    public String getDescription()
        {
        return (String) get("description");
        }

    public Map<String, Object> getScripts()
        {
        Map<String, Object> scripts = (Map<String, Object>) get("scripts");
        return scripts == null
               ? Collections.<String, Object>emptyMap()
               : scripts;
        }

    public String getScript(String sName)
        {
        return (String) getScripts().get(sName);
        }

    public boolean hasScript(String script)
        {
        return getScripts().containsKey(script);
        }
    }
