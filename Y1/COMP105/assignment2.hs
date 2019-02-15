import Data.List
import System.IO.Unsafe

---- Data Paths
-- Remember to use double backslashes for windows paths

data_path :: String
--data_path = "M:\\COMP105\\Assignment 2\\data.csv"
data_path = "C:\\Users\\Olee\\Desktop\\data.csv"

sales_path :: String 
--sales_path = "M:\\COMP105\\Assignment 2\\sales.txt"
sales_path = "C:\\Users\\Olee\\Desktop\\sales.txt"

---- Part A

convert_single :: [Float] -> Float -> [Float]
convert_single series exchange = map (*exchange) series

convert_all :: [[Float]] -> Float -> [[Float]]
convert_all series exchange = map ( map (*exchange)) series

days_above :: [Float] -> Float -> Int
days_above series amount = length (filter (>amount) series)


days_between :: [Float] -> Float -> Float -> Int
days_between series lower upper = length (filter (\s -> s > lower && s < upper) series)

----- Part B

modify_position :: Float -> Float -> Float -> Float -> Float
modify_position buy_price sell_price position price =
    if price <= buy_price || price >= sell_price then
    (if price >= sell_price
    then (if position == 0 then position else position-1)
    else (if price <= buy_price then position+1 else position))
    else position
    
final_position :: Float -> Float -> [Float] -> Float
final_position buy_price sell_price series = 
    foldl (\ acc x -> modify_position buy_price sell_price acc x) 0 series --37.52

daily_position :: Float -> Float -> [Float] -> [Float]
daily_position buy_price sell_price series =
    error "Not implemented"

--daily_position :: Float -> Float -> [Float] -> [Float]
--daily_position buy_price sell_price series =
--    scanl (\ acc x -> final_position buy_price sell_price x) 0 series


daily_holding_values :: Float -> Float -> [Float] -> [Float]
daily_holding_values buy_price sell_price series =
    error "Not implemented"

--daily_holding_values :: Float -> Float -> [Float] -> [Float]
--daily_holding_values buy_price sell_price series =
--foldl(\ acc x -> daily_holding_values buy_price sell_price acc x) 0 series


---- Part C

sales_final_position :: [String] -> [Float]
sales_final_position sales =
    error "Not implemented"

--


sales_holding_value :: [String] -> [[Float]] -> [Float]
sales_holding_value sales series =
    error "Not implemented"

--

---- Code for loading the data -- do not modify!

splitOn :: Eq a => a -> [a] -> [[a]]
splitOn sep [] = []
splitOn sep list =
    let
        ne_sep = \ x -> x /= sep
        first = takeWhile ne_sep list
        second = dropWhile ne_sep list
        rest = if null second then [] else tail second
    in
        first : splitOn sep rest

get_data :: [[Float]]
get_data = 
    unsafePerformIO $
        do
            file <- readFile data_path
            let line_split = splitOn '\n' file
                remove_r = map (filter (/='\r')) line_split
                full_split = map (splitOn ',') remove_r
                converted = map (map (read :: String -> Float)) full_split
            return converted

get_series :: Int -> [Float]
get_series n = 
    if n >= 0 && n < 10
    then (transpose get_data) !! n
    else error ("There is no series " ++ show n)

get_sales :: [String]
get_sales =
    unsafePerformIO $
        do 
            file <- readFile sales_path
            return $ map (filter (/='\r')) $ splitOn '\n' file

short_data :: [[Float]]
short_data = take 10 get_data

get_short_series :: Int -> [Float]
get_short_series n = take 10 (get_series n)

