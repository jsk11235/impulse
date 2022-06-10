# Variables
Impulse lets you declare and define variables easily.

# Var
`var` is used to declare a variable which has a value contained in the declaration.

Example:
```ipl
var x = 1
var y = 2 + 3
```

# Res
`res` is used to declare a variable which has a value that is computed by evaluating another procedure.

Example:
```ipl
res x = add: 1 2
res y = add: x 3
```

# Param
`param` is used to declare a variable whose value will be provided by the caller to the procedure.

Example:
```ipl
param x
param y
return x + y
```