**This is Amharic Lyrics web app based on MVC architectural design. This lets users browse artists and choose among their 
musics and the system shows them lyrics for the music. 


** This app has two portals; one is using a telegram bot and the other a simple web site. (view)
** It uses mysql database and php back end programming.
** Both telegram bot and web site derives their data from the same database. (model)
** There is separate php script to handle request coming from the bot and the web site. (controller)




The application is hosted in 00webhost
website - https://botmukera.000webhostapp.com/lyrics.php
telegram - bot name = mukera
	 - username = @yamihaendiebot

// tracks and lyrics are not provided in to database for most artist listed there.   For trial purpose
only teddy afro's data are available.

files
* lyrics.php - this is the website script
* index.php - this is telegram bot script
* db.php - this makes db update and retrieval
