echo Invalid time format for first request
curl -G "http://localhost:8080/api/screenings" --data-urlencode "periodStartTime=2022-12-31 10:00hello" --data-urlencode "periodEndTime=2022-12-31 21:00"
echo Period end time should be after start time
curl -G "http://localhost:8080/api/screenings" --data-urlencode "periodStartTime=2022-12-31 22:00" --data-urlencode "periodEndTime=2022-12-31 21:00"

echo Invalid screening data for second request
curl -X GET "http://localhost:8080/api/screening" -H "Accept-Language: pl" -H "Content-Type: application/json" -d "{ \"movieTitle\": \"\", \"screeningStartTime\": \"2022-12-31 11:00\" }"
echo Invalid time format for second request
curl -X GET "http://localhost:8080/api/screening" -H "Accept-Language: pl" -H "Content-Type: application/json" -d "{ \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00hello\" }"
echo Screening doesn't exist for second request
curl -X GET "http://localhost:8080/api/screening" -H "Accept-Language: pl" -H "Content-Type: application/json" -d "{ \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 10:00\" }"

echo Name should be at least three characters long
curl -X POST "http://localhost:8080/api/reservations/create" -H "Accept-Language: pl" -H "Content-Type: application/json" -d "{ \"firstName\": \"Ar\", \"lastName\": \"But\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 2, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"
echo Name should start with a capital letter
curl -X POST "http://localhost:8080/api/reservations/create" -H "Accept-Language: pl" -H "Content-Type: application/json" -d "{ \"firstName\": \"artem\", \"lastName\": \"But\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 2, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"
echo Surname should be at least three characters long
curl -X POST "http://localhost:8080/api/reservations/create" -H "Accept-Language: pl" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"Bu\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 2, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"
echo Surname should start with a capital letter
curl -X POST "http://localhost:8080/api/reservations/create" -H "Accept-Language: pl" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"but\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 2, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"
echo Second part of surname should start with a capital letter
curl -X POST "http://localhost:8080/api/reservations/create" -H "Accept-Language: pl" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"But-but\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 2, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"

echo Reservation should contain at least one seat
curl -X POST "http://localhost:8080/api/reservations/create" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"But\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00\" }, \"seats\": [] }"
echo Invalid screening data for third request
curl -X POST "http://localhost:8080/api/reservations/create" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"But\", \"screeningDetails\" : null, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 2, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"
echo Invalid movie title
curl -X POST "http://localhost:8080/api/reservations/create" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"But\", \"screeningDetails\" : { \"movieTitle\": \"\", \"screeningStartTime\": \"2022-12-31 11:00\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 2, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"
echo Invalid time format for third request
curl -X POST "http://localhost:8080/api/reservations/create" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"But\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00hello\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 2, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"
echo Screening doesn't exist for third request
curl -X POST "http://localhost:8080/api/reservations/create" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"But\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 10:00\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 2, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"

echo Requested seat has already been reserved
curl -X POST "http://localhost:8080/api/reservations/create" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"But\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 1, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"
echo Can't leave one seat in the row between two already reserved seats
curl -X POST "http://localhost:8080/api/reservations/create" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"But\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 3, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"

echo To test seats can be booked at latest 15 minutes before the screening begins exception, you should change test data or wait until New Year:)
pause