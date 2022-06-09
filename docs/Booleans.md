# Booleans
Impulse provides basic boolean functionality that you'd expect in a programming language.

## True/False
`true` and `false` are the two basic boolean values.

## And
The `&` operator is used to check if two booleans are both `true`.

Example:
```ipl
if true & true
    display "Both booleans are true"
over
```

## Or
The `|` operator is used to check if at least one of two booleans is `true`.

Example:
```ipl
if true | false
    display "At least one boolean is true"
over
```

## Xor
The `#` operator is used to check if only one of two booleans is `true`. We used this operator because we used `^` for exponentiation earlier.

Example:
```ipl
if true # false
    display "Only one boolean is true"
over
```

## Not
While Impulse does not have a `!` operator due to boolean parser workings, you can use `<expression> # true` as a replacement.

Example:
```ipl
if 1 = 2 # true
  displayln "1 is not equal to 2"
over
```
