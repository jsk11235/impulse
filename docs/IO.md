# I/O
There are a variety of ways to interact with the user. Impulse provides some basic ways to interact with the user.

## Print
The `print` function is used to print out a message to the user. It takes a single number or a mathematical expression as an argument.

Example:
```ipl
print 1
print 3+5
```

## Display
The `display` function works in much the same way as `print`, but instead of printing out a numerical value, it takes a string as an argument.

Example:
```ipl
display "Hello World!"
display " This is on the same line as the previous line"
```

## DisplayLine
The `displayln` function displays a string, but it also adds a newline to the end of the string.

Example:
```ipl
displayln "Hello World!"
displayln " This is on another line."
```

## Prompt
The `prompt` function is used to ask the user for input. It takes two arguments: a variable name and a string to display to the user.

Example:
```ipl
prompt num1,"Enter a number: "
prompt num2,"Enter another number: "
```
