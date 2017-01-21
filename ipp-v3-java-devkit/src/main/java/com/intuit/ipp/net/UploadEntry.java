package com.intuit.ipp.net;

/**
 * Structure which ties intuit entity (like attachable) with InputStream
 */

import com.intuit.ipp.core.IEntity;

import java.io.InputStream;

public class  UploadEntry <T extends IEntity> {
    /**
     * Intuit entity
     */
    T entity = null;

    /**
     * Stream to content for upload
     * One might want to use Attachable and input stream for upload
     */
    InputStream stream = null;

    public UploadEntry (T entity, InputStream in) {
        this.entity = entity;
        stream = in;
    }

    /**
     * Returns associated stream
     * @return stream
     */
    public InputStream getStream() {
        return stream;
    }

    /**
     * Returns associated entity
     * @return entity
     */
    public T getEntity() {
        return entity;
    }

    /**
     * Method checks properties for null values
     * @return boolean
     */
    public boolean isEmpty()
    {
        return (null == entity) || (null == stream);
    }

}
