TestDatabasePath='C:\wamp\www\uploads\';
for i=1:2
    str=strcat(TestDatabasePath,int2str(i),'.jpg');
    i=imread(str);
    imresize(i,[28,28]);
    imwrite(i,str);
end