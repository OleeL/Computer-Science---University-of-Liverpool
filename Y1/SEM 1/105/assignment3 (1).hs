import System.Environment
import System.IO

maze_path = "M:\\maze3.txt"

-- Useful code from Lecture 25
-- You may use this freely in your solutions

get :: [String] -> Int -> Int -> Char
get maze x y = (maze !! y) !! x 

modify_list :: [a] -> Int -> a -> [a]
modify_list list pos new =
    let
        before = take  pos    list
        after  = drop (pos+1) list
    in
        before ++ [new] ++ after

set :: [String] -> Int -> Int -> Char -> [String]
set maze x y char = 
    let
        line = maze !! y
        new_line = modify_list line x char
        new_maze = modify_list maze y new_line
    in
        new_maze

---- Part A

-- Question 1

get_maze :: String -> IO [String]
get_maze path = do 
    str <- readFile path
    return (lines str)

-- Question 2

print_maze :: [String] -> IO ()
print_maze m = do
    putStrLn (unlines m)

-- Question 3

is_wall :: [String] -> (Int, Int) -> Bool
is_wall m (x,y) = if ((m !! x) !! y) == '#' then True else False

-- Question 4
place_player :: [String] -> (Int, Int) -> [String]
--place_player m (x, y) =  (([take (y-1) m]) ++ [(take (x-1) (m !! y)) ++ ('@' : drop x (m !! y))]) ++ [drop y m]
place_player m (x, y) = set m x y '@'
---- Part Bx

-- Question 5

move :: (Int, Int) -> Char -> (Int, Int)
move (x, y) c = if ((c == 'w') || (c == 's')) then 
               (if c == 'w' then (x, y-1) else (x, y+1)) else 
               (if c == 'a' || c == 'd' then (if c == 'a' then (x-1, y) else (x+1, y)) else 
                   (if c /= 'a' then (x, y) else (x, y)))

-- Question 6

can_move :: [String] -> (Int, Int) -> Char -> Bool
can_move m (x, y) c = 
    let
        (z, v) = move (x, y) c
    in 
        if get m z v == '#' then False else True

-- Question 7

game_loop :: [String] -> (Int, Int) -> IO ()
game_loop m (x, y) = 
    do
        print_maze (place_player m (x, y))
        c <- getChar
        if can_move m (x, y) c then 
            game_loop m (move (x, y) c)
        else
            game_loop m (x, y)
        



---- Part C

-- Question 8
get_maze_size :: [String] -> (Int,Int)
get_maze_size m =
    let
        width = length (m !! 0)
        height = length m
    in
        (width, height)

get_start :: (Int, Int)
get_start = (1,1)
        
get_finish_coord :: [String] -> (Int,Int)
get_finish_coord m =
    let
        (w, h) = get_maze_size m
    in
        (w-2, h-2)
        
        
--MT = 
data MT a = MEN
              | MFN a [(MT a)]
              deriving (Eq,Ord,Show,Read)
                       


{- 
SOLVING PART C WITH TREES (I ended up getting too confused with this solution so I used the other one)

This is used like so:
MFN 1 [MFN 5 [MEN],MFN 7 [MFN 92 [MEN]],MFN 8 [MEN],MFN 23 [MEN]]

      1-¬
     /|\ \
    / | \ \
   5  7  8 23
     /
   92

MFN (1,1) [MFN (6,2) [MEN],MFN (7,2) [MFN (8,2) [MEN]],MFN (5,2) [MEN],MFN (1,2) [MEN]]

     (1,1) ___
     / | \    \
    /  |  \    \
(6,2)(7,2)(5,2)(1,2)
     /
  (8,2)


-}

--Solving part C WITHOUT TREES
next_move :: [String] -> (Int, Int) -> Bool -> Bool -> Bool -> Bool -> (Int, Int)
next_move m (x,y) up down left right = if right || down then if right then (x+1,y) else (x,y+1) else if left || up then (x-1,y) else (x,y-1) 
 
get_path :: [String] -> (Int, Int) -> (Int, Int) -> [(Int, Int)]
get_path m (x1, y1) (x2, y2) =
  let up                  = can_move m (x1,y1) 'w'
      down                = can_move m (x1,y1) 's'
      left                = can_move m (x1,y1) 'a'
      right               = can_move m (x1,y1) 'd'
      isEnd               = if ((next_move m (x1,y1) up down left right) == (x2, y2)) then False else True
  in
      if isEnd then
          [(x1,y1)]++(get_path m (next_move m (x1,y1) up down left right)(x2, y2))
      else
          [(x1,y1),(x2,y2)]



-- Question 9

-- It doesn't run all of the mazes because of my poor algorithm. I understand why it doesnt work
-- I just didn't know how to do it.
-- This was the best solution i could come up with

main :: IO ()
main = do
      a <- getArgs
      m <- get_maze (a!!0)
      --m <- get_maze maze_path
      print_maze (solve m (get_path m (get_start)(get_finish_coord m)))

solve :: [String] -> [(Int, Int)] -> [String]
solve m wp =
  let
      winpath = wp
      next = tail winpath
      (x, y) = (winpath !! 0)
      empty = if next == [] then True else False
  in
      if not empty then
        (solve (set m x y '.') next)
      else
        set m x y '.'

