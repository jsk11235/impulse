# Comparison
Impulse allows you to compare different values. 

## Equals
The `=` operator is used to check if two values are equal.

Example:
```ipl
if 2 + 2 = 4
    display "2 + 2 = 4"
over
```

## Less Than
The `<` operator is used to check if one value is less than another.

Example:
```ipl
if 2 < 3
    display "2 is less than 3"
over
```

## Greater Than
The `>` operator is used to check if one value is greater than another.

Example:
```ipl
if 2 > 3
    display "2 is greater than 3"
over
```

## Less Than or Equal To, Greater Than or Equal To
You can check for each constraint seperately and then combine them using the `|` operator.

Example:
```ipl
if 2 < 3 | 2 = 3
    display "2 is less or equal to 3"
over
```