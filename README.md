## Class for finding Longest Common Subsequence

### Description
- Functions using dynamic programming 
- enter a biological sequence 
- if the sequence is not valid, then an error message will be printed such as ```
This is not a biological Sequence ```
- if enter 'exit', then the program will be terminated

### Functions
- <code>LCSFinder.getLCS(String x, String y)</code> : find the Longest Common Subsequence of x and y 
- <code>LCSFinder.getLPS(String x)</code> : find the Longest Palindrome Subsequence of x
- <code>LCSFinder.getHairpin(String x)</code> : find the Longest Reverse Compliment Subsequence of x

### Algorithm of getHairpin
1. converting each character of a given string by 4 cases ( 'A' to 'T', 'T' to 'A', 'G' to 'C', 'C' to 'G' )

2. get reverse of the string on line 1

3. get the longest common subsequence for the given string and the string on line 2

4. return reverse of the string on line 3