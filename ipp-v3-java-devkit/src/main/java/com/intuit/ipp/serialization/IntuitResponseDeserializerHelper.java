/*******************************************************************************
 * Copyright (c) 2017 Intuit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.intuit.ipp.serialization;

import com.intuit.ipp.data.IntuitEntity;
import com.intuit.ipp.util.Config;
import com.intuit.ipp.util.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Created by ssadhoo on 8/24/15.
 */
public class IntuitResponseDeserializerHelper {

    /**
     * logger instance
     */
    private static final org.slf4j.Logger LOG = Logger.getLogger();

    /**
     * Interface which represent additional features
     */
    protected interface Feature {

        public void execute();
        public <T extends IntuitEntity> void set(T object);

    }

    /**
     * Executes feature if switch setting is on
     * @param featureSwitch
     * @param feature
     */
    protected void invokeFeature(String featureSwitch, Feature feature ) {
        if(Config.getBooleanProperty(featureSwitch,true)) {
            feature.execute();
        }
    }

    /**
     * Updates instances of BigDecimal with new scale in intuitEntity
     * @param intuitType
     */
    protected void updateBigDecimalScale(IntuitEntity intuitType) {
        Feature feature =  new Feature() {
            private IntuitEntity obj;
            public <T extends IntuitEntity> void set(T object) {
                obj = object;
            }
            public void execute() {
                (new BigDecimalScaleUpdater()).execute(obj);
            }
        };
        feature.set(intuitType);

        invokeFeature(Config.BIGDECIMAL_SCALE_SHIFT, feature);
    }

    protected class BigDecimalScaleUpdater {
        private final String GET = "get";
        private final String SET = "set";
        // New grade
        private final int POSITION_AFTER_PERIOD = 2;

        /**
         * Executes operation on a entity
         * It logs all exceptions and does nothing if they were thrown
         * @param entity instance of IntuitEntity
         */
        public void execute(IntuitEntity entity) {
            for(Field field : entity.getClass().getDeclaredFields()) {
                if(!isDecimal(field)) { continue; }
                if(!isGetter (field)) { continue; }
                if(!isSetter (field)) { continue; }
                try {
                    scaleTo(entity, field, POSITION_AFTER_PERIOD);
                } catch (Exception ex) {
                    handleException(ex, field.getDeclaringClass().getSimpleName() + "." + field.getName());
                    continue;
                }

            }
        }

        /**
         * Returns true if property has a setter method
         * @param field property field of the object
         * @return
         */
        private boolean isSetter(Field field)
        {
            return checkMethod(field,SET);
        }

        /**
         * Returns true if property has a getter method
         * @param field property field of the object
         * @return
         */
        private boolean isGetter(Field field)
        {
            return checkMethod(field, GET);
        }

        /**
         * Verifies if method exists
         * @param field property field
         * @param prefix prefix, which is "set", "get" or another
         * @return
         */
        private boolean checkMethod(Field field, String prefix)
        {
            try {
                Method method = getMethod(field, prefix);
                return null != method;
            } catch (NoSuchMethodException ex) {
                writeDebug("No "+prefix+"ter for " + field.getName());
            }
            return false;
        }

        /**
         * Update scale of BigDecimal using associated getter and setter. Scale is changed if it doesn't equal new value
         * @param entity instance of IntuitEntity
         * @param field property, which has BigDecimal type
         * @param newScale new scale
         * @throws NoSuchMethodException
         * @throws IllegalAccessException
         * @throws InvocationTargetException
         */
        private void scaleTo(IntuitEntity entity, Field field, int newScale) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
        {
            BigDecimal value = (BigDecimal)(getMethod(field, GET).invoke(entity));
            if(value != null && newScale != value.scale()) {
                getMethod(field, SET).invoke(entity,value.setScale(newScale));
            }
        }

        /**
         * Logs everything into parent log
         * @param ex extension
         * @param name string which contains name or identification for operation
         */
        private void handleException(Exception ex, String name)
        {
            if(ex instanceof NoSuchMethodException) {
                writeDebug("No getter for " + name + "." );
            }

            if(ex instanceof IllegalAccessException) {
                writeDebug("Access to " + name + " using setter and getter methods failed." );
            }

            if(ex instanceof InvocationTargetException) {
                writeDebug("Bad target invocation for setter/getter for " + name + " field." );
            }

            if(ex instanceof Exception) {
                writeDebug("Unexpected exception for  " + name + " using setter/getter method. Message: " + ex.getMessage());
            }
        }

        /**
         * Returns true if passed field is BigDecimal
         * @param field
         * @return
         */
        private boolean isDecimal(Field field)
        {
            return field.getType().equals(BigDecimal.class);
        }

        /**
         * Returns method representation for specified field name and postfix.
         * It tries to format method name in camel style (getField, setField etc)
         * IT also uses BigDecimal class as single argument for if prefix equals "set"
         *
         * @param field
         * @param prefix
         * @return
         * @throws NoSuchMethodException
         */
        @SuppressWarnings("unchecked")
        private Method getMethod(Field field, String prefix) throws NoSuchMethodException
        {
            Class clazz = field.getDeclaringClass();
            String methodName = prefix + upperCaseFirstLetter(field.getName());
            return SET.equals(prefix) ? clazz.getMethod(methodName, BigDecimal.class)
                    : clazz.getMethod(methodName);
        }

        /**
         * Utility method. It upper case first letter in the passed string
         * @param input
         * @return
         */
        private String upperCaseFirstLetter(String input)
        {
            if(input.length() == 0)
                return input;
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }

        /**
         * Writes messages into parent logger
         * @param message
         */
        private void writeDebug(String message)
        {
            LOG.debug(message);
        }

    }
}
