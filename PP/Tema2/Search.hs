{-# OPTIONS_GHC -Wall #-}
{-# LANGUAGE MultiParamTypeClasses #-}

module Search where

import ProblemState

import qualified Data.Set as S

{-
    *** TODO ***

    Tipul unei nod utilizat în procesul de căutare. Recomandăm reținerea unor
    informații legate de:

    * stare;
    * acțiunea care a condus la această stare;
    * nodul părinte, prin explorarea căruia a fost obținut nodul curent;
    * adâncime
    * copiii, ce vor desemna stările învecinate
-}

data Node s a = Node {
    state :: s,
    action :: a,
    parent :: Node s a,
    depth :: Int,
    children :: [Node s a],
    isRoot :: Bool
}
    deriving (Eq, Ord)

{-
    *** TODO ***

    Întoarce starea stocată într-un nod.
-}

nodeState :: Node s a -> s
nodeState node = state node

{-
    *** TODO ***

    Generarea întregului spațiu al stărilor 
    Primește starea inițială și creează nodul corespunzător acestei stări, 
    având drept copii nodurile succesorilor stării curente.
-}

createStateSpace :: (ProblemState s a) => s -> Node s a
createStateSpace st = Node st undefined undefined 0 (map (createChild (createStateSpace st) 1 False) (successors st)) True


createChild :: (ProblemState s a) => Node s a -> Int -> Bool -> (a, s) -> Node s a
createChild par dep isroot succs = Node (snd succs) (fst succs) par dep (map (createChild (Node (snd succs) (fst succs) par dep undefined isroot) (dep + 1) False) (successors (snd succs))) False

{-
    *** TODO PENTRU BONUS ***

    Ordonează întreg spațiul stărilor după euristica din ProblemState. 
    Puteți folosi `sortBy` din Data.List.
-}

orderStateSpace :: (ProblemState s a) => Node s a -> Node s a
orderStateSpace = undefined


{-
    *** TODO ***

    Întoarce lista nodurilor rezultate prin parcurgerea limitată în adâncime
    a spațiului stărilor, pornind de la nodul dat ca parametru.

    Pentru reținerea stărilor vizitate, recomandăm Data.Set. Constrângerea
    `Ord s` permite utilizarea tipului `Set`.
-}

limitedDfs :: (ProblemState s a, Ord s)
           => Node s a    -- Nodul stării inițiale
           -> Int         -- Adâncimea maximă de explorare
           -> [Node s a]  -- Lista de noduri
limitedDfs root dep = limitedDfsList dep [root] [] []

{-
    dep is the limit depth of the dfs
    open is a list (used as the stack in imperative programming);
    close is the set of nodes with nonduplicative states
    state is a set with nonduplicative states, used to help with the insertion in the close set
-}

limitedDfsList :: (ProblemState s a, Ord s) => Int -> [Node s a] -> [Node s a] -> [s] -> [Node s a]
limitedDfsList dep open close states  
    | length open == 0 = close
    | depth (first) < dep = if (elem (state first) states) 
                            then limitedDfsList dep ((children first) ++ (tail open)) close states 
                            else limitedDfsList dep ((children first) ++ (tail open)) (close ++ [first]) (states ++ [state first])
    | otherwise = if (elem (state first) states) 
                  then limitedDfsList dep (tail open) close states 
                  else limitedDfsList dep (tail open) (close ++ [first]) (states ++ [state first])
        where first = head open

{-
    *** TODO ***

    Explorează în adâncime spațiul stărilor, utilizând adâncire iterativă,
    pentru determinarea primei stări finale întâlnite.

    Întoarce o perche între nodul cu prima stare finală întâlnită și numărul
    de stări nefinale vizitate până în acel moment.
-}

iterativeDeepening :: (ProblemState s a, Ord s)
    => Node s a         -- Nodul stării inițiale
    -> (Node s a, Int)  -- (Nod cu prima stare finală,
                        --  număr de stări nefinale vizitate)
iterativeDeepening root = 
    -- (last nodesList, length nodesList)
    -- where nodesList = takeWhile (\next -> not (isGoal (state next))) $ (foldl (\acc x -> acc ++ ((limitedDfs root) x)) [] [0..])
     {-(foldl (\acc x -> acc ++ [x]) []-} 
    idHelper root 0 [] 0

idHelper :: (ProblemState s a, Ord s) => Node s a -> Int -> [Node s a] -> Int -> (Node s a, Int)
idHelper root limit searchList visited
    | length searchList == 0 = idHelper root (limit + 1) (limitedDfs root (limit + 1)) visited
    | isGoal (state (head searchList)) = (head searchList, visited + 1)
    | otherwise = idHelper root limit (tail searchList) (visited + 1)

{-
    *** TODO ***

    Pornind de la un nod, reface calea către nodul inițial, urmând legăturile
    către părinți.

    Întoarce o listă de perechi (acțiune, stare), care se încheie în starea
    finală, dar care EXCLUDE starea inițială.
-}

extractPath :: (Eq a, Eq s) => Node s a -> [(a, s)]
extractPath node = reverse $ map (\x -> (action x, state x)) list
    where list = takeWhile (\next -> (isRoot next) == False) $ iterate (\x -> parent x) node
    
    --[(action (parent (parent node)), state (parent (parent node))), (action (parent node), state (parent node)), (action node, state node)] --pathHelper node []

-- pathHelper :: (Eq a, Eq s) => Node s a -> [(a, s)] -> [(a, s)]
-- pathHelper node = map (\x -> (action x, state x)) list
--     where list = takeWhile (\next -> (parent next) /= undefined)) $ iterate (\x -> parent x) node


{-
    *** TODO ***

    Pornind de la o stare inițială, se folosește de iterativeDeepening pentru 
    a găsi prima stare finală și reface calea către nodul inițial folosind 
    extractPath. 
  
    Întoarce o listă de perechi (acțiune, stare), care se încheie în starea
    finală, dar care EXCLUDE starea inițială.
-}

solve :: (ProblemState s a, Ord s, Eq a)
      => s          -- Starea inițială de la care se pornește 
      -> Bool       -- Dacă să folosească sau nu euristica dată 
      -> [(a, s)]   -- Lista perechilor
solve st heur = extractPath $ fst (iterativeDeepening (createStateSpace st))

{-
    Poate fi utilizată pentru afișarea fiecărui element al unei liste
    pe o linie separată.
-}

printSpacedList :: Show a => [a] -> IO ()
printSpacedList = mapM_ (\a -> print a >> putStrLn (replicate 20 '*'))