char_to_int '0' = 0 --converts the the char actual integer
char_to_int '1' = 1
char_to_int '2' = 2
char_to_int '3' = 3
char_to_int '4' = 4
char_to_int '5' = 5
char_to_int '6' = 6
char_to_int '7' = 7
char_to_int '8' = 8
char_to_int '9' = 9

int_to_char 0 = '0' --converts the integer to a Char
int_to_char 1 = '1'
int_to_char 2 = '2'
int_to_char 3 = '3'
int_to_char 4 = '4'
int_to_char 5 = '5'
int_to_char 6 = '6'
int_to_char 7 = '7'
int_to_char 8 = '8'
int_to_char 9 = '9'

isInt x = if x == '0' || --complex encode and checks if the Char is an Integer
             x == '1' ||
             x == '2' ||
             x == '3' ||
             x == '4' ||
             x == '5' ||
             x == '6' ||
             x == '7' ||
             x == '8' ||
             x == '9' then
             True else 
             False

--repeats a Char given a number of times. e.g. repeat_char 'a' 5 -> aaaaa
repeat_char _ 0 = ""
repeat_char c i = c : repeat_char c (i-1)

--decodes the run length encoding like so: decode "o1w9!1"
decode "" = ""
decode (x:y:xs) = repeat_char x (char_to_int y) ++ decode xs

--returns the legnth of the list provided
len [] = 0
len (_:xs) = 1 + len xs

-- being provided with a Char and the [Char] it deletes all of the first items of
-- the [Char] that are equal to the Char e.g aaaabbbbbttttt -> bbbbbttttt
drop_char _ "" = ""
drop_char c (x:xs) = (if c == x then drop_char c xs else x:xs)

-- the encode function works like so encode "aaaaggg" -> "a4g3"
encode "" = ""
encode (x:xs) = x : "" ++  int_to_char (len(x:xs)-len(drop_char x (x:xs))) : encode (drop_char x (x:xs))

--COMPLEX ENCODE --
--takes num 321 and checks if it is 1,2,3,4,5,6,7,8,9,0 if false then 321 div 10 so 32
--divide_int 0 = "0"
--divide_int x = if isInt x then "" : (int_to_char x) else divide_int (x `div` 10) ++ ""

--unfinished but this was supposed to break up numbers greater than 1 digit using the divide_int function
--I would then just swap the integers to chars using the int_to_char function and the Char to a [Char]
--complex_int_to_char 0 = ""
--complex_int_to_char num = 

--This would hold all the functions for the complex encoding and you would use this function to encode the string
--e.g: aaaaaaaaaaajjjjjjjjjjjjtr -> a10j12tr

--complex_encode "" = ""
--complex_encode (x:xs) = x : take_int(len(x:xs)-len(drop_char x (x:xs))) ++ encode (drop_char x (x:xs))

--COMPLEX DECODE --

mult x y = x*(10^y) --used to convert the encoded [Char] frequency to string 
                    --e.g 235 = (2*(10^2))+(3*(10^1))+(2*(10^0))

-- takes the integer from the front of a string to decode the string
take_int "" = ""
take_int (x:xs) = if isInt x then x : take_int (xs) ++ "" else ""

--This was just supposed to put the chars to ints using the mult function
--charl_to_int "" = 0
--charl_to_int (x:xs) = (mult((char_to_int x) ((len (x:xs))-1))) + (charl_to_int (xs))

--complex_decode "" = ""
--complex_decode(x:xs) = 