package tests.unit.parsers;

public class HttpRequestProvider {
	public static final String file = "/hello.htm";
	public static final String file_with_space1 = "/he%20llo.htm";
	public static final String file_with_space1_decoded = "/he llo.htm";
	public static final String file_with_space2 = "/he%20l%20lo.htm";
	public static final String file_with_space2_decoded = "/he l lo.htm";
	public static final String file_with_space3 = "/he%20%20llo.htm";
	public static final String file_with_space3_decoded = "/he  llo.htm";
	public static final String file_with_space4 = "/he%20l%l20o.htm";
	public static final String file_with_space4_decoded = "/he l%l20o.htm";
		
	public static final String GET_REQUEST = "GET " + HttpRequestProvider.file + " HTTP/1.1\r\n"
			+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n" + "Host: www.myserver.com\r\n"
			+ "Accept-Language: en-us\r\n" + "Accept-Encoding: gzip, deflate\r\n" + "Connection: Keep-Alive";
	
	public static final String GET_REQUEST_fil1 = "GET " + HttpRequestProvider.file_with_space1 + " HTTP/1.1\r\n"
			+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n" + "Host: www.myserver.com\r\n"
			+ "Accept-Language: en-us\r\n" + "Accept-Encoding: gzip, deflate\r\n" + "Connection: Keep-Alive";
	
	public static final String GET_REQUEST_file2 = "GET " + HttpRequestProvider.file_with_space2 + " HTTP/1.1\r\n"
			+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n" + "Host: www.myserver.com\r\n"
			+ "Accept-Language: en-us\r\n" + "Accept-Encoding: gzip, deflate\r\n" + "Connection: Keep-Alive";
	
	public static final String GET_REQUEST_file3 = "GET " + HttpRequestProvider.file_with_space3 + " HTTP/1.1\r\n"
			+ "User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n" + "Host: www.myserver.com\r\n"
			+ "Accept-Language: en-us\r\n" + "Accept-Encoding: gzip, deflate\r\n" + "Connection: Keep-Alive";
	
	public static final String GET_REQUEST_file4 = "GET " + HttpRequestProvider.file_with_space4 + " HTTP/1.1\r\n"
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
