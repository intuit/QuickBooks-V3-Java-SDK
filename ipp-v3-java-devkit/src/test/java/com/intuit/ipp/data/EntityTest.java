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
package com.intuit.ipp.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import com.intuit.ipp.core.IEntity;
import com.intuit.ipp.data.Customer;
import com.intuit.ipp.data.IntuitResponse;
import com.intuit.ipp.extra.SerializeXML;
import com.intuit.ipp.util.Logger;

public class EntityTest {
	
	private static final org.slf4j.Logger LOG = Logger.getLogger();
	
	//@Test
	public void testEntitySerialization(){
		Customer customer = new Customer();
		customer.setAcctNum("99999");
		customer.setGivenName("Lokesh");
		customer.setMiddleName("");
		customer.setFamilyName("Gupta");
		customer.setDisplayName("Loki");
		customer.setActive(false);
		customer.setJob(false);
		
		EntityTest test = new EntityTest();
		test.add(customer);
	}
	
	public <T extends IEntity> IntuitResponse add(T entity) {
		doPost(entity);
		return null;
	}
	
	public String doPost(IEntity entity) {
		
		String bodyData = SerializeXML.serialize(entity);
		HttpClient httpclient = new DefaultHttpClient();
		HttpHost proxy = new HttpHost("127.0.0.1", 8888);
		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

		httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, 
			    HttpVersion.HTTP_1_0);
		
		
		HttpPost httpPost = new HttpPost(createURI(entity));
		httpPost.addHeader("Authorization",getAuthString());
		try {
			StringEntity bodyDataEntity = new StringEntity(bodyData);
			httpPost.setEntity(bodyDataEntity);
		
			HttpResponse response = httpclient.execute(httpPost);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			StringBuilder builder = new StringBuilder();
			for (String line = null; (line = reader.readLine()) != null;) {
			    builder.append(line).append("\n");
			}
			LOG.debug(builder.toString());
			return builder.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String createURI(IEntity entity) {
		String uri = "https://services.intuit.com/v3/company/"; 
		String companyId = "318318240";
		String entity_name = entity.getClass().getSimpleName().toLowerCase();
		return uri + companyId + "/" +entity_name;
	}
	
	public String getAuthString(){
		return "OAuth oauth_token=\"replace oauth token here\",oauth_nonce=\"9f69d439-ca71-4e1f-b3d1-5e753bad6379\",oauth_consumer_key=\"qye2eKNlWXp9KoYhA6BQT8izLpu9ho\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"1340286917\",oauth_version=\"1.0\",oauth_signature=\"Cpd27aZYhEZmFgFVFSvuCVWT82c%3D\"";
	}
}