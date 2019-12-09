package ru.ilb.metadataextractor.entities;

import java.util.Map;

/**
 * Extracts metadata from various formats
 * @author slavb
 */
public interface MetadataExtractor {

    /**
     * get property value
     *
     * @param propName
     * @return
     */
    public String getProperty(String propName);

    /**
     * get all properties as map
     *
     * @return
     */
    public Map<String, String> asMap();

}
