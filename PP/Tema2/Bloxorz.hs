{-# OPTIONS_GHC -Wall #-}
{-# LANGUAGE EmptyDataDecls, MultiParamTypeClasses,
             TypeSynonymInstances, FlexibleInstances,
             InstanceSigs #-}


module Bloxorz where

import ProblemState

import qualified Data.Array as A

{-
    Caracterele ce vor fi printate pentru fiecare tip de obiect din joc 
    Puteți înlocui aceste caractere cu orice, în afară de '\n'.
-}

hardTile :: Char
hardTile = '▒'

softTile :: Char
softTile = '='

block :: Char
block = '▓'

switch :: Char
switch = '±'

emptySpace :: Char
emptySpace = ' '

winningTile :: Char
winningTile = '*'

{-
    Sinonim de tip de date pentru reprezetarea unei perechi (int, int)
    care va reține coordonatele de pe tabla jocului
-}

type Position = (Int, Int)

{-
    Direcțiile în care se poate mișcă blocul de pe tablă
-}

data Directions = North | South | West | East
    deriving (Show, Eq, Ord)

{-
    *** TODO ***

    Tip de date care va reprezenta plăcile care alcătuiesc harta și switch-urile
-}

data Cell = HardTile | SoftTile | Block | Switch | EmptySpace | WinningTile
    deriving (Eq, Ord)

{-
    show returneaza string-uri asa ca avem nevoie de o functie care sa converteasca 
    char-urile cu care lucram 
 -}

charToString :: Char -> String
charToString = (:[])

instance Show Cell where
    show HardTile = charToString hardTile
    show SoftTile = charToString softTile
    show Block = charToString block
    show Switch = charToString switch
    show EmptySpace = charToString emptySpace
    show WinningTile = charToString winningTile

{-
    *** TODO ***

    Tip de date pentru reprezentarea nivelului curent
-}

data State = Lost | Won | Ongoing
    deriving (Eq, Ord)

data Level = Level {
    -- o matrice indexata dupa pozitii, care are ca elemente celulele
    myMap :: A.Array Position Cell, 
    {-
        array indexat pozitia switch-urilor introduse, care are ca elemente pozitiile
        tile-urilor activate/dezactivate
    -}
    switches :: A.Array Position [Position], 
    -- pozitia blocului cu care jucam
    blockPos :: (Position, Position),
    -- starea jocului
    state :: State
}
   deriving (Eq, Ord)

{-
    *** Opțional *** 
  
    Dacă aveți nevoie de o funcționalitate particulară, 
    instantiati explicit clasele Eq și Ord pentru Level. 
    În cazul acesta, eliminați deriving (Eq, Ord) din Level. 
-}

-- instance Eq Level where
--     (==) = undefined

-- instance Ord Level where
--     compare = undefined

{-
    *** TODO ***

    Instantiati Level pe Show. 

    Atenție! String-ul returnat va fi urmat și precedat de un rând nou. 
    În cazul în care jocul este câștigat, la sfârșitul stringului se va mai
    concatena mesajul "Congrats! You won!\n". 
    În cazul în care jocul este pierdut, se va mai concatena "Game Over\n". 
-}

instance Show Level where
    show level = (foldl (\acc x -> if (fst x == fst (blockPos level)) || (fst x == snd (blockPos level)) 
                                    then (if (snd (fst x)) == snd (snd (A.bounds (myMap level))) 
                                    then acc ++ (charToString block) ++ "\n" else acc ++ (charToString block))
                                    else (if (snd (fst x)) == snd (snd (A.bounds (myMap level)))
                                            then acc ++ (show (snd x)) ++ "\n" 
                                            else acc ++ (show (snd x)) )) "\n" (A.assocs (myMap level))) ++
                 case (state level) of Ongoing -> ""
                                       Lost -> "Game Over\n"
                                       Won -> "Congrats! You won!\n"
        
{-
    *** TODO ***

    Primește coordonatele colțului din dreapta jos a hârtii și poziția inițială a blocului.
    Întoarce un obiect de tip Level gol.
    Implicit, colțul din stânga sus este (0, 0).
-}

emptyLevel :: Position -> Position -> Level
emptyLevel size blkPos = Level (A.array ((0, 0), size) [((x, y), EmptySpace) | x <- [0..(fst size)], y <- [0..(snd size)]])
                               (A.array ((0, 0), size) []) (blkPos, (-1, -1)) Ongoing 

{-
    *** TODO ***

    Adaugă o celulă de tip Tile în nivelul curent.
    Parametrul char descrie tipul de tile adăugat: 
        'H' pentru tile hard 
        'S' pentru tile soft 
        'W' pentru winning tile 
-}

addTile :: Char -> Position -> Level -> Level
addTile tile pos level = let thisMap = myMap level in
    Level (thisMap A.// [(pos , case tile of 'H' -> HardTile
                                             'S' -> SoftTile
                                             'W' -> WinningTile
                                             _ -> EmptySpace)])
          (switches level) (blockPos level) (state level) 


