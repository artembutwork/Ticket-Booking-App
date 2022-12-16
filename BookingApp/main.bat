echo User gets sorted movies list on selected day and time
curl -G "http://localhost:8080/api/screenings" --data-urlencode "periodStartTime=2022-12-31 10:00" --data-urlencode "periodEndTime=2022-12-31 21:00"

echo User gets information regarding screening room and available seats for particular screening
curl -X GET "http://localhost:8080/api/screening" -H "Content-Type: application/json" -d "{ \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00\" }"

echo Reservation with chosen seats creates on the user name. User gets back message with the total amount to pay and reservation expiration time
curl -X POST "http://localhost:8080/api/reservations/create" -H "Accept-Language: pl" -H "Content-Type: application/json" -d "{ \"firstName\": \"Artem\", \"lastName\": \"But\", \"screeningDetails\" : { \"movieTitle\": \"Matrix\", \"screeningStartTime\": \"2022-12-31 11:00\" }, \"seats\": [ { \"rowNumber\": 1, \"seatNumber\": 2, \"ticketType\": \"adult\"}, { \"rowNumber\": 2, \"seatNumber\": 3, \"ticketType\": \"child\"}] }"
pause