#!/usr/bin/env bash
#
# Sample usage:
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
# When not in Docker
#: ${HOST=localhost}
#: ${PORT=7000}

# When in Docker
: ${HOST=localhost}
: ${PORT=8080}

function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

function testUrl() {
  url=$@
  if curl $url -ks -f -o /dev/null
  then
        echo "Ok"
        return 0
    else
        echo -n "not yet"
        return 1
  fi;
}

function setupTestdata() {

  body=\
'{"bookId":1,"title":"t1","author":"a1","artist":"art1", "subject":"s1", "publisher":"p1", "type":"t1", "app":"a1", "status":"stat1", accountings":[
        {"accountId":1,"paidPrice":"9.99","savings":"5.00","method":"method 1"},
    ], "portals":[
        {"portalId":1,"link":"onlinebooks.com/","userName":"reader1"}
    ]}'
    recreateComposite 1 "$body"

    body=\
'{"bookId":113,"title":"t113","author":"a113","artist":"art113", "subject":"s113", "publisher":"p113", "type":"t113", "app":"a113", "status":"stat113", "portals":[
    {"portalId":113,"link":"onlinebooks.com/113","userName":"reader113"}
]}'
    recreateComposite 113 "$body"

    body=\
'{"bookId":1,"title":"t213","author":"a213","artist":"art213", "subject":"s213", "publisher":"p213", "type":"t213", "app":"a213", "status":"stat213", "accountings":[
       {"accountId":213,"listedPrice":19.99,"paidPrice":15.99,"savings":10.00,"method":"m213"}
]}'
    recreateComposite 213 "$body"
}

function recreateComposite() {
    local bookId=$1
    local composite=$2

    assertCurl 200 "curl -X DELETE http://$HOST:$PORT/book-composite/${bookId} -s"
    curl -X POST http://$HOST:$PORT/book-composite -H "Content-Type:
    application/json" --data "$composite"
}

function waitForService() {
  url=$@
  echo -n "Wait for: $url... "
  n=0
  until testUrl $url
  do
    n=$((n + 1))
    if [[ $n == 100 ]]
    then
      echo " Give up"
      exit 1
      else
        sleep 6
        echo -n ", retry #$n "
    fi
  done
}

set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

if [[ $@ == *"start"* ]]
then
  echo "Restarting the test environment..."
  echo "$ docker-compose down"
  docker-compose down
  echo "$ docker compose up -d"
  docker-compose up -d
fi

#waitForService http://$HOST:${PORT}/book-composite/1

waitForService curl -X DELETE http://$HOST:$PORT/book-composite/13

setupTestdata

# Verify that a normal request works, expect one accounting and one portal
assertCurl 200 "curl http://$HOST:$PORT/book-composite/1 -s"
assertEqual 1 $(echo $RESPONSE | jq .bookId)
assertEqual 1 $(echo $RESPONSE | jq ".accountings | length")
assertEqual 1 $(echo $RESPONSE | jq ".portals | length")

# Verify that a 404 (Not Found) error is returned for a non existing bookId (13)
assertCurl 404 "curl http://$HOST:$PORT/book-composite/13 -s"

# Verify that no accounting is returned for bookId 113
assertCurl 200 "curl http://$HOST:$PORT/book-composite/113 -s"
assertEqual 113 $(echo $RESPONSE | jq .bookId)
assertEqual 0 $(echo $RESPONSE | jq ".accountings | length")
assertEqual 1 $(echo $RESPONSE | jq ".portals | length")

# Verify that no portal is returned for bookId 213
assertCurl 200 "curl http://$HOST:$PORT/book-composite/213 -s"
assertEqual 213 $(echo $RESPONSE | jq .bookId)
assertEqual 1 $(echo $RESPONSE | jq ".accountings| length")
assertEqual 0 $(echo $RESPONSE | jq ".portals | length")

# Verify that a 422 (Unprocessable Entity) error is returned for a productId that is out of range (-1)
assertCurl 422 "curl http://$HOST:$PORT/book-composite/-1 -s"
assertEqual "\"Invalid bookId: -1\"" "$(echo $RESPONSE | jq .message)"

# Verify that a 400 (Bad Request) error error is returned for a productId that is not a number, i.e. invalid format
assertCurl 400 "curl http://$HOST:$PORT/book-composite/invalidBookId -s"
assertEqual "\"Type mismatch.\"" "$(echo $RESPONSE | jq .message)"

# Verify that a 406 (Not Acceptable) error wrong genre is returned for a bookId that is not recognized, i.e. wrong genre
assertCurl 406 "curl http://$HOST:$PORT/book-composite/wrongGenre -s"
assertEqual "\"Genre not recognized.\"" "$(echo $RESPONSE | jq .message)"

if [[ $@ == *"stop"* ]]
then
    echo "We are done, stopping the test environment..."
    echo "$ docker-compose down"
    docker-compose down
fi






