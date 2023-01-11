import java.util.ArrayList;

public class Student {
    private String submitDate;
    private String email;
    private String firstName;
    private String lastName;
    private String ID;
    private String phoneNumber;
    private String gradYear;
    private String counselor;
    private String school;
    private Parent parent;
    private ArrayList<Course> requestList;
    private boolean summerPlan = false;
    private String summerCourse = "";
    private boolean needPE9 = false;  //used?
    private boolean needHealth = false;  //used?
    private double activeCredits;
    private double altCredits;
    private boolean goodID = true;
    private boolean geoEx = false;
    private String geType = "";

    public Student(String[] info) {
        submitDate = info[0];
        email = info[1];
        firstName = info[2];
        lastName = info[3];
        ID = info[4];
        phoneNumber = info[5];
        school = info[7];
        if(gradYear == null) {
            gradYear = "2027";
        }
        counselor = info[8];
        parent = new Parent(info[9], info[10], info[11]);
        requestList = new ArrayList<Course>();
        geType = info[12];
        activeCredits = 0;
        School.cardProcessed(this);
    }

    //Constructor to create Middle School Students
    public Student(String id, String last, String first) {
        ID = id;
        firstName = first;
        lastName = last;
        school = "UNKNOWN";
        gradYear = "2027";
        requestList = new ArrayList<Course>();
        //School.cardProcessed(this);
        submitDate = "DID NOT SUBMIT FORM";
        email = "NO EMAIL";
        phoneNumber = "NO PHONE";
        counselor = "NONE";
        parent = new Parent("BLANK", "BLANK", "BLANK");

    }

    public void addCourse(Course request) {
        requestList.add(request);
    }

    public void updateInfo(String[] info) {
        submitDate = info[0];
        phoneNumber = info[5];
        school = info[7];
        counselor = info[8];
        parent = new Parent(info[9], info[10], info[11]);
        geType = info[12];
        activeCredits = 0;

    }

    //MAY NOT NEED
    public void notePE9(String choice) {
        if (choice.equalsIgnoreCase("A")) {
            needPE9 = true;
            needHealth = true;
        } else if (choice.equalsIgnoreCase("B")) {
            needPE9 = true;
        } else if (choice.equalsIgnoreCase("C")) {
            needHealth = true;
        }
    }

    public void markAlternates() {
        int numSemesters = 0;
        /*
        for (Course c : requestList) {
            numSemesters += c.getNumSem();
            if (numSemesters > 14) {
                c.makeAlt();
                altCredits += c.getNumSem();
                numSemesters -= c.getNumSem();
            }
        }
         */
        for (int i = 0; i < requestList.size(); i++) {
            if(requestList.get(i).getNumSem() < 0) {
                requestList.remove(i);
                i--;
            } else {
                numSemesters += requestList.get(i).getNumSem();
                if (numSemesters > 14) {
                    requestList.get(i).makeAlt();
                    altCredits += requestList.get(i).getNumSem();
                    numSemesters -= requestList.get(i).getNumSem();
                }
            }
        }
        activeCredits = numSemesters / 2.0;


    }

    public void printRequests() {
        for (Course c : requestList) {
            //if(!c.isAlternate()) {
            System.out.println(c);
            //}
        }
        System.out.println("-------------------------------------");
    }

    public void exportRequests() {
        for (Course c : requestList) {
            if (!c.isAlternate()) {
                countRequests(c.getACCN());
                MediaFile.writeString(ID, false);
                MediaFile.writeString(lastName + ", " + firstName, false);
                MediaFile.writeString(gradYear, false);
                MediaFile.writeString(c.getACCN(), false);
                MediaFile.writeString(c.getName(), false);
                MediaFile.writeString(c.getTeacher(), false);
                MediaFile.writeString(c.getPeriod(), true);
            } else {
                countAlternates(c.getACCN());
            }
        }
    }

    private void countRequests(String ACCN) {
        for (CourseMaster c : School.courseList) {
            if (c.getACCN().equalsIgnoreCase(ACCN)) {
                c.addRequest();
                break;
            }
        }
    }

    private void countAlternates(String ACCN) {
        for (CourseMaster c : School.courseList) {
            if (c.getACCN().equalsIgnoreCase(ACCN)) {
                c.addAlternate();
                break;
            }
        }
    }

    //MAY NOT NEED
    public boolean doesNeedPE9() {
        return needPE9;
    }

    public boolean doesNeedHealth() {
        return needHealth;
    }
    //////////////////

    //GETTERS
    public String getID() {
        return ID;
    }

    public double getActiveCredits() {
        double countCredits = 0;
        for(Course c : requestList) {
            if(!c.isAlternate())
                countCredits += c.getNumSem() / 2.0;
        }
        activeCredits = countCredits;
        return countCredits;
        //return activeCredits;
    }

    public double getAltCredits() {
        return altCredits;
    }

    public ArrayList<Course> getRequests() {
        return requestList;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGradYear() {
        return gradYear;
    }

    public String getCounselor() {
        return counselor;
    }

    public Parent getParent() {
        return parent;
    }

    public ArrayList<Course> getRequestList() {
        return requestList;
    }

    public boolean isGoodID() {
        return goodID;
    }

    public boolean isSummerPlan() {
        return summerPlan;
    }

    public String getSummerCourse() {
        return summerCourse;
    }

    public String getSchool() {
        return school;
    }



    //SETTERS
    public void setSummer(String plans) {
        summerPlan = true;
        summerCourse = plans;
    }

    public void setGE(String type) {
        geoEx = true;
        geType = type;
    }

    public String getGeType() {
        return geType;
    }

    public void badID() {
        this.goodID = false;
    }

    public String toString() {
        return lastName + "; " + firstName + " (" + ID + ")";
    }
}