
import com.neovisionaries.i18n.CountryCode;
import processing.core.*;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
//import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;

import java.io.File;
//import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

//import org.apache.hc.core5.http.ParseException;

public class ClientCredentialsExample extends PApplet{
  private static final String clientId = "2148b8ad70a346f79d855789069ddba5";
  private static final String clientSecret = "c8ba8c74ac5a4257b8ed404c23fd197e";
  private static String token = "";
	private static final String q = "Disturbed";
	private static final String type = ModelObjectType.TRACK.getType();
	  private static String id = "";
	  private static int begin = 0;
	  private static int end = 0;
	  
	  public static String clientCredentials_Async() {
		    try {
		      final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();

		      // Thread free to do other tasks...

		      // Example Only. Never block in production code.
		      final ClientCredentials clientCredentials = clientCredentialsFuture.join();

		      // Set access token for further "spotifyApi" object usage
		      spotifyApi.setAccessToken(clientCredentials.getAccessToken());
		      spotifyApi.setRefreshToken(token);
		      token = clientCredentials.getAccessToken();
		      System.out.println(token);
		      System.out.println("Expires in: " + clientCredentials.getExpiresIn());
		    } catch (CompletionException e) {
		      System.out.println("Error: " + e.getCause().getMessage());
		    } catch (CancellationException e) {
		      System.out.println("Async operation cancelled.");
		    }
		    return token;
		  }
  private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
		  .setAccessToken(token)
    .setClientId(clientId)
    .setClientSecret(clientSecret)
    .build();

  private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
    .build();


//  public static void clientCredentials_Sync() {
//    try {
//      final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
//
//      // Set access token for further "spotifyApi" object usage
//      spotifyApi.setAccessToken(clientCredentials.getAccessToken());
//
//      System.out.println("Expires in: " + clientCredentials.getExpiresIn());
//    } catch (IOException | SpotifyWebApiException | ParseException e) {
//      System.out.println("Error: " + e.getMessage());
//    }
//  }


  private static final SearchItemRequest searchItemRequest = spotifyApi.searchItem(q, type)
		  
          .market(CountryCode.US)
          .limit(2)
          .offset(0)
          .includeExternal("audio")
          
    .build();
  public static void searchItem_ASync() {
	  
	    try {
	      final CompletableFuture<SearchResult> searchResultFuture = searchItemRequest.executeAsync();
	      final SearchResult searchResult = searchResultFuture.join();
	      id = searchResult.getTracks().toString();
//	      System.out.println(id);
	      begin = id.indexOf("open.spotify.com/track");
	      id = id.substring(begin);
	      end = id.indexOf('}');
	      id = id.substring(0,end);
	      System.out.println(id);
	    
//	      System.out.println("Total tracks: " + searchResult.getTracks().toString());
	      
	    } catch (CompletionException e) {
	        System.out.println("Error: " + e.getCause().getMessage());
	    } catch (CancellationException e) {
	      System.out.println("Async operation cancelled.");
	    }
	  }
	public static void main(String[] args) {

		clientCredentials_Async();
		searchItem_ASync();
		String download_path="/Users/philippe/Documents/Processing/libraries/spotify-web-api-java";
		String url="https://open.spotify.com/track/0b7b3OR4cKHo08rdpLHofM?si=5d55e693dde94849";
		File file = new File(download_path);
		String[] paths;
		  paths = file.list();
		String[] command =
	    {
	        "sh",
	    };
		
	    Process p;
		try {
			p = Runtime.getRuntime().exec(command); 
		        new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
	                new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
	                PrintWriter stdin = new PrintWriter(p.getOutputStream());
	                stdin.println("export SPOTIPY_CLIENT_ID=2148b8ad70a346f79d855789069ddba5"); //set client credentials
	                stdin.println("export SPOTIPY_CLIENT_SECRET=c8ba8c74ac5a4257b8ed404c23fd197e");
	    		    stdin.println("export PATH=\"/usr/local/bin:$PATH\"");
	                stdin.println("cd \""+download_path+"\"");
//	                stdin.println(download_path+"\\/youtube-dl --ffmpeg-location /usr/local/Cellar/ffmpeg/4.4_2/bin/ffmpeg");
	                stdin.println(download_path+"\\/spotify_dl -l"+url+" -o "+download_path+" -f 'bestaudio[ext=m4a]/mp4'");
	                
	                stdin.close();
	                p.waitFor();
	    	} catch (Exception e) {
	 		e.printStackTrace();
		}
		 for(String two:paths) {
             
             // prints filename and directory name
             System.out.println(two);
          }
	}	
}
