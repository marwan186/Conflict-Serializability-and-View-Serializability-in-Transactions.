package com;



public class SerializationChecker {

    private Schedule schedule;


    public void populateSchedule(String ...schedule){
        this.schedule = new Schedule(schedule);
    }


    public String getPrecedenceGraph(){
        return schedule.precedenceGraph();
    }

    public String getConflictSerializable(){
        return schedule.conflictSerializableSolution();
    }

    public String getViewSerializable(){
        return "The schedule is view serializable: " + schedule.sameResult(schedule);
    }

    public String getSchedule(){
        return schedule.getSchedule();
    }






}
