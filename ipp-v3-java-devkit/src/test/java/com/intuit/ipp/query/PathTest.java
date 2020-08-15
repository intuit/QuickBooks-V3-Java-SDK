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
package com.intuit.ipp.query;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PathTest {

    /**
     * @author enzozafra
     */
    private Path path;

    private String entity;
    private String pathString;

    @BeforeTest
    public void init() {
        entity = "entity";
        pathString = "pathString";
    }

    @BeforeMethod
    public void setUp() {
        path = new Path(pathString, entity);
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(path.getEntity(), entity);
        Assert.assertEquals(path.getPathString(), pathString);
    }

    @Test
    public void testAllSetters() {
        String newEntity = "new Entity";
        String newPathString = "new PathString";

        path.setEntity(newEntity);
        path.setPathString(newPathString);

        Assert.assertEquals(path.getEntity(), newEntity);
        Assert.assertEquals(path.getPathString(), newPathString);
    }
}