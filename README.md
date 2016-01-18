#Sudoku solver

##Overview
Sudoku Solver is a java based standalone SWT application for solving sudoku puzzles. It uses recursive Backtracking algorithm to solve puzzles of any complexity.

##Backtracking algorithm

Backtracking is a general algorithm for finding all (or some) solutions to some computational problems, notably constraint satisfaction problems, that incrementally builds candidates to the solutions, and abandons each partial candidate c ("backtracks") as soon as it determines that c cannot possibly be completed to a valid solution.

###Key steps to implement recursive backtracking algorithm for solving suduko puzzle
 
1. Identifying the data structure which best represents the input parameters of the problem and the partial states of it's solution. In Sudoku solver it is a `Grid` class which is composed of a 2D array of bytes to store the partially solved grid and a 3D boolean array which stores the probable candidates for a corresponding empty box in 2D array. Below diagram illustrates a partial state.
The memory footprint of this data structure needs to be kept as small as possible as we will have multiple instances of it in stack during recursion calls.

<img src="https://github.com/nik200/Swing-SodukuSolver/blob/master/img/2D-array.png" width="45%"></img>
<img src="https://github.com/nik200/Swing-SodukuSolver/blob/master/img/3D-Array.png" width="40%"></img>

2. Algorithm to derive the next possible (temporary)state which is one step closer to solution. In Soduku solver it is done by choosing a probable and assuming it to be a valid value for a box. Then based on this assumption the probables in the other boxes are calculated and the process is recurred unless a solution is found OR we end up having a partial state which cannot have any solution, in latter case the call is returned to the method where we made an assumption by choosing a probable and the other candidate probable is chosen.

##GUI

Sudoku solver is a java Swing application. With simple interface to accept preset values and then solve the grid. User can save the grid data and store the grid data to a .grid file which is a serialized object dump of `Grid` class object. 

<img src="https://github.com/nik200/Swing-SodukuSolver/blob/master/img/screen1.png" width="30%"></img>
<img src="https://github.com/nik200/Swing-SodukuSolver/blob/master/img/screen2.png" width="30%"></img>
<img src="https://github.com/nik200/Swing-SodukuSolver/blob/master/img/screen3.png" width="30%"></img>

##Future Scope

Implementing generic interfaces for backtracking algorithm to use with any data model which can model the initial and intermediate states of a puzzle eligible to be solved with recursive backtracking algorithm.
