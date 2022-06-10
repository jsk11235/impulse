# Procedures
Procedures are the basic building blocks that make up Impulse. With procedures, you can create functions that are callable from elsewhere, and that can be used to perform computations.

## Structure
Each procedure must contain a return statement. Common practice is to return 0 when the procedure is being called from the terminal, rather than from another procedure. However, to return a value to another procedure, you would use `return`.

In addition, procedures can contain a list of parameters, which are variables that are passed to the procedure when it is called.

You can call other procedures from within a procedure. By default, all procedures within a directory are available to be called from within other procedures. You can also call `import` to import other procedures that are possibly not in the same directory. Imported procedures take precedence over procedures in the same directory, so if an imported procedures shares a name with a procedure in the same directory, the imported procedure will be called instead of the one in the directory.

## Calling from the terminal
You can call a procedure from the terminal by typing:

```
impulse <procedure name> <param1> <param2> ...
```

Before trying to do this, please make sure that the alias for Impulse exists (this should work after installing Impulse via Woo).

