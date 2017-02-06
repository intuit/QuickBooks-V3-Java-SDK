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
package com.intuit.ipp.interceptors;

import java.util.List;

import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.util.Logger;

/**
 * This class changes interceptors execution flow to support multiple uploads
 * It also implement Interceptor interface to graceful shutdown original connection interceptor behavior
 * 
 */
public class IntuitBatchInterceptorProvider extends IntuitInterceptorProvider implements Interceptor {

    /**
     * logger instance
     */
    private static final org.slf4j.Logger LOG = Logger.getLogger();

    /**
     * Constructor IntuitInterceptorProvider
     */
    public IntuitBatchInterceptorProvider() {
        super();
        swapRequestInterceptor(HTTPClientConnectionInterceptor.class, this);
    }
    /**
     * Swap one interceptor with new one
     * @param target subclass of interceptor
     * @param interceptor new interceptor which will be used instead
     * TODO do we need to pay attention to safe threading here (expected answer: no)
     */
    private void swapRequestInterceptor(Class<? extends Interceptor> target, Interceptor interceptor)
    {
        List<Interceptor> list = this.getRequestInterceptors();

        for(Interceptor object : list) {
            if(object.getClass() == target) {
                list.set(list.indexOf(object),interceptor);
            }
        }

        this.setRequestInterceptors(list);
    }

    /**
     * Method executes original interceptors in specific sequence. It supports multiple IntuitMessages
     * @param intuitMessages
     * @throws FMSException
     *
     */
    public void executeInterceptors(final List<IntuitMessage> intuitMessages) throws FMSException {

        for(IntuitMessage message : intuitMessages) {
            executeRequestInterceptors(message);
        }
        HTTPBatchClientConnectionInterceptor batchConnectionInterceptor = new HTTPBatchClientConnectionInterceptor();
        batchConnectionInterceptor.execute(intuitMessages);
        for(IntuitMessage message : intuitMessages) {
            try {
                message.getResponseElements().getResponseContent().reset();
            } catch (Exception ex) {
                LOG.error("IllegalStateException while get the content from HttpRespose.", ex);
                throw new FMSException(ex);
            }
            executeResponseInterceptors(message);
        }

    }

    /**
     * Method does nothing. It replaces original execution of connection interceptor
     * to support multiple IntuitMessage instances
     * @param intuitMessage the intuit message
     * @throws FMSException
     */
    @Override
    public void execute(IntuitMessage intuitMessage) throws FMSException {

    }



    
}
