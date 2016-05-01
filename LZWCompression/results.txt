Anthony Poerio
adp59@pitt.edu
CS1501 - Assignment 02: LZW Compression
Spring 2016

RESULTS from testing different edits to LZW compression, and GZip.

| FILE (size)              | LZW (Original)         | LZW Variable Width    | LZW Reset Mode        | LZW Monitor Mode      | G-Zip                  |
|--------------------------|------------------------|-----------------------|-----------------------|-----------------------|------------------------|
| all.tar - 3 MB           | 1.8 MB  (ratio: 1.666) | 1.8 MB (ratio: 1.666) | 1.2 MB (ratio: 2.500) | 2.1 MB (ratio: 1.428) | 969 kb  (ratio: 3.096) |
| assig2.doc - 87 kb       | 75 kb   (ratio: 1.160) | 40 kb (ratio: 2.175)  | 40 kb (ratio: 2.175)  | 40 kb (ratio: 2.175)  | 23 kb (ratio: 3.783)   |
| bmps.tar - 1.1 MB        | 925 kb (ratio: 1.189)  | 81 kb (ratio: 13.580) | 81 kb (ratio: 13.580) | 81 kb (ratio: 13.580) | 64 kb  (ratio: 17.188) |
| code.txt - 72 kb         | 31 kb (ratio: 2.322)   | 25 kb (ratio: 2.880)  | 25 kb (ratio: 2.880)  | 25 kb (ratio: 2.880)  | 15 kb (ratio:  4.800)  |
| code2.txt - 58 kb        | 24 kb (ratio: 2.416)   | 21 kb (ratio:  2.762) | 21 kb (ratio: 2.762)  | 21 kb (ratio: 2.762)  | 13 kb  (ratio: 4.462)  |
| edit.exe - 236 kb        | 251 kb  (ratio: 0.940) | 156 kb (ratio: 1.512) | 152 kb (ratio: 1.552) | 156 kb (ratio: 1.512) | 127 kb (ratio: 1.858)  |
| frosty.jpg - 127 kb      | 177 kb  (ratio: 0.718) | 164 kb (ratio: 0.774) | 171 kb (ratio: 0.743) | 164 kb (ratio: 0.774) | 127 kb (1.000)         |
| gone_fishing.bmp - 17 kb | 9 kb  (ratio: 1.888)   | 9 kb (ratio: 1.888)   | 9 kb (ratio: 1.888)   | 9 kb (ratio: 1.888)   | 9 kb (ratio: 1.888)    |
| large.txt - 1.2 MB       | 605 kb (ratio: 1.983)  | 502 kb (ratio: 2.390) | 528 kb (ratio: 2.272) | 502 kb (ratio: 2.390) | 493 kb (ratio: 2.434)  |
| Lego-big.gif - 93 kb     | 129 kb (ratio: 0.721)  | 122 kb (ratio: 0.762) | 122 kb (ratio: 0.762) | 122 kb (ratio: 0.762) | 92 kb (ratio: 1.011)   |
| medium.txt - 25 kb       | 13 kb (ratio: 1.923)   | 13 kb (ratio: 1.923)  | 13 kb (ratio: 1.923)  | 13 kb (ratio: 1.923)  | 11 kb (ratio: 2.273)   |
| texts.tar - 1.4 MB       | 1 MB  (ratio: 1.4)     | 598 kb (ratio: 2.341) | 590 kb (ratio: 2.372) | 598 kb (ratio: 2.341) | 533 kb (ratio: 2.627)  |
| wacky.bmp - 922 kb       | 4 kb  (ratio: 230.5)   | 4 kb (ratio: 230.5)   | 4 kb (ratio: 230.5)   | 4 kb (ratio: 230.5)   | 3 kb (ratio: 307.333)  |
| winnt256.bmp - 157 kb    | 159 kb (ratio: 0.9874) | 63 kb (ratio: 2.492)  | 63  kb (ratio: 2.492) | 63 kb (ratio: 2.492)  | 50 kb (ratio: 3.140)   |
