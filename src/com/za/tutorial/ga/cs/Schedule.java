package com.za.tutorial.ga.cs;

import java.util.ArrayList;
import java.util.ArrayList;

public class Schedule {
    private ArrayList<Class> classes;
    private int classNumb=0;
    private int numOfConflicts=0;
    private boolean isFitnessChanged = true;
    private double fitness = -1 ;
    private Data data;
    public Data getData() {
        return data;
    }

    public Schedule(Data data) {
        this.data = data;
        classes = new ArrayList<Class>(data.getNumberOfClasses());
    }

    public Schedule initialize(){
        new ArrayList<Department>(data.getDepts()).forEach(dept -> {
            dept.getCourses().forEach(course -> {
                Class newClass = new Class(classNumb++,dept,course);
                newClass.setMeetingTime(data.getMeetingTime().get((int) (data.getMeetingtime().size() * Math.random())));
                newClass.setRoom(data.getRooms().get((int) (data.getRooms().size() * Math.random())));
                newClass.setInstructor((course.getInstructor().get((int)(course.getInstructors().size() * Math.random()))));
                classes.add(newClass);
            });
        });
        return this;
    }
    public int getNumOfConflicts() {
        return numOfConflicts;
    }
    public double getFitness(){
        if (isFitnessChanged == true){
            fitness = calculateFitness();
            isFitnessChanged = false;
        }
        return fitness;
    }

    private double calculateFitness(){
        numOfConflicts=0;
        classes.forEach(x -> {
            if (x.getRoom().getSeatingCapacity() < x.getCourse().getMaxNumOfStudents()) numOfConflicts++;
            classes.stream().filter(y -> classes.indexOf(y) >= classes.indexOf(x)).forEach(y -> {
                if (x.getMeetingTime() == y.getMeetingTime() && x.getId() != y.getId()){
                    if (x.getRoom() == y.getRoom()) numOfConflicts++;
                    if (x.getInstructor() == y.getInstructor()) numOfConflicts++;
                }
            });
        });
        return 1/(double)(numOfConflicts+1);

    }

    public String toString(){
        String returnValue = new String();
        for (int x= 0 ; x < classes.size() -1; x++) returnValue += classes.get(x)+",";
        returnValue += classes.get(classes.size() -1);
        return returnValue;
    }
}
