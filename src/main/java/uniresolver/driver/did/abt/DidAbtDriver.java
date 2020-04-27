package uniresolver.driver.did.abt;

import did.Authentication;
import did.DIDDocument;
import did.PublicKey;
import did.Service;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;
import uniresolver.ResolutionException;
import uniresolver.driver.Driver;
import uniresolver.result.ResolveResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DidAbtDriver implements Driver {

	private static Logger log = LoggerFactory.getLogger(DidAbtDriver.class);

	public static final Pattern DID_ABT_PATTERN = Pattern
			.compile("^did:abt:([123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{35,36})$");

	public static final String[] DIDDOCUMENT_AUTHENTICATION_TYPES = new String[] { "Ed25519SignatureAuthentication2018" };

	public static final String DEFAULT_ABT_URL = "https://did.abtnetwork.io";

	public static final HttpClient DEFAULT_HTTP_CLIENT = HttpClientBuilder.create()
			.setRedirectStrategy(new LaxRedirectStrategy()).build();

	private String abtUrl = DEFAULT_ABT_URL;

	private HttpClient httpClient = DEFAULT_HTTP_CLIENT;

	public DidAbtDriver() {
	}

	@Override
	public ResolveResult resolve(String identifier) throws ResolutionException {
		// match
		Matcher matcher = DID_ABT_PATTERN.matcher(identifier);
		if (!matcher.matches()) {
			return null;
		}

		// fetch data from ABT
		String resolveUrl = this.getABTUrl() + "/1.0/identifiers/" + identifier;
		HttpGet httpGet = new HttpGet(resolveUrl);

		// find the DDO
		JSONObject resultJO;
		DIDDocument didDocument;
		try {
			CloseableHttpResponse httpResponse = (CloseableHttpResponse) this.getHttpClient().execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new ResolutionException("Cannot retrieve DDO for `" + identifier + "` from `" + this.getABTUrl() + ": "
						+ httpResponse.getStatusLine());
			}

			// extract payload
			HttpEntity httpEntity = httpResponse.getEntity();
			String entityString = EntityUtils.toString(httpEntity);
			EntityUtils.consume(httpEntity);

			// get DDO
			resultJO = new JSONObject(entityString);
			JSONObject didDocumentJO = resultJO.getJSONObject("didDocument");
			didDocument = DIDDocument.fromJson(didDocumentJO.toString());
		} catch (IOException ex) {
			throw new ResolutionException(
					"Cannot retrieve DDO info for `" + identifier + "` from `" + this.getABTUrl() + "`: " + ex.getMessage(), ex);
		} catch (JSONException jex) {
			throw new ResolutionException("Cannot parse JSON response from `" + this.getABTUrl() + "`: " + jex.getMessage(),
					jex);
		}

		JSONObject resolverMetadataJO = resultJO.getJSONObject("resolverMetadata");
		Map<String, Object> resolverMetadata = new LinkedHashMap<String, Object>();
		resolverMetadata.put("identifier", resolverMetadataJO.getString("identifier"));
		resolverMetadata.put("driverId", resolverMetadataJO.getString("driverId"));
		resolverMetadata.put("didUrl", resolverMetadataJO.getJSONObject("didUrl").toMap());

		// create RESOLVE RESULT
		ResolveResult resolveResult = ResolveResult.build(didDocument, resolverMetadata, null);

		// done
		return resolveResult;
	}

	@Override
	public Map<String, Object> properties() {

		Map<String, Object> properties = new HashMap<>();
		properties.put("abtUrl", this.getABTUrl());

		return properties;
	}

	/*
	 * Getters and setters
	 */

	public String getABTUrl() {

		return this.abtUrl;
	}

	public void setABTUrl(String abtUrl) {
		this.abtUrl = abtUrl;
	}

	public HttpClient getHttpClient() {

		return this.httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {

		this.httpClient = httpClient;
	}
}
