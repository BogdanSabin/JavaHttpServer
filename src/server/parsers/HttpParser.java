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
			if(input == null) throw new InvalidRequestException("input is null");
			StringTokenizer parse = new StringTokenizer(input); // split the request by " \t\n\r\f"
			String method = parse.nextToken().toUpperCase();
			if(Arrays.asList(supportedMethods).contains(method)) {
				response.put("method", method);
				response.put("fileRequested", fromatFile(parse.nextToken().toLowerCase()));
				System.out.println("File requested: " + response.get("fileRequested"));
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
	
	private static String fromatFile(String file) {
		String formatted = file.replace("%20", " ");
		return formatted;
	}
}