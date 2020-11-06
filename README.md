# Redbook - web client for reddit

**Live demo:** http://salty-shelf-51621.herokuapp.com

Redbook is a web client application developed with Java 11 and Spring framework that also includes custom section for 
non-reddit activities. It utilizes the reddit APIs to function.

### Features
- Browse reddit anonymously
- Browse reddit using your account
- Vote posts
- Bookmark posts
- See subscribed subreddits
- And more...

### Features for non-reddit section

- Authenticate with a local account
- Submit posts
- Assign roles
- And more...

As this app is constantly updating, you can track the progress from this [Trello](https://trello.com/b/aggbZ4hA/redbook-project) 
board. The database used for the non-reddit section is PostgreSQL. 

### Compile and Run

To compile and run it on your machine, assign your client id, secret, and user agent to *reddit.properties* file and 
classes in *security and rest* packages. After this, run it from IDE or with `mvn spring-boot:run` command.