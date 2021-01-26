package com;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;



public class Schedule {
    // the schedule which holds the operations
    private LinkedList<Operation> theSchedule;

    public Schedule() {
    }


   
    public Schedule(String[] schedule) {
        try {
            theSchedule = new LinkedList<Operation>();
            populate(schedule);
        } catch (Exception e) {
            System.out.println("There was an error with the importing of the schedule");
            System.out.println("The exception: ");
            e.printStackTrace();
        }
    }

   
    private void populate(String[] schedule) {
        for (int i = 0; i < schedule.length; i++) {
            String action = schedule[i].substring(0, 1);
            int transaction = Integer.parseInt(schedule[i].substring(1, 2));
            String item = schedule[i].substring(2);
            append(action, transaction, item);
        }
        System.out.println("schedule size: " + theSchedule.size());
    }

   
    private void append(String action, int transaction, String item) {
        theSchedule.add(new Operation(action, transaction, item));
    }

    
    public Operation getItem(int index) {
        return theSchedule.get(index);
    }

    public String getSchedule() {
        String schedule = "";
        for (Operation s : theSchedule) {
            schedule += s + ", ";
        }
        return schedule;
    }

   
    public String conflictSerializableSolution() {
        String result = "Is Schedule Conflict-Serializable: ";
        HashSet<OperationConflict> conflicts = conflictSerializableTest();
        for (OperationConflict i : conflicts) {
            Operation outerFrom = i.getFromOperation();
            Operation outerTo = i.getToOperation();
            for (OperationConflict j : conflicts) {
                Operation innerFrom = j.getFromOperation();
                Operation innerTo = j.getToOperation();
                //means schedule has a cycle, thus its conflict serailizable
                if (outerFrom.equals(innerTo) && outerTo.equals(innerFrom)) {
                    result += "False\n";
                    result += "There is a cycle between transactions: T" + outerFrom.getTransaction() + " and T" + innerFrom.getTransaction();
                    return result;
                }
            }
        }
        result += "True\n";
        result += "Schedule is acyclic, thus it's conflict serializable.\n";
        return result;
    }

//88888888888888888888888888888888888888888888888888888888888
 
    
   
    public String sameResult(Schedule s2) {
        HashMap<Character, Integer> firstReadS1 = new HashMap<>();
        HashMap<Character, Integer> firstReadS2 = new HashMap<>();
        HashMap<Character, Integer> finalWriteS1 = new HashMap<>();
        HashMap<Character, Integer> finalWriteS2 = new HashMap<>();
        HashMap<Operation, Integer> updateS1 = new HashMap<>();
        HashMap<Operation, Integer> updateS2 = new HashMap<>();
        
//        for (Operation o : theSchedule) {
        for (int i = 0; i < theSchedule.size(); i++) {
            Operation o = theSchedule.get(i);
            if (o.action == 'r') {
                if (!firstReadS1.containsKey(o.getItem())) {
                    firstReadS1.put(o.getItem(), o.getTransaction());
                }
                int lastWrite = finalWriteS1.containsKey(o.getItem()) ? finalWriteS1.get(o.getItem()) : 0;
                updateS1.put(o, lastWrite);
            } else {
                finalWriteS1.put(o.getAction(), o.getTransaction());
            }

        }
        for (int i = 0; i < theSchedule.size(); i++) {
            Operation o = theSchedule.get(i);
            if (o.action == 'w') {
                if (!firstReadS2.containsKey(o.getItem())) {
                    firstReadS2.put(o.getItem(), o.getTransaction());
                }
                int lastWrite = finalWriteS2.containsKey(o.getItem()) ? finalWriteS2.get(o.getItem()) : 0;
                updateS2.put(o, lastWrite);
            } else {
                finalWriteS2.put(o.getItem(), o.getTransaction());
            }
        }
        Iterator it = firstReadS1.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            if (firstReadS1.get(key) != firstReadS2.get(key)){
                String fr1 = "First read s1: " + firstReadS1 + "\n";
                String fr2 = "First read operations: " + firstReadS2 + "\n";
                String lw1 = "Last write s1: " + finalWriteS1 + "\n";
                String lw2 = "Last write operations: " + finalWriteS2 + "\n";
                return "false, \n" + fr1 + fr2 + lw1 + lw2;
            }

        }

