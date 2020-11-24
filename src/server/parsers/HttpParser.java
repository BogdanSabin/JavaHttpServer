package server.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import server.errors.InvalidRequestException;

public class HttpParser {

	public static Map<String, String> parseRequest(BufferedReader request, String[] supportedMethods) throws InvalidRequestException {
		HashMap<String, String> response = new HashMap<String, String>();
		try {
			if(request == null) throw new InvalidRequestException("request is null");
			String input = request.readLine();
			StringTokenizer parse = new StringTokenizer(input); // split the request by " \t\n\r\f"
			String method = parse.nextToken().toUpperCase();
			if(Arrays.asList(supportedMethods).contains(method)) {
				response.put("method", method);
				response.put("fileRequested", parse.nextToken().toLowerCase());
				return response;
			}
			else
				throw new InvalidRequestException("Method " + method + " is not supported by server");
		} catch (IOException e) {
			System.out.println("Error HTTP parser: " + e);
			e.printStackTrace();
			throw new InvalidRequestException("Error HTTP parser: " + e);
		}
	}
}