{-
    *** TODO ***

    Adaugă o celulă de tip Swtich în nivelul curent.
    Va primi poziția acestuia și o listă de Position
    ce vor desemna pozițiile în care vor apărea sau 
    dispărea Hard Cells în momentul activării/dezactivării
    switch-ului.
-}

addSwitch :: Position -> [Position] -> Level -> Level
addSwitch switchPos activatedTiles level = Level (changedMap) (currSwitches A.// [(switchPos, activatedTiles)]) (blockPos level) (state level)
    where changedMap = ((myMap level) A.// [(switchPos, Switch)])
          currSwitches = switches level


{-
    === MOVEMENT ===
-}

{-
    *** TODO ***

    Activate va verifica dacă mutarea blocului va activa o mecanică specifică. 
    În funcție de mecanica activată, vor avea loc modificări pe hartă. 
-}

activate :: Cell -> Level -> Level
activate cell level 
    | cell == EmptySpace = Level (myMap level) (switches level) (blockPos level) Lost 
    | cell == WinningTile  = Level (myMap level) (switches level) (blockPos level) Won
    | cell == SoftTile && (snd (blockPos level)) == (-1, -1) = Level (myMap level) (switches level) (blockPos level) Lost
    | cell == Switch = Level (changedMap) (switches level) (blockPos level) Ongoing
    | otherwise = level 
        where changedMap = (myMap level) A.// [((pos), case (myMap level) A.! pos of HardTile -> EmptySpace
                                                                                     EmptySpace -> HardTile
                                                                                     _ -> EmptySpace)
                            | pos <- ((switches level) A.! switchPos)]
              switchPos = if (myMap level) A.! (fst (blockPos level)) == Switch then (fst (blockPos level))
                          else (snd (blockPos level))
                            

{-
    *** TODO ***

    Mișcarea blocului în una din cele 4 direcții 
    Hint: Dacă jocul este deja câștigat sau pierdut, puteți lăsa nivelul neschimbat.
-}

