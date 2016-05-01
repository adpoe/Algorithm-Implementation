#!/bin/bash
#compile, so we can use changes
javac mylzw.java
#mkdirs we may need, if they don't exist
mkdir -p compressed_$1
mkdir -p expanded_$1
echo "BEGINNING COMPRESSION"
# compress all files
#java MyLZW - $1 < example_files/all.tar > compressed_$1/all_tar.lzw
echo "--> compressed all.tar"
#java MyLZW - $1 < example_files/assig2.doc > compressed_$1/assig2.lzw
echo "--> compressed assig2.doc"
#java MyLZW - $1 < example_files/bmps.tar > compressed_$1/bmps.lzw
echo "--> compressed bmps.tar"
#java MyLZW - $1 < example_files/code.txt > compressed_$1/code.lzw
echo "--> compressed code.txt"
#java MyLZW - $1 < example_files/code2.txt > compressed_$1/code2.lzw
echo "--> compressed code2.txt"
#java MyLZW - $1 < example_files/edit.exe > compressed_$1/edit.exe.lzw
echo "--> compressed edit.exe"
java MyLZW - $1 < example_files/frosty.jpg > compressed_$1/frosty.lzw
echo "--> compressed frosty.jpg"
#java MyLZW - $1 < example_files/gone_fishing.bmp > compressed_$1/gone_fishing.lzw
echo "--> compressed gone_fishing.bmp"
#java MyLZW - $1 < example_files/large.txt > compressed_$1/large.lzw
#echo "--> compressed large.txt"
#java MyLZW - $1 < example_files/Lego-big.gif > compressed_$1/Lego-big.lzw
echo "--> compressed Lego-big.gif"
#java MyLZW - $1 < example_files/medium.txt > compressed_$1/medium.lzw
echo "--> compressed medium.txt"
#java MyLZW - $1 < example_files/texts.tar > compressed_$1/texts.lzw
echo "--> compressed texts.tar"
#java MyLZW - $1 < example_files/wacky.bmp > compressed_$1/bmp.lzw
echo "--> compressed wacky.bmp"
#java MyLZW - $1 < example_files/winnt256.bmp > compressed_$1/winnt256.lzw
echo "--> compressed winnt256.bmp"
echo "COMPRESSION COMPLETE"
echo ""
echo "BEGINNING EXPANSION"
# expand all files
#java MyLZW + < compressed_$1/all_tar.lzw >  expanded_$1/all.tar 
echo "--> expanded all.tar"
#java MyLZW + < compressed_$1/assig2.lzw > expanded_$1/assig2.doc 
echo "--> expanded assig2.doc"
#java MyLZW + < compressed_$1/bmps.lzw > expanded_$1/bmps.tar 
echo "--> expanded bmps.tar"
#java MyLZW + < compressed_$1/code.lzw > expanded_$1/code.txt 
echo "--> expanded code.txt"
#java MyLZW + < compressed_$1/code2.lzw > expanded_$1/code2.txt
echo "--> expanded code2.txt"
#java MyLZW + < compressed_$1/edit.exe.lzw > expanded_$1/edit.exe
echo "--> expanded edit.exe"
java MyLZW + < compressed_$1/frosty.lzw > expanded_$1/frosty.jpg  
echo "--> expanded frosty.jpg"
#java MyLZW + < compressed_$1/gone_fishing.lzw > expanded_$1/gone_fishing.bmp 
echo "--> expanded gone_fishing.bmp"
#java MyLZW + < compressed_$1/large.lzw > expanded_$1/large.txt  
echo "--> expanded large.txt"
#java MyLZW + < compressed_$1/Lego-big.lzw > expanded_$1/Lego-big.gif  
echo "--> expanded Lego-big.gif"
#java MyLZW + < compressed_$1/medium.txt > expanded_$1/medium.lzw 
echo "--> expanded medium.txt"
#java MyLZW + < compressed_$1/texts.lzw > expanded_$1/texts.tar 
echo "--> expanded texts.tar"
#java MyLZW + < compressed_$1/bmp.lzw > expanded_$1/wacky.bmp  
echo "--> expanded wacky.bmp"
#java MyLZW + < compressed_$1/winnt256.lzw > expanded_$1/winnt256.bmp 
echo "--> compressed winnt256.bmp"
echo "EXPANSION COMPLETE"
echo ""


