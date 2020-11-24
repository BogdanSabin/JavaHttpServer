package tests.unit.parsers;

public class HttpRequestProvider {
	public static final String file = "/hello.htm";
	public static final String GET_REQUEST = "GET " + HttpRequestProvider.file + " HTTP/1.1\r\n"
			+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n" + "Host: www.myserver.com\r\n"
			+ "Accept-Language: en-us\r\n" + "Accept-Encoding: gzip, deflate\r\n" + "Connection: Keep-Alive";

	public static final String INVALID_REQUEST = HttpRequestProvider.file + " GET HTTP/1.1\r\n"
			+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n" + "Host: www.myserver.com\r\n"
			+ "Accept-Language: en-us\r\n" + "Accept-Encoding: gzip, deflate\r\n" + "Connection: Keep-Alive";

	public static final String UNKNOWN_REQUEST = "POST " + HttpRequestProvider.file + " HTTP/1.1\r\n"
			+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n" + "Host: www.myserver.com\r\n"
			+ "Content-Type: application/x-www-form-urlencoded\r\n" + "Content-Length: length\r\n"
			+ "Accept-Language: en-us\r\n" + "Accept-Encoding: gzip, deflate\r\n" + "Connection: Keep-Alive";

	public static final String HEAD_REQUEST = "HEAD " + HttpRequestProvider.file + " HTTP/1.1\r\n"
			+ "Accept: application/json\r\n" + "Host: reqbin.com";

}
