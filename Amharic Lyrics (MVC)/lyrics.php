<!DOCTYPE html>

<?php
    require("./db.php");
    $website = "/lyrics.php";

    $is_artist_choosen = FALSE;
    $is_track_choosen = FALSE;

    $choosen_artist = null;
    $choosen_artist_name = null;
    $choosen_track = null;
    $track_lyrics = null;
    if (isset($_GET["artist"])){
        $is_artist_choosen =TRUE;
        $choosen_artist = $_GET["artist"];
        $choosen_artist_name = $_GET["name"];
    }

    if (isset($_GET["artist"]) & isset($_GET["track"])){
        $is_track_choosen = TRUE;
        $choosen_track = $_GET["track"];
        $track_lyrics = "fasdfadsf";
    }


    
?>

<html lang="en">

<head>
    <link rel="stylesheet" href="./bootstrap.min.css">

    <style>
        header{
  background-color: darksalmon;
  overflow: hidden;
  padding:0px 10px;
}

.mainBody{
  overflow: hidden;
}

.innerNav{
  margin-top: 10px;
  margin-bottom: 10px;
  background-color: #f1f1f1;
  padding: 10px 0px 5px 30px;
}
.navItem{
    margin-right: 10px;
}

section{
  overflow: hidden;
}

.artists{
  width: 90%;
  margin: auto;
}
.artists div{
  margin-right: 5%;
}
    </style>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>

<body>
    <div class="container">
        <header>
            <div class="row">
                <div class="col-md-5 col-xs-10">
                    <h1>Amharic Music Lyrics</h1>
                </div>

            </div>
        </header>

        <div class="mainBody">
            <div>
                <div class="innerNav">
                    <a href="<?php echo$website?>" class="navItem">Home >></a>
                    <?php
                        if ($is_artist_choosen){                                                        
                            echo  "<a class='navItem' href='$website?artist=$choosen_artist&name=$choosen_artist_name'> ".$choosen_artist_name." >></a>";
                        }

                        if($is_track_choosen){
                            echo  "<a class='navItem' href='$website?artist=$choosen_artist&name=$choosen_artist_name&track=$choosen_track'>$choosen_track </a>";   
                        }
                    ?>
                </div>
            </div>

            <section>
                
<?php
    if ($is_track_choosen){
        echo "<h3> Lyrics for $choosen_track </h3>";
        $lyrics = getLyricsFor($choosen_track);
        if ($lyr = $lyrics->fetch_assoc()){
            echo "<pre>";
            echo $lyr["lyrics"];
            echo "</pre>";
        }
    }
    elseif ($is_artist_choosen){
        echo "<h3>Available Tracks for <strong>".$choosen_artist_name." </strong></h3>";  
        echo "<div class='artists'>";
        echo "<ul>";
        
        $tracks = getAllTracksFor($choosen_artist);
        
        $i = 0;
        while ($track = $tracks->fetch_assoc()){
            if($i % 3 == 0){
                if ($i > 0){
                    echo "</ul> </div>";
                }
                echo "<div class='col-md-3 col-xs-10'> <ul>";
            }            
            echo "<li> <a href='$website?artist=$choosen_artist&name=$choosen_artist_name&track=".$track["Title"]."'>".$track["Title"]."</a></li>";
            $i++;
        }
        
        echo "</ul> </div>";
        echo "</div>";      
    }
    else{
        echo "<h3> Available Artists </h3>";
        echo "<div class='artists'>";
        // echo "<ul>";
        $artists = getAllArtists();
        $i = 0;
        while ($artist = $artists->fetch_assoc()){
            if($i % 3 == 0){
                if ($i > 0){
                    echo "</ul> </div>";
                }
                echo "<div class='col-md-3 col-xs-10'> <ul>";
            }
            echo "<li> <a href='$website?artist=".$artist["ID"]."&name=".$artist["FullName"]."'>".$artist["FullName"]."</a></li>";      
            $i++;
        }
        
        
        echo "</ul> </div>";
        echo "</div>";        
    }
?>
            </section>
        </div>


    </div>

</body>

</html>