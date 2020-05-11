
## Akka-Http

- Curl command to demo how Akka-Http accepts Post request and return response as Json.

```
curl --location --request POST 'http://localhost:8080/echo' \
--header 'Content-Type: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
	
	"num": 10,
	"sentence":"heello post scala"
}'
```

