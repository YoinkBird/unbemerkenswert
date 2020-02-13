curl -i -H "Content-Type:application/json" -d '{"firstName": "Frodo", "lastName": "Baggins"}' localhost:8081/people
# HTTP/1.1 201 
# Vary: Origin
# Vary: Access-Control-Request-Method
# Vary: Access-Control-Request-Headers
# Location: http://localhost:8081/people/1
# Content-Type: application/hal+json
# Transfer-Encoding: chunked
# Date: Thu, 13 Feb 2020 15:35:05 GMT
# 
# {
#   "firstName" : "Frodo",
#   "lastName" : "Baggins",
#   "_links" : {
#     "self" : {
#       "href" : "http://localhost:8081/people/1"
#     },
#     "person" : {
#       "href" : "http://localhost:8081/people/1"
#     }
#   }


curl -i localhost:8081/people/search/findByLastName?name=Baggins
# HTTP/1.1 200 
# Vary: Origin
# Vary: Access-Control-Request-Method
# Vary: Access-Control-Request-Headers
# Content-Type: application/hal+json
# Transfer-Encoding: chunked
# Date: Thu, 13 Feb 2020 15:35:58 GMT
# 
# {
#   "_embedded" : {
#     "people" : [ {
#       "firstName" : "Frodo",
#       "lastName" : "Baggins",
#       "_links" : {
#         "self" : {
#           "href" : "http://localhost:8081/people/1"
#         },
#         "person" : {
#           "href" : "http://localhost:8081/people/1"
#         }
#       }
#     } ]
#   },
#   "_links" : {
#     "self" : {
#       "href" : "http://localhost:8081/people/search/findByLastName?name=Baggins"
#     }
#   }
