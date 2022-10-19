public class CourseMaster {
    private String ACCN;
    private String courseName;
    private String department;
    private int numSem;
    private int numRequest;
    private int numAltRequest;
    
    public CourseMaster(String ACCN, String name, String numSem, String dept) {
        this.ACCN = ACCN;
        courseName = name;
        this.numSem = (int)(Double.valueOf(numSem)*2);
        department = dept;
    }
    
    public void addRequest(){  numRequest++;  }
    public void addAlternate() {  numAltRequest++; }
    
    public String getACCN() { return ACCN; }
    public String getCourseName() { return courseName; }
    public String getDept() { return department; }
    public int getNumSem() { return numSem; }
    public int getNumRequests()  { return numRequest;  }
    public int getNumAlternates() { return numAltRequest; }
    
    public String toString() {
        return ACCN + " " + courseName + " " + numSem;
    }
}