<?php 

chdir('C:\Octave\Octave-4.0.0');
$output = shell_exec('octave Test.m');
$txt_output = file_get_contents('file.txt');
echo $txt_output;

?>