public class ActiveStudent {
    private String id;
    private String firstName;
    private String lastName;
    private String capTeacher1;
    private String capTeacher2;
    private int grade;
    private boolean ell;
    private boolean sped;
    private boolean has504;
    private boolean submittedCard;
    
    public ActiveStudent(String[] info) {
        id = info[0];
        lastName = info[1];
        firstName = info[2];
        grade = Integer.valueOf(info[3]);
        capTeacher1 = info[4];
        submittedCard = false;
        ell = info[5].equals("1");
        sped = info[6].equals("1");
        has504 = info[7].equals("1");
    }

    //Constructor for incoming 9th grade students
    public ActiveStudent(String[] info, boolean hs) {
        id = info[0];
        lastName = info[1];
        firstName = info[2];
        grade = Integer.valueOf(info[3]);
        capTeacher1 = info[5];
        submittedCard = false;
    }
    
    public void addTeacher(String name) {
        if(!name.equalsIgnoreCase(capTeacher1)) {
            capTeacher2 = name;
        }
    }
    
    public String getID() {
        return id;
    }

    public String getSchool() {
        return capTeacher1;  //stores school for MS students
    }
    
    public String getName() {
        return lastName + ", " + firstName;
    }
    
    public String getTeacher() {
        if(capTeacher2 != null) {
            return capTeacher1 + " & " + capTeacher2;
        } else {
            return capTeacher1;
        }
    }
    
    public int getGrade() {
        return grade;
    }

    public boolean isEll() {
        return ell;
    }

    public boolean isSped() {
        return sped;
    }

    public boolean isHas504() {
        return has504;
    }

    public void setEll(boolean ell) {
        this.ell = ell;
    }

    public void setSped(boolean sped) {
        this.sped = sped;
    }

    public void setHas504(boolean has504) {
        this.has504 = has504;
    }

    public void submitted() {
        submittedCard = true;
    }
    
    public boolean didSubmit() {
        return submittedCard;
    }
    
    public String toString() {
        return id + " " + lastName;
    }
}