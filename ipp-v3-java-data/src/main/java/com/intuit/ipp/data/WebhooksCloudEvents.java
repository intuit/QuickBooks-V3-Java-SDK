/*******************************************************************************
 * Copyright (c) 2025 Intuit
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
 ******************************************************************************/
package com.intuit.ipp.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * New CloudEvents-based webhook event item.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhooksCloudEvents {

    @JsonProperty("specversion")
    private String specVersion;

    @JsonProperty("id")
    private String id;

    @JsonProperty("source")
    private String source;

    @JsonProperty("type")
    private String type;

    @JsonProperty("datacontenttype")
    private String dataContentType;

    @JsonProperty("time")
    private String time;

    @JsonProperty("intuitentityid")
    private String intuitEntityId;

    @JsonProperty("intuitaccountid")
    private String intuitAccountId;

    @JsonProperty("data")
    private Map<String, Object> data;

    public String getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIntuitEntityId() {
        return intuitEntityId;
    }

    public void setIntuitEntityId(String intuitEntityId) {
        this.intuitEntityId = intuitEntityId;
    }

    public String getIntuitAccountId() {
        return intuitAccountId;
    }

    public void setIntuitAccountId(String intuitAccountId) {
        this.intuitAccountId = intuitAccountId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}


