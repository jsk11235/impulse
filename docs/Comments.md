# Comments
Commenting your code is a crucial part of the programming pipeline. Impulse gives you multiple ways to comment your code. Commented code does not have any impact on the program's output.

## Single-line comments
You can create a single line comment by prefixing your line with a `$`. These must be on their own line or errors could potentially be thrown.

Example:
```ipl
$ This is a comment
$ This is another comment
$ Slick huh?
```

## Multi-line comments

You can open a multi-line comment by prefixing your line with a `[$`. This must be on its own line or errors could potentially be thrown.
To close the comment, you must prefix your line with a `$].`

Example:
```ipl
[$ 
   This is a multi-line comment
   Whoa, that's a lot of comments!
   I'm not sure how to handle this.
$]