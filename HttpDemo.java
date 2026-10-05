import java.net.URI;
import java.net.http.*;

public class HttpDemo {
    public static void main ( String[] args ) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder( URI.create("https://v1.hitokoto.cn")).build();
        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

        System.out.println ( "状态码："  + resp.statusCode());
        System.out.println (resp.body ()) ;
    }
}
