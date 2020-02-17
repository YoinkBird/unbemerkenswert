package com.example.notiz;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//src: https://github.com/perwendel/spark/blob/master/README.md#examples
/**
 * A simple CRUD example showing how to create, get, update and delete notebook resources.
 */
public class Merkmal {

    /**
     * Map holding the notebooks
     */
    private static Map<String, Notebook> notebooks = new HashMap<String, Notebook>();

    public static void main(String[] args) {
        port(8080);
        final Random random = new Random();

        // Creates a new notebook resource, will return the ID to the created resource
        // author and title are sent in the post body as x-www-urlencoded values e.g. author=Foo&title=Bar
        // you get them by using request.queryParams("valuename")
        post("/notebooks/create", (request, response) -> {
            String author = request.queryParams("author");
            String title = request.queryParams("title");
            Notebook notebook = new Notebook(author, title);

            int id = random.nextInt(Integer.MAX_VALUE);
            notebooks.put(String.valueOf(id), notebook);

            response.status(201); // 201 Created
            return id;
        });

        // Gets the notebook resource for the provided id
        get("/notebooks/:id", (request, response) -> {
            Notebook notebook = notebooks.get(request.params(":id"));
            if (notebook != null) {
                return "Title: " + notebook.getTitle() + ", Author: " + notebook.getAuthor();
            } else {
                response.status(404); // 404 Not found
                return "Notebooks not found";
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
                String newAuthor = request.queryParams("author");
                String newTitle = request.queryParams("title");
                if (newAuthor != null) {
                    notebook.setAuthor(newAuthor);
                }
                if (newTitle != null) {
                    notebook.setTitle(newTitle);
                }
                return "Notebooks with id '" + id + "' updated";
            } else {
                response.status(404); // 404 Not found
                return "Notebooks not found";
            }
        });

        // Deletes the notebook resource for the provided id
        post("/notebooks/:id/delete", (request, response) -> {
            String id = request.params(":id");
            Notebook notebook = notebooks.remove(id);
            if (notebook != null) {
                return "Notebooks with id '" + id + "' deleted";
            } else {
                response.status(404); // 404 Not found
                return "Notebooks not found";
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
    }

    public static class Notebook {

        public String author, title;

        public Notebook(String author, String title) {
            this.author = author;
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
