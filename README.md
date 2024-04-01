## SQL injection example: Java with MySQL

Subtitle: how not to write a login screen

To set up: create a SQLite database, then run the following commands in the shell to create a passwords table, and add some test data.

```
create table passwords (username TEXT, login TEXT, password TEXT);

insert into passwords values ('Abby Admin', 'admin', 'kittens');
insert into passwords values ('Ben SysAdmin', 'ben', 'octopus');
insert into passwords values ('Carl ComputerTech', 'carl', 'mouse');
insert into passwords values ('Deb Developer', 'deb', 'zebra');
```


Run the app.

Try logging in with one of the valid passwords, and also try with an invalid password. You should see an appropriate success or failure message.

OK, now enter anything for the username, and this for password. 

```
whatever' or '1'='1
```

Who are you logged in as?

In addition to logging in as admin, there's more you can do with SQL injection.

Even though we can gain access to the admin account, we don't actually know the admin's password. With a little trial-and-error, we can discover the password. Try typing `admin` for the username and this for the password

    ' or password like 'a%

Put that into the SQL string, and you are asking to be logged in if our admin's password starts with `a`.
Now, try this for the password,

      ' or password like 'k%

Success! We now know our admin's password starts with `k`. An attacker can use trial and error to figure out all of the characters in the password.

    ' or password like 'ka%
    ' or password like 'kb%
    ' or password like 'kc%
    ....
    ' or password like 'ki%

If access is allowed for `' or password like 'ki%` the attacker now knows that the password begins "ki". Repeat with kia, kib, kic, kid... and then more letters until the password is known.  It might take a while, but admin access to a server or access to a whole database full of credit card numbers is worth the effort. And anyone smart enough to use SQL injection is probably smart enough to write a script that will do the hard work for them, or download one of the many SQL injection tools freely available on the internet. *Don't* try this out on any real website - hacking is illegal!

Consider this could be a login form on a website that anyone could access. This is a huge problem. 

And this is just the tip of the iceberg of SQL injection. There are many more variations which can be used to discover your database table names, columns, and all of the data within. If you don't filter user input, a malicious user can potentially read all of your data and/or destroy your database. Think about databases of usernanmes and passwords, or credit card data, or names and social security numbers; for example LinkedIn (millions of username and passwords stolen) or the Heartland data breach (millions of credit cards stolen), VTech, the Wall Street Journal, the TalkTalk ISP, many government organization, and many more...

SQL injection hall of shame (Code Curmudgeon): http://codecurmudgeon.com/wp/sql-injection-hall-of-shame/
 
OK, so how to fix? One very useful preventative measure is parameterized queries. Try replacing the SQL statement at PasswordDatabase's authenticateUser() method with a parameterized query, and then try the evil SQL again. It shouldn't work. This is why you should always user parameterized queries, and doubly always when user input is involved!

And a final reminder - it's fine to try these SQL injection techniques on this example website. But you should **never try any of this on any real website**. Hacking or trying to gain unauthorized access to a website, even for educational purposes, is illegal with severe penalties, include jail time. 
