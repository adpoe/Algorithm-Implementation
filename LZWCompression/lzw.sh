#!/bin/bash
#compile, so we can use changes
javac lzw.java
#mkdirs we may need, if they don't exist
mkdir -p compressed_lzw
mkdir -p expanded_lzw
echo "BEGINNING COMPRESSION"
# compress all files
java LZW - < example_files/all.tar > compressed_lzw/all_tar.lzw
echo "--> compressed all.tar"
java LZW - < example_files/assig2.doc > compressed_lzw/assig2.lzw
echo "--> compressed assig2.doc"
java LZW - < example_files/bmps.tar > compressed_lzw/bmps.lzw
echo "--> compressed bmps.tar"
java LZW - < example_files/code.txt > compressed_lzw/code.lzw
echo "--> compressed code.txt"
java LZW - < example_files/code2.txt > compressed_lzw/code2.lzw
echo "--> compressed code2.txt"
java LZW - < example_files/edit.exe > compressed_lzw/edit.exe.lzw
echo "--> compressed edit.exe"
java LZW - < example_files/frosty.jpg > compressed_lzw/frosty.lzw
echo "--> compressed frosty.jpg"
java LZW - < example_files/gone_fishing.bmp > compressed_lzw/gone_fishing.lzw
echo "--> compressed gone_fishing.bmp"
java LZW - < example_files/large.txt > compressed_lzw/large.lzw
echo "--> compressed large.txt"
java LZW - < example_files/Lego-big.gif > compressed_lzw/Lego-big.lzw
echo "--> compressed Lego-big.gif"
java LZW - < example_files/medium.txt > compressed_lzw/medium.lzw
echo "--> compressed medium.txt"
java LZW - < example_files/texts.tar > compressed_lzw/texts.lzw
echo "--> compressed texts.tar"
java LZW - < example_files/wacky.bmp > compressed_lzw/bmp.lzw
echo "--> compressed wacky.bmp"
java LZW - < example_files/winnt256.bmp > compressed_lzw/winnt256.lzw
echo "--> compressed winnt256.bmp"
echo "COMPRESSION COMPLETE"
echo ""
echo "BEGINNING EXPANSION"
# expand all files
java LZW + < compressed_lzw/all_tar.lzw >  expanded_lzw/all.tar 
echo "--> expanded all.tar"
java LZW + < compressed_lzw/assig2.lzw > expanded_lzw/assig2.doc 
echo "--> expanded assig2.doc"
java LZW + < compressed_lzw/bmps.lzw > expanded_lzw/bmps.tar 
echo "--> expanded bmps.tar"
java LZW + < compressed_lzw/code.lzw > expanded_lzw/code.txt 
echo "--> expanded code.txt"
java LZW + < compressed_lzw/code2.lzw > expanded_lzw/code2.txt
echo "--> expanded code2.txt"
java LZW + < compressed_lzw/edit.exe.lzw > expanded_lzw/edit.exe
echo "--> expanded edit.exe"
java LZW + < compressed_lzw/frosty.lzw > expanded_lzw/frosty.jpg  
echo "--> expanded frosty.jpg"
java LZW + < compressed_lzw/gone_fishing.lzw > expanded_lzw/gone_fishing.bmp 
echo "--> expanded gone_fishing.bmp"
java LZW + < compressed_lzw/large.lzw > expanded_lzw/large.txt  
echo "--> expanded large.txt"
java LZW + < compressed_lzw/Lego-big.lzw > expanded_lzw/Lego-big.gif  
echo "--> expanded Lego-big.gif"
java LZW + < compressed_lzw/medium.lzw> expanded_lzw/medium.txt
echo "--> expanded medium.txt"
java LZW + < compressed_lzw/texts.lzw > expanded_lzw/texts.tar 
echo "--> expanded texts.tar"
java LZW + < compressed_lzw/bmp.lzw > expanded_lzw/wacky.bmp  
echo "--> expanded wacky.bmp"
java LZW + < compressed_lzw/winnt256.lzw > expanded_lzw/winnt256.bmp 
echo "--> compressed winnt256.bmp"
echo "EXPANSION COMPLETE"
echo ""