        it = finalWriteS1.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next().toString();
            if (finalWriteS1.get(key) != finalWriteS2.get(key)) {
                String fr1 = "First read s1: " + firstReadS1 + "\n";
                String fr2 = "First read operations: " + firstReadS2 + "\n";
                String lw1 = "Last write s1: " + finalWriteS1 + "\n";
                String lw2 = "Last write operations: " + finalWriteS2 + "\n";
                return "false, \n" + fr1 + fr2 + lw1 + lw2;
            }

        }

        it = updateS1.keySet().iterator();
        while (it.hasNext()) {
            Operation key = (Operation) it.next();
            if (updateS1.get(key) != updateS2.get(key)) {
                String fr1 = "First read s1: " + firstReadS1 + "\n";
                String fr2 = "First read operations: " + firstReadS2 + "\n";
                String lw1 = "Last write s1: " + finalWriteS1 + "\n";
                String lw2 = "Last write operations: " + finalWriteS2 + "\n";
                return "false, \n" + fr1 + fr2 + lw1 + lw2;
            }

        }

        return "true";
    }


   
    public String precedenceGraph() {
        String result = "";
        for (OperationConflict o : conflictSerializableTest()) {
            result += o.fromOperation.getTransaction() + " -> " + o.toOperation.getTransaction() + "\n";
        }

        return result;
    }

    
    private HashSet<OperationConflict> conflictSerializableTest() {
        HashSet<String> results = new HashSet<String>();
        HashSet<OperationConflict> results2 = new HashSet<OperationConflict>();

        for (int i = 0; i < theSchedule.size(); i++) {

            Operation outerOperation = theSchedule.get(i);

            for (int j = 0; j < theSchedule.size(); j++) {
                Operation innerOperation = theSchedule.get(j);

                
                if ((outerOperation.getTransaction() == innerOperation.getTransaction())) {
                    continue;

                  
                } else if (outerOperation.getAction() == 'r' && innerOperation.getAction() == 'r') {
                    continue;
                   
                } else if (outerOperation.getItem() != innerOperation.getItem()) {
                    continue;
                }

                else {
                    
                    if (i < j) {
                        //result += outerOperation.getTransaction() + " -> " + innerOperation.getTransaction() + "\n";
                        results.add(outerOperation.getTransaction() + " -> " + innerOperation.getTransaction());
                        results2.add(new OperationConflict(outerOperation, innerOperation));
                        
                    } else if (i > j) {
                        //result += innerOperation.getTransaction() + " -> " + outerOperation.getTransaction() + "\n";
                        results.add(innerOperation.getTransaction() + " -> " + outerOperation.getTransaction());
                        results2.add(new OperationConflict(innerOperation, outerOperation));
                    } else {
                        System.out.println("Shouldn't be printer, ever.");
                    }
                }
            }
        }
        return results2;

    }
}

class Operation {
   
    char action;
    int transaction;
    char item;

    Operation(String action, int transaction, String item) {
        this.action = action.charAt(0); //convert string to char
        this.transaction = transaction;
        this.item = item.charAt(0); //convert string to char
    }

   
    public char getAction() {
        return action;
    }

   
    public int getTransaction() {
        return transaction;
    }

    
    public char getItem() {
        return item;
    }

    public String toString() {
        return String.valueOf(action) + transaction + String.valueOf(item);
    }

    
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + transaction;
        return result;
    }

    
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Operation other = (Operation) obj;
        if (transaction != other.transaction)
            return false;
        return true;
    }

}


class OperationConflict {
    Operation fromOperation;
    Operation toOperation;


    
    OperationConflict(Operation from, Operation to) {
        this.fromOperation = from;
        this.toOperation = to;
    }

    
    public Operation getFromOperation() {
        return fromOperation;
    }

   
    public Operation getToOperation() {
        return toOperation;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((fromOperation == null) ? 0 : fromOperation.hashCode());
        result = prime * result
                + ((toOperation == null) ? 0 : toOperation.hashCode());
        return result;
    }

   
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OperationConflict other = (OperationConflict) obj;
        if (fromOperation == null) {
            if (other.fromOperation != null)
                return false;
        } else if (!fromOperation.equals(other.fromOperation))
            return false;
        if (toOperation == null) {
            if (other.toOperation != null)
                return false;
        } else if (!toOperation.equals(other.toOperation))
            return false;
        return true;
    }
}


