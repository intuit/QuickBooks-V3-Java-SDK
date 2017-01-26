/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
