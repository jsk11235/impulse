<html>
  <head></head>
  <body>
  <p align="center">
  <img src="https://user-images.githubusercontent.com/67127399/170313423-6a3eff48-c339-47b3-80f2-5778aafbb511.png" width="250px" height="150px">

  <h1 align="center">Impulse Language</h1>
    <h3 align="center">by DrBracket</h3>
<pre>
DrBracket: William Vongphanith, Ari Gurovich, Jacob Kirmayer
APCS1
</pre>

  <h2>What is this</h2>
    Our product is called... <strong>Impulse Language</strong>!<br>
    It is a novel performant programming language that is based on procedures and files.
  <h2>Prerequisites</h2>
    - JDK installed
    - bash shell
  <h2>Launch</h2>
    <code>javac Woo.java && java Woo</code> (One time)
    <code>source ~/.bashrc </code>
    <code>impulse [directory] [file (without .ipl)] [params]</code>
  </p>
  </body>
</html>

 # Syntax
 
 ## Parameter Declarations
   Used to declare parameters to a file. Each file is one procedure. 
    
    param foo
    param goo
    
   Will declare foo and goo as variables taking the value of the first and second input into the procedure respectively
    
## Variable Declarations
    var foo = 4*5
    
 Will declare foo as a variable taking a value of 20.
 
 ## Result Declarations
    res foo = f:4,5
    
 Will declare foo as a variable, equal to the result of function f on 4 and 5. For f to be called, it must be in a file called f.ipl in the same directory.
    
  ## Print Statements
   Used to print numerical values. 
    
    print 1*2+5
   
   will print a value of 7, for example.
    
 ## Display Statements
 
   Used to print strings. 
    
    display 1*2+5 
    
   will print the string "1*2+5".
    
 ## Conditional Statements
     if n>3
        return 3
     over
     return n
     
  Begins with a boolean statement following 'if' and ends with 'over'. This piece of code will return n as long as it's no more than 3. If n
  is greater than 3, 3 is returned instead.
     
  ## Math Operations
    + add
    - subtract
    * multiply
    / divide
    ^ exponent
    % mod
    [ forward parentheses
    ] reverse parentheses
    > greater than
    < less than
    = equal to
    PEMDAS is employed while parsing operations
    
    
  ## Boolean operations
    & and
    | or

# Configuration
   ## The .iplrc
      This file will be created in your home directory. It should look something like this.
        
        precision 10
        scientific false
        
        You can use this to configure the display of numerical values
        
 # Sample Code
 (choose.ipl) This code will calculate the number of ways to choose m items from a set of n, using recursion.
    
    param m
    param n
    if m>n
      return 0
    over
    if m<0
      return 0
    over
    if n=0
      return 1
    over
    res chooseAll = choose: m,n-1
    res chooseAllButOne = choose: m-1,n-1
    print n
    print m
    print chooseAll+chooseAllButOne
    return chooseAll+chooseAllButOne


<h1>BIG NOTE</h1>
<p>Woo.java installs the whole language onto your computer (well really it just installs an alias but we can pretend it's very cool)</p>
