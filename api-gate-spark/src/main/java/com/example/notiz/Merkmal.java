package com.example.notiz;

import static spark.Spark.*;
import spark.Request;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.*;
import com.google.gson.JsonParser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

//src: https://github.com/perwendel/spark/blob/master/README.md#examples
/**
 * A simple CRUD example showing how to create, get, update and delete notebook resources.
 */
public class Merkmal {

    /**
     * Map holding the notebooks
     */
    private static Map<String, Notebook> notebooks = new HashMap<String, Notebook>();
    /* Map holding the notes */
    private static Map<String, Note> notes = new HashMap<String, Note>();

    private static JsonObject respToJson( Request request ){
      JsonParser parser = new JsonParser();
      JsonElement respData = parser.parse(request.body());
      if (!respData.isJsonObject()) {
        // TODO
      }
      JsonObject obj = respData.getAsJsonObject();
      return obj;
    }

    public static void main(String[] args) {
        port(8080);
        final Random random = new Random();
        Gson gson = new Gson();

        // Creates a new notebook resource, will return the ID to the created resource
        // author and title are sent in the post body as x-www-urlencoded values e.g. author=Foo&title=Bar
        // you get them by using request.queryParams("valuename")
        post("/notebooks/create", (request, response) -> {
          /*
            ObjectMapper mapper = new ObjectMapper();
            Notebook newNotebook = mapper.readValue(request.body(), Notebook.class);
            System.out.println("mapper: " + newNotebook.toString());
            */
            //System.out.println("request: " + request.body());
            //System.out.println("response: " + response.body());
            JsonObject obj = respToJson( request );
            /* skip validation:
             * if (!obj.hasField("author")){}
             * if (!obj.hasField("title")){}
             */
            JsonElement titleAsElem = obj.get("title");
            JsonElement authorAsElem = obj.get("author");
            Notebook notebook = new Notebook(authorAsElem.getAsString(), titleAsElem.getAsString());

            int id = random.nextInt(Integer.MAX_VALUE);
            String id_str = String.valueOf(id);
            notebooks.put(String.valueOf(id), notebook);
            notebook.setId( id );

            response.status(201); // 201 Created
            response.type("application/json");
            return( notebooks.get(id_str).toString() );
        });

        // Gets the notebook resource for the provided id
        get("/notebooks/:id", (request, response) -> {
            Notebook notebook = notebooks.get(request.params(":id"));
            if (notebook != null) {
                return( notebook.toString() );
            } else {
                response.status(404); // 404 Not found
                return "{\"error\":\"notebook not found\"}";  // TODO: proper JSON and include the id
            }
        });
            //        }, json());

        // Updates the notebook resource for the provided id with new information
        // author and title are sent in the request body as x-www-urlencoded values e.g. author=Foo&title=Bar
        // you get them by using request.queryParams("valuename")
        post("/notebooks/:id/update", (request, response) -> {
            String id = request.params(":id");
            Notebook notebook = notebooks.get(id);
            if (notebook != null) {
                JsonObject obj = respToJson( request );
                JsonElement titleAsElem = obj.get("title");
                JsonElement authorAsElem = obj.get("author");
                String newAuthor = titleAsElem.getAsString();
                String newTitle = authorAsElem.getAsString();
                if (newAuthor != null) {
                    notebook.setAuthor(newAuthor);
                }
                if (newTitle != null) {
                    notebook.setTitle(newTitle);
                }
                return( notebook.toString() );
                //return "Notebooks with id '" + id + "' updated";
            } else {
                response.status(404); // 404 Not found
                return "{\"error\":\"notebook not found\"}";  // TODO: proper JSON and include the id
            }
        });

        // Deletes the notebook resource for the provided id
        post("/notebooks/:id/delete", (request, response) -> {
            String id = request.params(":id");
            Notebook notebook = notebooks.remove(id);
            if (notebook != null) {
                return String.format("{\"deleted\" : [ \"%s\" ]}", id);
            } else {
                response.status(404); // 404 Not found
                return "{\"error\":\"notebook not found\"}";  // TODO: proper JSON and include the id
            }
        });

        // Gets all available notebook resources (ids)
        get("/notebooks", (request, response) -> {
            String ids = "";
            for (String id : notebooks.keySet()) {
                ids += id + " ";
            }
            return ids;
        });
        // TODO: dedupe - consolidate with 'get("/notebooks")'
        get("/", (request, response) -> {
            String ids = "";
            for (String id : notebooks.keySet()) {
                ids += id + " ";
            }
            return ids;
        });

        // Creates a new note within an existing notebook resource, will return the ID to the created resource
        // title, body, tags[]
        post("/notebooks/:nbid/create", (request, response) -> {
            JsonObject obj = respToJson( request );
            JsonElement titleAsElem = obj.get("title");
            JsonElement bodyAsElem = obj.get("body");
            JsonElement tagsAsElem = obj.get("tags");
            System.out.println( tagsAsElem.toString() );
            JsonArray tagsAsArray = obj.get("tags").getAsJsonArray();
            String title = titleAsElem.getAsString();
            String body = bodyAsElem.getAsString();
            //String[] tags = tagsAsElem.getAsJsonArray(); // {}; // TODO: fix
            System.out.println("JsonAarry size " + tagsAsArray.size());
            String[] tags = new String[ tagsAsArray.size() ];
            System.out.println("String[] tags " + Arrays.toString(tags));
            Iterator itr = tagsAsArray.iterator();
            //while (itr.hasNext()){
            //    tags.
            //}
            // https://javadoc.io/static/com.google.code.gson/gson/2.8.5/com/google/gson/JsonArray.html#get-int-
            // https://stackoverflow.com/a/33421601
            for ( int i=0; i<tags.length; i++){
              tags[i] = tagsAsArray.get(i).getAsString();
              System.out.println("derpA " + tagsAsArray.get(i));
              System.out.println("derpB " + tagsAsArray.get(i).getAsString());
            }
            System.out.println("String[] tags " + tags.toString());
            Note note = new Note( title, body, tags );

            int id = random.nextInt(Integer.MAX_VALUE);
            String id_str = String.valueOf(id);
            notes.put(String.valueOf(id), note);
            note.setId( id );

            System.out.println("post note: " + note.toString());
            System.out.println("post note: " + Arrays.toString(note.getTags()));

            response.status(201); // 201 Created
            response.type("application/json");
            return( notes.get(id_str).toString() );
        });

    }

}
