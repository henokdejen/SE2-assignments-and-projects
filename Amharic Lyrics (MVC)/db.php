<?php
        // echo gettype($DBC);
        function connectToDb(){
            return new mysqli("localhost","id4090284_botmukera","BOTmukera","id4090284_botdb");
        }
        
        function getAllArtists(){
            $DBC = connectToDb();
            $Artists = $DBC->query("select * from Artists");
            return $Artists;
        }
        
        function getAllTracksFor($artistId){
            $DBC = connectToDb();
            $Tracks = $DBC->query("select * from Tracks where artistId=$artistId");
            return $Tracks;
        }
        
        function getLyricsFor($trackTitle){
            $DBC = connectToDb();
            $lyrics = $DBC->query("select * from Tracks where Title='$trackTitle'");
            return $lyrics;
        }
        
        function getAllTrackForBot($artistname){
            $DBC = connectToDb();
            $artist = $DBC->query("select * from Artists where FullName = '$artistname'");
            $artistId = ($artist->fetch_assoc())["ID"];
            return getAllTracksFor($artistId);
        }

?>