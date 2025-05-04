# LR(0) Parser

This project implements a bottom-up LR(0) parser in Java. It reads grammar rules, action and goto tables from files and parses a sequence of tokens, producing a parse tree and a detailed trace of parsing steps.

## Features

- Shift-reduce LR(0) parsing
- Grammar rules loaded from file
- Inputs loaded from file
- Configurable ActionTable and GotoTable
- Builds a parse tree during reductions
- Output printed to file and includes:
    - Stack, Input, and Action per step
    - Parse tree printed in path format

## Requirements

- Java JDK 17 or newer

Check your version:

```bash
java -version
```
## How to Compile
```bash
javac -d out src/main/java/com/swe204/lrparser/**/*.java
```
This compiles the entire project into the out/ folder.

## How to Run
```bash
java -cp out com.swe204.lrparser.Main
```
The parser will:

Read input files: src/main/resources/input/input1.txt through input9.txt

Parse each file

Write output to: src/main/resources/output/output1.txt, output2.txt, etc.

## Input Format
Each file in resources/input/ should contain a single line of space-separated tokens. Example:

```bash
id + id * id $
```
## Output Format
```bash
Stack                                    Input                                   Action
--------------------------------------------------------------------------------------------------------
0id5                                     id + id * id $                          Shift 5
0F3                                      + id * id $                             Reduce 6 (GOTO [0, F])
...

--------------------------------------------------------------------------------------------------------
Parse tree:
/E
/E/E
/E/E/T
/E/E/T/F
/E/E/T/F/id
/E/+
/E/T/T/F/id
...
ACCEPTED
```
## Notes
If src/main/resources/output/ does not exist, it will be created automatically.

### Hülya Cengiz