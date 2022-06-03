# Impulse Language Documentation

## General Syntax

### Parameter Declaration

```
param [variable]   
```
e.x. param x declares variable x 

### If Statements

```
if [boolean statement]  
  [executable code]  
over
```

```ipl
param n
param g
if n = g then
    print "n equals g"
else
    print "n doesn't equal g"
```
over

### Boolean Operators

Impulse uses the following boolean operators:
- operator `>` for greater than
- operator `<` for less than
- operator `=` for equal to

```ipl
param n
param g
if n >5
    if g <5
      return n-1
    over
    return n+1
over
```

### Arithmetic Operators

Impulse uses the following arithmetic operators:
- operator `+` for addition
- operator `-` for subtraction
- operator `*` for multiplication
- operator `/` for division
- operator `%` for mod
- operator `^` for exponent

    
```ipl
param n
param g
return n+g
```
    

### Running Files

While running files, one must run the file followed by inputs for each parameter the file takes in.  
If an impulse file name Code.ipl takes in two params, one would run it using the following example statement:  

```bash
$ java Procedure Code 17 4
```

You can also create an alias and pretend this is a legit interpreter.

```bash
$ alias impulse="java Procedure"
```

```bash
$ impulse Code 17 4
```

### `.iplrc`

This file is used to store user preferences.  It is located in the same directory as the user's home directory.
For example, you can configure decimal precision to be 2 by adding the following line to your `.iplrc` file:

```bash
decimal_precision 2
```