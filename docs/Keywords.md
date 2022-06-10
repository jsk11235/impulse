# Keywords
This is a review of the keywords used in the language.

## Return
The `return` keyword is used to return a value from a procedure.

Example:
```ipl
return 3
```

## Import
The `import` keyword is used to import other procedures from external directories.

Example:
```ipl
import "./Procedures/add.ipl" as "add"
```

## Param
The `param` keyword is used to define a parameter for a procedure.

Example:
```ipl
param x
param y
```

## Var
The `var` keyword is used to define a variable for a procedure.

Example:
```ipl
var x = 3
```

## Res
The `res` keyword is used to define a result of execution from another procedure.

Example:
```ipl
res answer = add: x, y
```

## If
The `if` keyword is used to start an if statement.

Example:
```ipl
if x > y
    return x
over
```

## Over
The `over` keyword is used to end an if statement.

Example (refer to the previous example)

## Print
The `print` keyword is used to print a numeric value to the terminal.

Example:
```ipl
print 1 + 2
```

## Display
The `display` keyword is used to display a string value to the terminal.

Example:
```ipl
display "Hello, world!"
```

## Displayln
The `displayln` keyword is used to display a string value to the terminal, followed by a newline.

Example:
```ipl
displayln "Hello, world!"
```

## Prompt
The `prompt` keyword is used to request input from the terminal.

Example:
```ipl
prompt number,"Enter a number: "
```

