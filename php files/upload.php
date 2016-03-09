<?php
	$imageDataEncoded = $_POST["base64"];
	$imageData = base64_decode($imageDataEncoded);
	$source = imagecreatefromstring($imageData);
	$imageName = $_POST["ImageName"];
	$id=$_POST["id"];
	$file=fopen("C:\Octave\Octave-4.0.0\id.txt","w");
	echo fwrite($file,$id);
	fclose($file);
	imagejpeg($source,"uploads/".$imageName,100);
?>
