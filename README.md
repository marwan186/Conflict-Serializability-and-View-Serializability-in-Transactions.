# Conflict-Serializability-and-View-Serializability-in-Transactions.
this project is to implement a Conflict/View Serializable Checker with the basic operation to test for the conflict Serializability and the View Serializability.
# introduction :

Databases are computer structures that save, organize, protect, and deliver data. ... Any system that manages databases is called a database management system, or DBMS.
Serial schedules have lower use of resources and poor efficiency. Two or more transactions are conducted simultaneously to maximize this. However the competitiveness of transactions can lead to database inconsistency. To prevent this we need to verify if these simultaneous schedules can be serialized or not. A schedule is called conflict serializable if it can be transformed into a serial schedule by swapping non-conflicting operations.
we say its conflict if it satisfy these conditions: 


●	They belong to different transactions
●	They operate on the same data item
●	At Least one of them is a write operation


# Implemented approach :

We used a viewConroller class that controls the GUI for the user. This class uses the class SerializationChecker which is a middle layer between the controller and the other operations of the system.
The other operations of the system are built with three classes: schedule, operation, and operation conflict. schedule stores the operations entered by the user, in the format “r1x,w3y,r4z, … etc” and parse the text into operations, then store these operations in a LinkedList<Operation>, on the Schedule you can call the method conflictSerializable() which will check if there is any conflicts and store the results, if there is any, it will return a String to the controller saying there is a conflict and here is the cycle we found, otherwise it will return a string saying there is no conflicts found. on the same class you can run the method precedenceGraph() which will return the graph so the controller can show it in the GUI
The class Operation represents operations, it stores the action, the transaction, and the item. This will make the parsing of text like “r2x” much easier.
The class OperationConflict represents operations’ conflicts. it stores the operations that conflict with each other to be represented in the precedenceGraph() method then shown on the GUI.



# Data Structures

1-  Action: which holds the action for the operation (r or w)
 
2-  Transaction: is the transaction number for the operation

3-  Item: which holds the item variable (x,y,z,etc.)
 
 
·  HashMap to check if the two schedulers are the same.
·  HashSet which examines the given schedule and returns a hashset of type OperationConflict containing node pairs which have been found to conflict
·  precedenceGraph() Method returning a flat precedence graph e.g (1->2)

 
# Methodology
 we have to show the Conflict/View Serializable rules to check
1. the precedence Graph is not cycle
2. Initial Read
3. Final Write
4. Update Read