move :: Directions -> Level -> Level
move dir level 
    | (state level == Won) || (state level == Lost) = level
    -- north
    | (dir == North) && standing = activate (chooseCell (myMap level A.! ((fst fstPos) - 1, snd fstPos)) (myMap level A.! ((fst fstPos) - 2, snd fstPos))) 
        (Level (myMap level) (switches level) (((fst fstPos) - 1, snd fstPos), ((fst fstPos) - 2, snd fstPos)) Ongoing)
    | (dir == North) && horizontal = activate (chooseCell (myMap level A.! (fst fstPos - 1, snd fstPos)) (myMap level A.! (fst fstPos - 1, snd fstPos + 1))) 
        (Level (myMap level) (switches level) ((fst fstPos - 1, snd fstPos), (fst fstPos - 1, snd fstPos + 1)) Ongoing)
    | dir == North = activate (myMap level A.! ((fst fstPos - 2, snd fstPos))) 
        (Level (myMap level) (switches level) ((fst fstPos - 2, snd fstPos), (-1, -1)) Ongoing)
    -- south
    | (dir == South) && standing = activate (chooseCell (myMap level A.! ((fst fstPos) + 2, snd fstPos)) (myMap level A.! ((fst fstPos) + 1, snd fstPos))) 
        (Level (myMap level) (switches level) (((fst fstPos) + 2, snd fstPos), ((fst fstPos) + 1, snd fstPos)) Ongoing)
    | (dir == South) && horizontal = activate (chooseCell (myMap level A.! (fst fstPos + 1, snd fstPos)) (myMap level A.! (fst fstPos + 1, snd fstPos + 1))) 
        (Level (myMap level) (switches level) ((fst fstPos + 1, snd fstPos), (fst fstPos + 1, snd fstPos + 1)) Ongoing)
    | dir == South = activate (myMap level A.! ((fst fstPos + 1, snd fstPos))) 
        (Level (myMap level) (switches level) ((fst fstPos + 1, snd fstPos), (-1, -1)) Ongoing)
    -- west
    | (dir == West) && standing = activate (chooseCell (myMap level A.! (fst fstPos, snd fstPos - 2)) (myMap level A.! (fst fstPos, snd fstPos - 1))) 
        (Level (myMap level) (switches level) ((fst fstPos, snd fstPos - 2), (fst fstPos, snd fstPos - 1)) Ongoing)
    | (dir == West) && (not horizontal) = activate (chooseCell (myMap level A.! (fst fstPos, snd fstPos - 1)) (myMap level A.! (fst fstPos - 1, snd fstPos -1))) 
        (Level (myMap level) (switches level) ((fst fstPos, snd fstPos - 1), (fst fstPos - 1, snd fstPos - 1)) Ongoing)
    | dir == West = activate (myMap level A.! ((fst fstPos, snd fstPos - 1))) 
        (Level (myMap level) (switches level) ((fst fstPos, snd fstPos - 1), (-1, -1)) Ongoing)
    -- est
    | (dir == East) && standing = activate (chooseCell (myMap level A.! (fst fstPos, snd fstPos + 1)) (myMap level A.! (fst fstPos, snd fstPos + 2))) 
        (Level (myMap level) (switches level) ((fst fstPos, snd fstPos + 1), (fst fstPos, snd fstPos + 2)) Ongoing)
    | (dir == East) && (not horizontal) = activate (chooseCell (myMap level A.! (fst fstPos, snd fstPos + 1)) (myMap level A.! (fst fstPos - 1, snd fstPos + 1))) 
        (Level (myMap level) (switches level) ((fst fstPos, snd fstPos + 1), (fst fstPos - 1, snd fstPos + 1)) Ongoing)
    | dir == East = activate (myMap level A.! ((fst fstPos, snd fstPos + 2))) 
        (Level (myMap level) (switches level) ((fst fstPos, snd fstPos + 2), (-1, -1)) Ongoing)
    | otherwise = level
        where standing = case (snd $ blockPos level) of (-1, -1) -> True -- blocul ocupa un tile
                                                        _ -> False -- blocul ocupa doua tile-uri
              horizontal = if (fst $ fst $ blockPos level) == (fst $ snd $ blockPos level) then True else False 
                                                        
              fstPos = (fst $ blockPos level)

chooseCell :: Cell -> Cell -> Cell
chooseCell cell1 cell2 
    | (cell1 == EmptySpace) || (cell2 == EmptySpace) = EmptySpace
    | (cell1 == WinningTile) || (cell2 == WinningTile) = WinningTile
    | (cell1 == Switch) || (cell2 == Switch) = Switch
    | (cell1 == SoftTile) || (cell1 == SoftTile) = SoftTile
    | otherwise = HardTile

{-
    *** TODO ***

    Va returna True dacă jocul nu este nici câștigat, nici pierdut.
    Este folosită în cadrul Interactive.
-}

continueGame :: Level -> Bool
continueGame level 
    | (state level) == Ongoing = True
    | otherwise = False

{-
    *** TODO ***

    Instanțiați clasa `ProblemState` pentru jocul nostru. 
  
    Hint: Un level câștigat nu are succesori! 
    De asemenea, puteți ignora succesorii care 
    duc la pierderea unui level.
-}

instance ProblemState Level Directions where
    successors level = foldl (\acc x -> if (state (move x level) == Ongoing) then acc ++ [(x, move x level)] else acc) [] [North, South, East, West]

    isGoal level
        | (state level == Won) = True
        | otherwise = False

    -- Doar petru BONUS
    -- heuristic = undefined
