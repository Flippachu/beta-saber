

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.special.SearchResult;
import com.wrapper.spotify.requests.data.search.SearchItemRequest;
import org.apache.hc.core5.http.ParseException;
import java.util.Scanner;
import java.io.IOException;


public class SearchTracksExample {
  static Scanner in = new Scanner(System.in);
  private static final String accessToken = ClientCredentialsExample.clientCredentials_Async();
  private static final String q = "Eve leo";
  private static final String type = ModelObjectType.TRACK.getType();
  private static String id = "";
  private static String id2 = "";
//  private static String title="";
  private static int begin = 0;
  private static int begin2 = 0;
  private static int end = 0;
  private static int end2 = 0;
  
  
  


 
  public static String[] searchItem_Sync(String sent) {
	  System.out.println(sent);
	  final SpotifyApi spotifyApi = new SpotifyApi.Builder()
			    .setAccessToken(accessToken)
			    .build();
			  final SearchItemRequest searchItemRequest = spotifyApi.searchItem(sent, type)
			          .market(CountryCode.US)
			          .limit(2)
			          .offset(0)
			          .includeExternal("audio")
			    .build();
    try {
      final SearchResult searchResult = searchItemRequest.execute();
      id = searchResult.getTracks().toString();
      id2 = searchResult.getTracks().toString();
      
      System.out.println(id2);
      begin2 = id2.indexOf("name=");
      id2=id2.substring(begin2);
      end2 = id2.indexOf(", artists");
      id2=id2.substring(5,end2);
      
      System.out.println(id2);
      
      begin = id.indexOf("open.spotify.com/track");
      id = id.substring(begin);
      end = id.indexOf('}');
      id = id.substring(0,end);
//      
//   
    
//      System.out.println("Total tracks: " + searchResult.getTracks().toString());
      
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
    System.out.println(id);
    String[]together = {id2,id};
	return together;
  }


//  public static void searchItem_Async() {
//    try {
//      final CompletableFuture<SearchResult> searchResultFuture = searchItemRequest.executeAsync();
//
//      // Thread free to do other tasks...
//
//      // Example Only. Never block in production code.
//      final SearchResult searchResult = searchResultFuture.join();
//
//      System.out.println("Total tracks: " + searchResult.getTracks().getTotal());
//    } catch (CompletionException e) {
//      System.out.println("Error: " + e.getCause().getMessage());
//    } catch (CancellationException e) {
//      System.out.println("Async operation cancelled.");
//    }
//  }

  public static void main(String[] args) {

    searchItem_Sync(q);
  }
}
  
