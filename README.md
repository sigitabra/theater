# Theater reservations

Application aims to manage seats availability for theater theaterPlays.

For this any user with "COORDINATOR" or "ADMIN" role should be allowed to create theaterPlays and schedule time for them
by defining the limit of available seats and other relevant information (i.e. address, date and time).

Any public person should be able to register up to 3 seats per reservation.

Once theaterPlay is completed any user with "COORDINATOR" or "ADMIN"  should be able to delete scheduled theaterPlay
with all related reservations, to manage GDPR concerns.

##  Use cases

* Get theater play details by ID (public endpoint for all users)
* Get list of all theater plays (public endpoint for all users)
* Add new theater play (available only with "COORDINATOR" or "ADMIN" roles)
* Delete theater play by ID (can be done only by ADMIN user)
* Update theater play by ID (available only with "COORDINATOR" or "ADMIN" roles)
* Add new scheduled play (available only with "COORDINATOR" or "ADMIN" roles)
* Get all available scheduled plays (public endpoint for all users)
* Update scheduled play by ID (available only with "COORDINATOR" or "ADMIN" roles, only if any of the key parameters have changed)
* Delete scheduled play by ID with all related reservations (available only with "COORDINATOR" or "ADMIN" roles, then
  play is already past, no need to save user data due to GDPR)
* Get list of reservations by scheduled play ID (available only with "COORDINATOR" or "ADMIN" roles)
* Add a reservation to the reservations' list of scheduled play (public endpoint for users to register)
* Delete reservation by id and by scheduled play id (public endpoint for users to cancel personal reservation)







