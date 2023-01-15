public class CourseMaster {
    private String ACCN;
    private String courseName;
    private String department;
    private int numSem;
    private int numRequest;   //counts number of requests for students in district
    private int numRequestGE;  //counts number of requests for potential GE students
    private int numAltRequest;  //counts number of alt requests for students in district
    private int numAltRequestGE;  //counts number of alt request for potential GE students
    private int numCulinaryAlt;  //counts number of times course is first alt of students that requested culinary
    
    public CourseMaster(String ACCN, String name, String numSem, String dept) {
        this.ACCN = ACCN;
        courseName = name;
        this.numSem = (int)(Double.valueOf(numSem)*2);
        department = dept;
    }
    
    public void addRequest(Student s){  
        if(((MSStudent)s).isProjected()) {
            numRequest++;
        } else {
            numRequestGE++;
        }
        //System.out.println("REQUEST ADDED");
    }

    public void addAlternate(Student s) {  
        if(((MSStudent)s).isProjected()) {
            numRequest--;
            numAltRequest++; 
        } else {
            numRequestGE--;
            numAltRequestGE++; 
        }
    }

    public void countCulinaryAlt() {
        numCulinaryAlt++;
        System.out.println("CULINARY ALT: " + ACCN);
    }
    
    public String getACCN() { return ACCN; }
    public String getCourseName() { return courseName; }
    public String getDept() { return department; }
    public int getNumSem() { return numSem; }
    public int getNumRequests()  { return numRequest;  }
    public int getNumRequestsGE() { return numRequestGE; }
    public int getNumAlternates() { return numAltRequest; }
    public int getNumAlternatesGE() { return numAltRequestGE; }
    public int getCulinaryAlts() { return numCulinaryAlt; }
    
    public String toString() {
        return ACCN + " " + courseName + " " + numSem;
    }
}