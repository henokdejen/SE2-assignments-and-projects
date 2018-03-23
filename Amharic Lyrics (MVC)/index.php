<?php
    $website = "https://api.telegram.org/bot585700468:AAETx53QYHFwyGQvzFqUB6R_N1DbyWpLtfQ";
    $GlobalCommands = "/chooseartist";
    require("db.php");
    /*
        For the time being artist names are stored with # prefix, tracks with *prefix
    */



    function getReceivedMessage($Received){
        return $Received->message->text;
        // return "ache";
    }

    function getChatId($Received){
        return $Received->message->from->id;
    }

    function prepareResponse($Received){
        $GlobalCommands = "/chooseartist";        
        $receivedText = getReceivedMessage($Received);
        $responseText = "Invalid Input";
        // checks the text contains name of artist, track or is global command based on its first character
        // if it strats with '#' it is artist name
        // if it starts with '*' it is a track name
        if ($receivedText[0] == "/"){
       switch($receivedText[strlen($receivedText)-1]){
            default:
                if ($receivedText == $GlobalCommands){
                    $artists =  getAllArtists();
                    $responseText = "";
                    while ($artist = $artists->fetch_assoc()){
                        $responseText = $responseText."/".$artist["FullName"]."0\n";
                    }
                    // $responseText = "list of artists available"; // '#' should preced artist's name
                }
                break;
            case "0":
                // specific artist is choosen, #
                $choosenArtist = substr($receivedText, 1, -1);
                $responseText = "No track";
                $tracks = getAllTrackForBot($choosenArtist);
                if (sizeof($tracks) > 0) {$responseText = "";}
                while ($track = $tracks->fetch_assoc()){
                    $responseText = $responseText."/".$track["Title"]."1\n";    
                }
                break;
            case "1":
                // a specific track is choosen, *
                $availableLyrics = getLyricsFor(substr($receivedText, 1, -1)); // to be replaced with database result
                if (sizeof($availableLyrics) > 0){
                    $lyr = $availableLyrics->fetch_assoc();
                    $responseText = substr($receivedText, 1, -1)."\n". $lyr["lyrics"];
                }
                break;
                
        }            
        }
        
 
        return urlencode($responseText);
    }

    function sendMessage($chat_id, $text){
        // file_get_contents($website."/sendMessage?chat_id=318267251&text=working");
        file_get_contents("https://api.telegram.org/bot472316626:AAGOqb8NJD88OIwPI6wZS-WWwRZ-alyLl8c/sendMessage?chat_id=$chat_id&text=$text");
        // echo "chat_id : ".$chat_id."\n text : ".$text;
    }

    function handleRequest(){
        $Received = file_get_contents("php://input"); // extracts body of the coming request
        $Received = json_decode($Received);  // parsing the body to object

        $resText = prepareResponse($Received);
        $chat_id = getChatId($Received);

        sendMessage($chat_id, $resText);
    }

    handleRequest();
?>