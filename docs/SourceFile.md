# `.iplrc`
This file is used to store user preferences.  It is located in the same directory as the user's home directory.

## Precision
Stores the number of decimal places to display when displaying numerical values. This also controls when to display the number in scientific notation when the number is too large to display in the given precision.

Default: 10

Example: `precision 12`

## Overflow
Stores the overflow constant for any mathematical operation. When a number exceeds 10^overflow, an error will be thrown. This constant should be set between 1 and 308, since anything above 308 is above the precision stored by Java's double precision.

Default: 100

Example: `overflow 308`

## Epsilon
Stores the epsilon constant for any mathematical operation. When a number is less than epsilon, it is considered to be zero. This is used to determine whether a number has a rounding error, so it can be removed.

Default: 0.00000001

Example: `epsilon 0.000005`

## Mode
Sets the mode of the program. This can be either `normal` (what you would expect, numbers are displayed normally and if they are too big, they are displayed in scientific notation) or `scientific` (numbers are always displayed in scientific notation).

Default: normal

Example: `mode scientific`