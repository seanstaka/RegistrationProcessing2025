public class Course {
    private String ACCN;
    private String name;
    private int numSem;
    //private int priority;
    private String currTeacher;
    private String currPeriod;
    private boolean alternate;
    private boolean goodACCN;

    public Course(String combined, String teacher, String period) {
        getCourseInfo(combined);
        currTeacher = teacher.trim();
        currPeriod = period.trim();
        /*
        if(!goodACCN) {
            System.out.println("BAD ACCN: " + ACCN);
        }
         */
    }

    public Course(String ACCN, String name, String teacher, String period) {
        getCourseInfo(ACCN.trim() + " - " + name);
        currTeacher = teacher;
        currPeriod = period;
        /*
        if(!goodACCN) {
            System.out.println("BAD ACCN: " + ACCN);
        }
         */

    }

    public boolean isAlternate() {  return alternate;  }

    public void makeAlt(Student s) { 
        for(CourseMaster cm : School.getCourseMaster()) {
            if(cm.getACCN().equals(ACCN)) {
                cm.addAlternate(s);
            }
        }
        this.alternate = true; 
    }

    //Looks through the course master arrayList for the ACCN for the actual title.
    //If the ACCN can't be found, use the title input by student
    private void getCourseInfo(String combined) {
        //System.out.println(combined);
        String splitter = " - ";
        boolean found = false;
        int index = combined.indexOf(splitter);
        ACCN = combined.substring(0, index);
        for(CourseMaster c : School.courseList) {
            if(c.getACCN().equalsIgnoreCase(ACCN)) {
                //System.out.println(ACCN);
                this.name = c.getCourseName();
                this.numSem = c.getNumSem();
                found = true;
                goodACCN = true;
                break;
            } 
            /*else {
                this.name = combined.substring(index+3);
                this.numSem = 2;
            }*/
        }
        if(!found) {
            //System.out.println("FOUND BAD ACCN");
            this.name = combined.substring(index+3);
            this.numSem = 2;
            goodACCN = false;
        }
    }

    public String getACCN() { return ACCN; }

    public String getName() { return name; }

    public String getTeacher() { return currTeacher; }

    public String getPeriod() { return currPeriod; }

    public int getNumSem() { return numSem; }
    public boolean isGoodACCN() { return goodACCN; }

    public String toString() {
        return ACCN + " " + name + currTeacher+numSem + "ALT"+ alternate;
    }
}