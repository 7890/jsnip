```
LProps.java helps to quickly make a member variable of a class a configuration key saved in a .properties text file.
The Java reflection api is used to match configured keys and class member variables.
The class must declare the variable to be set as public.
If in the configuration file loaded any key is found that matches a public member variable, 
it will be parsed and the member variable will be set.
```
