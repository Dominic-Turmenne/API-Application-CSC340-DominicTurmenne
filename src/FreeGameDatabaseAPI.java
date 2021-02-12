import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
2/12/2021
This class uses the www.freetogame.com API to display general game info to the user.
Since this is a prototype, it will only display info on Call of Duty: Warzone
Author(s): Dominic Turmenne
*/

class FreeGameDatabaseAPI {
    
    public static void getGameInfo(){
        // Create HTTP Connection.
        String baseURL = "https://www.freetogame.com";
        String callAction = "/api/game";
        String gameId = "452";
        String urlString = baseURL + callAction + "?id=" + gameId;
        URL url;
        try {
            // Make the connection.
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Examine response code.
            int responseCode = connection.getResponseCode();
            if (responseCode != 200){
                System.out.println("Error: Could not load game information: " + responseCode);
            } else {
                // Parsing input stream into a text string.
                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = input.readLine()) != null) {
                    content.append(inputLine);
                }
                // Close connections.
                input.close();
                connection.disconnect();
                // Print out JSON string.
                System.out.println("Output: " + content.toString());
                // Parse into JSON object.
                JSONObject object = new JSONObject(content.toString());
                // Display the name of the game.
                String gameName = object.getString("title");
                System.out.println("------------------------------" + '\n' + 
                        "Game: " + gameName + 
                        '\n' + "------------------------------");
                // Access information about the game from JSON
                String gameGenre = object.getString("genre");
                String gameDeveloper = object.getString("developer");
                String gameReleaseDate = object.getString("release_date");
                String gameDescription = object.getString("short_description");              
                // Display summary text about the game.
                System.out.println(gameName + " is a " + gameGenre + " game developed by " 
                        + gameDeveloper + " and was released on " + gameReleaseDate + ".");
                // Display the game description.
                System.out.println("Description from website: " + gameDescription);
            }   
        } catch (Exception exception) {
            System.out.println("Error: " + exception);
            return;
        }
    }
}
