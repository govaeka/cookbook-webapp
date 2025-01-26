package edu.sb.cookbook.service;

import static jakarta.ws.rs.core.HttpHeaders.WWW_AUTHENTICATE;
import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;
// import java.util.Base64;
import jakarta.annotation.Priority;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import edu.sb.tool.Copyright;


/**
 * JAX-RS filter provider that performs HTTP "Basic" authentication on any REST service request
 * within an HTTP server environment. This aspect-oriented design swaps "Authorization" headers
 * for "X-Requester-Identity" headers within any REST service request being received.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
@Copyright(year=2017, holders="Sascha Baumeister")
public class BasicAuthenticationFilterSkeleton implements ContainerRequestFilter {

	/**
	 * HTTP request header for the authenticated requester's identity.
	 */
	static public final String REQUESTER_IDENTITY = "X-Requester-Identity";


	/**
	 * Performs HTTP "basic" authentication by calculating a password hash from the password contained in the request's
	 * "Authorization" header, and comparing it to the one stored in the person matching said header's username. The
	 * "Authorization" header is consumed in any case, and upon success replaced by a new "X-Requester-Identity" header that
	 * contains the authenticated person's identity. The filter chain is aborted in case of a problem. Note that OPTIONS
	 * requests should never be authenticated to support CORS pre-flight requests; optionally, certain types of GET
	 * requests may also not be authenticated, usually involving media content.
	 * @param requestContext the request context
	 * @throws NullPointerException if the given argument is null
	 */
	public void filter (final ContainerRequestContext requestContext) throws NullPointerException, ClientErrorException {
		// - Abort with Status.BAD_REQUEST if the given request context's headers map already contains a
		//   "X-Requester-Identity" key, in order to prevent spoofing attacks.
		// - Remove the "Authorization" header from said map and store the result in a local variable "credentialsList".
		// - Allow any OPTIONS requests to pass without requiring successful authentication. Also, allow GET and INFO
		//   requests targeting URI paths either being "application.wadl" or starting with "documents" to pass without
		//   requiring successful authentication. This enables CORS service discovery, CORS caching support, and CORS
		//   document access for HTML img elements without authentication.
		// - if the "credentialsList" variable is not empty and its first element starts with "Basic ", parse the first
		//   element's text after "Basic programmatically using Base64.getDecoder().decode(). Use the resulting byte
		//   array to create a new String instance. Split the resulting text into two parts where the first
		//   ':' character is located, which provides the user's email and password.
		// - Perform the PQL-Query "select p from Person as p where p.email = :email", using the credentials email
		//   address part. Note that this query will go to the second level cache before hitting the database if the
		//   Person#email field is annotated using @CacheIndex(updateable=true)! 
		// - if the resulting people list contains exactly one element, calculate the hex-string representation
		//   (i.e. 2 digits per byte) of the SHA2-256 hash code of the credential's password part using
		//   HashCodes.sha2HashText(256, password).
		// - if this hash representation is equal to the queried person's password hash, add a new "X-Requester-Identity"
		//   header to the request headers, using the person's identity converted to String as value, and return
		//   from this method.
		// - in all other cases, abort the request using requestContext.abortWith() in order to challenge the client
		//   to provide HTTP Basic credentials (i.e. status code 401 with "WWW-Authenticate" header value "Basic").
		requestContext.abortWith(Response.status(UNAUTHORIZED).header(WWW_AUTHENTICATE, "Basic").build());
	}
}