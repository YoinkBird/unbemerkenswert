Cloud Engineering - Programming Assignment
# N-e-v-e-r-n-o-t-e API
Design and implement an “Evernote-like” RESTful web service that can be used to save, retrieve and update organized notes.

This assignment must be completed in Java and use Maven, but you may use the frameworks and libraries of your choice.

The API should support the creation and deletion of “notebooks”.
A notebook is simply a collection of notes.
The ability to retrieve a list of notebooks along with the number of notes in each one should be possible.

The API should also support CRUD operations for notes, using appropriate REST resources
and operations to create notes in a given notebook, update a given note, and delete notes.

Creating notes requires submitting a JSON request body with “title”, “body”, and “tags”
attributes. “Title” and “body” can be strings, but tags should be an array of strings.
```
curl -s -H Content-Type:application/json \
 -d '{"title": "<title>" \
 "body": "<body>" \
 "tags": "[<tags>] \
 }' \
 localhost:8080/create
```

When a note is first created, an “id”, “created” timestamp and “lastModified” timestamp should
be set to the note. When a note is updated, the “lastModified” timestamp should be updated.
```
#expected:
{"id":<n>,"created":"yyyy-MM-ddTHH:mm:ss","lastModified":null,"title":"Samwise Gamgee","body":"gardener","tags":["tag1","tag2"]}
```

Retrieving a notebook should provide a list of note “metadata” (i.e. “id”, “title”, “tags”, “created”
time and ”lastModified” time for each note). You should be able to specify a tag when retrieving
a notebook and receive a filtered list of notes that contain that tag string.
```
```

When retrieving a specific note, provide a response body with “id”, “title”, “body”, “tags” (as an
array of strings), “created” and “lastModified”.
```
```

When a note is deleted, it can be considered permanent and no record of it needs to be kept.
```
```


# Additional:
* Unit tests must be included in your submission!
* You can add additional fields and functionality at your discretion
* No persistence is required. Data can be stored in memory and reset on subsequent runs
(however, you can implement persistence if you choose to do so)
* No support is needed to allow moving notes from one notebook to another
* Searching with a tag only applies to a given notebook. The API does not need to support
searching tags across multiple notebooks
* No UI elements are required
* No authentication/authorization is required

* Be prepared to discuss technologies, methodologies, best practices and your overall
approach used for this assignment, as well as how your implementation might change if
this was deployed as a stateless, scalable web service
* Please use Github or Bitbucket to host your repository and write a relevant README.md
file to document your assignment
* Include a Dockerfile that can be used to build a container of your application.

