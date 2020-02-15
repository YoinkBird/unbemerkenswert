# Examples

The examples use for pretty-printing the CLI util `jq`:

### Create Two Notes
```
$ curl -s -X POST localhost:8080/notebooks/1/create -H 'Content-type:application/json' -d '{"title": "java development", "body": "rest api", "tags": ["beans","runanywhere"]}'| jq
{
  "id": 1,
  "created": "2020-02-10T17:55:12.298",
  "lastModified": "2020-02-10T17:55:12.298",
  "title": "java development",
  "body": "rest api",
  "tags": [
    "beans",
    "runanywhere"
  ]
}
$ curl -s -X POST localhost:8080/notebooks/1/create -H 'Content-type:application/json' -d '{"title": "containers", "body": "use Docker!", "tags": ["whale","shipit"]}' | jq
{
  "id": 2,
  "created": "2020-02-10T17:56:05.420",
  "lastModified": "2020-02-10T17:56:05.420",
  "title": "containers",
  "body": "use Docker!",
  "tags": [
    "whale",
    "shipit"
  ]
}
```

### View Specific Note

For notebook id, any number is fine - specific notebooks not yet implemented
```
$ curl -s localhost:8080/notebooks/1/2 | jq
{
  "id": 2,
  "created": "2020-02-10T17:56:05.420",
  "lastModified": "2020-02-10T17:56:05.420",
  "title": "containers",
  "body": "use Docker!",
  "tags": [
    "whale",
    "shipit"
  ]
}
```

### View All Notes

```
$ curl -s localhost:8080/notebooks | jq
[
  {
    "id": 1,
    "created": "2020-02-10T17:55:12.298",
    "lastModified": "2020-02-10T17:55:12.298",
    "title": "java development",
    "body": "rest api",
    "tags": [
      "beans",
      "runanywhere"
    ]
  },
  {
    "id": 2,
    "created": "2020-02-10T17:56:05.420",
    "lastModified": "2020-02-10T17:56:05.420",
    "title": "containers",
    "body": "use Docker!",
    "tags": [
      "whale",
      "shipit"
    ]
  }
]
```

### Delete Note
```
curl -s localhost:8080/notebooks/1/2/delete
```
