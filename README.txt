James Plecher
s3898959

***INSTRUCTIONS***
To run program, "use com.idk.demo.Main" and run
If "mydatabase.db" is not running properly to start, run "com/idk/demo/Models/DatabasesInteract/ClearTables.java"
It will clear any existing tables and recreate them with no data.
If you want extra data, run the "com/idk/demo/Models/DatabasesInteract/InsertRow.java"


**LOGIN PAGE**
There are two default usernames and passwords included in database:
    Manager Login:
        username = "a"
        password = "a"
    Staff Login:
        username = "b"
        password = "b"

**HOME PAGE**
After logging in, on the homepage, the Events Requests and Venues can be imported from a csv, filling the ListViews.
Venues has adding, filtering and search capabilities
Events can be confirmed and will be transferred to the "Confirmed Orders" list

Venues, Events and Orders can all be DOUBLE CLICKED on, displaying information about each
I never implemented availabilities or auto-match capabilities due to being lazy and running out of time and the java.util.Date and java.sql.Date formats setting me back 3 hours due to debugging :(

**PROFILE PAGE**
Not much here
Username and Password can be updated
No first or last name implementation due to not needing it.
Username has validation to always be unique

**MANAGER PAGE**
Can ADD, DELETE, PROMOTE accounts.
I didn't understand the "manager 909" special key, so I did not implement
Can import and export the serialized master and transactional data
Has the two data visualisations
Has the stats summary at the bottom

***Dependencies***
I don't think any dependencies will need to be downloaded, but if they do, I guess I'm not getting an amazing mark

***GitHub***
Repo Link:
https://github.com/JamesVichie/assignment.git

I realise now I accidently used a personal Github account. I hope this is ok. I am using my desktop since its an online class and it was logged into a very old account.
