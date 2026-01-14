import java.util.ArrayList;

public class School {
    public static ArrayList<CourseMaster> courseList = new ArrayList<CourseMaster>();
    public static ArrayList<ActiveStudent> studentList = new ArrayList<ActiveStudent>();
    public static ArrayList<Student> processedStudents = new ArrayList<Student>();

    /**
     * Import master list of all courses offered
     * Input File needs: ACCN-title-#credits-term-dept
     */
    public static void importCourses() {
        MediaFile.setInputFile("InputFiles\\accnList9th.csv");
        String info = MediaFile.readString();
        while(info != null){
            String[] results = info.split(",");
            courseList.add(new CourseMaster(results[0], results[1], results[2], results[4]));
            info = MediaFile.readString();
        }
        MediaFile.saveAndClose();
    }

    /**
     * Load active current MoHS students, checks for duplicate students
     * Input file should have: id-first-last-grade-cap-el-sped-504 for each student
     */
    public static void loadStudents() {
        MediaFile.setInputFile("InputFiles\\studentMaster");
        String info = MediaFile.readString(); //clear header
        info = MediaFile.readString();
        while(info != null){
            String[] results = info.split("\t");
            if(!checkDuplicate(results[0], results[4])) {
                studentList.add(new ActiveStudent(results));
            }
            info = MediaFile.readString();
        }
        System.out.println("STUDENTS LOADED: " + studentList.size());
        MediaFile.saveAndClose();
    }

    private static boolean checkDuplicate(String id, String teacher) {
        for(ActiveStudent s : studentList) {
            if(s.getID().equals(id)) {
                s.addTeacher(teacher);
                return true;
            }
        }
        return false;
    }

    /**
     * Iterates through studentList of activeStudents, find Student s to mark as submitted
     * If student cannot be found, student find in middle school active list
     * If cannot be found in either, print ID as a bad ID
     * @param s the Student to be processed and marked as "submitted"
     */
    public static void cardProcessed(Student s) {
        boolean found = false;
        //Look for student in studentList (all active HS students)
        for(ActiveStudent active : studentList) {
            //System.out.println("s.getID() " + s.getID() + "\tid " + active.getID());
            if(active.getID().equals(s.getID())) {
                //System.out.println("FOUND" + s.getID());
                active.submitted();
                found = true;
                break;
            }
        }
        //If s cannot be found in studentList, look through msActiveStudentList
        if(!found) {
            for(ActiveStudent active : MiddleSchool.getMsActiveStudentList()) {
                if(active.getID().equals(s.getID())) {
                    //System.out.println("FOUND in MS" + s.getID());
                    //System.out.println("s.getID() " + active.getID() + "\t" + active.didSubmit());
                    active.submitted();
                    found = true;
                    //System.out.println("s.getID() " + active.getID() + "\t" + active.didSubmit());
                    break;
                }
            }
        }
        if(!found) {
            //System.out.println(s.getID() + " NOT found in Card Processed");
            s.badID();
        }
        processedStudents.add(s);
    }

    public static void exportMissing() {
        MediaFile.setOutputFile("missingCards");
        MediaFile.writeString("Student ID", false);
        MediaFile.writeString("Student Name", false);
        MediaFile.writeString("Student Grade", false);
        MediaFile.writeString("CAP Teacher", true);
        for(ActiveStudent s : studentList) {
            if(!s.didSubmit()) {
                MediaFile.writeString(s.getID(), false);
                MediaFile.writeString(s.getName(), false);
                MediaFile.writeString(String.valueOf(s.getGrade()), false);
                MediaFile.writeString(s.getTeacher(), true);
            }
        }
        MediaFile.saveAndClose();
    }

    public static void exportMissingSPED() {
        MediaFile.setOutputFile("missingCardsServices");
        for(ActiveStudent s : studentList) {
            if(!s.didSubmit() && s.isEll()) {
                MediaFile.writeString(s.getID(), false);
                MediaFile.writeString(s.getName(), false);
                MediaFile.writeString(String.valueOf(s.getGrade()), false);
                MediaFile.writeString(s.getTeacher(), false);
                MediaFile.writeString("EL", true);
            }
            if(!s.didSubmit() && s.isSped()) {
                MediaFile.writeString(s.getID(), false);
                MediaFile.writeString(s.getName(), false);
                MediaFile.writeString(String.valueOf(s.getGrade()), false);
                MediaFile.writeString(s.getTeacher(), false);
                MediaFile.writeString("SPED", true);
            }
        }
        MediaFile.saveAndClose();
    }

    public static void exportRequestCount() {
        MediaFile.setOutputFile("courseRequestCounts");
        MediaFile.writeString("ACCN", false);
        MediaFile.writeString("Course Name", false);
        MediaFile.writeString("Num Requests", false);
        MediaFile.writeString("Num GE Requests", false);
        MediaFile.writeString("Department", false);
        MediaFile.writeString("Num Alts", false);
        MediaFile.writeString("Num GE Alts", false);
        MediaFile.writeString("Num Culinary Alts", true);
        for(CourseMaster c : courseList) {
            MediaFile.writeString(c.getACCN(), false);
            MediaFile.writeString(c.getCourseName(), false);
            MediaFile.writeString(String.valueOf(c.getNumRequests()), false);
            MediaFile.writeString(String.valueOf(c.getNumRequestsGE()), false);
            MediaFile.writeString(c.getDept(), false);
            MediaFile.writeString(String.valueOf(c.getNumAlternates()), false);
            MediaFile.writeString(String.valueOf(c.getNumAlternatesGE()), false);
            MediaFile.writeString(String.valueOf(c.getCulinaryAlts()), true);
        }
        MediaFile.saveAndClose();
    }

    public static void exportAllClassRequest(String accn) {
        MediaFile.setOutputFile("listStudents" + accn);
        for(Student s : processedStudents) {
            ArrayList<Course> list = s.getRequestList();
            for(Course c : list) {
                if(c.getACCN().equals(accn)) {
                    System.out.println("NAME:" + s.getLastName());
                    MediaFile.writeString(c.getACCN(), false);
                    MediaFile.writeString(s.getID(), false);
                    MediaFile.writeString(s.getLastName(), false);
                    MediaFile.writeString(s.getFirstName(), false);
                    MediaFile.writeString(String.valueOf(((MSStudent)s).isProjected()), false);
                    MediaFile.writeString(String.valueOf(c.isAlternate()), false);
                    for(Course c2 : list) {
                        if(c2.isAlternate()) {
                            MediaFile.writeString(c2.getACCN(), false);
                        }
                    }
                    MediaFile.writeString("END", true);
                }
            }

        }
        MediaFile.saveAndClose();
        //System.out.println(processedStudents);
    }

    public static void shortClasses() {
            MediaFile.setOutputFile("shortClasses");
            for(Student s : processedStudents) {
                if(s.getActiveCredits() != 7) {
                    MediaFile.writeString(s.getID(), false);
                    MediaFile.writeString(s.getLastName() + ", " + s.getFirstName(), false);
                    MediaFile.writeString(String.valueOf(s.getActiveCredits()), false);
                    MediaFile.writeString(String.valueOf(s.getAltCredits()), true);
                }
            }
            MediaFile.saveAndClose();
    }

    public static void exportBadIDs() {
        MediaFile.setOutputFile("badIDnumbers");
        for(Student s : processedStudents) {
            if(!s.isGoodID()) {
                MediaFile.writeString(s.getID(), false);
                MediaFile.writeString(s.getLastName() + ", " + s.getFirstName(), false);
                MediaFile.writeString(String.valueOf(s.getGradYear()), false);
                MediaFile.writeString(s.getGeType(), true);
            }
        }
        MediaFile.saveAndClose();
    }

    public static void exportMergeData() {
        MediaFile.setOutputFile("dataForMerge");
        String[] headers = {"Date","SFirst","Slast","SID", "Team","School", "Sgrad","Couns","Pname","Pemail","Pphone",
                "EngACCN","EngCourse","EngTeach","Soc1ACCN","Soc1Course","Soc2ACCN","Soc2Course","SocTeach","MathACCN","MathCourse","MathTeach",
                "SciACCN","SciCourse","SciTeach", "PEACCN","PEName","PETeach","HlthACCN","HlthName","HlthTeach",
                "E1Code","E1Name","E1Teach","E2Code","E2Name","E2Teach","E3Code","E3Name","E3Teach","E4Code","E4Name","E4Teach",
                "E5Code","E5Name","E5Teach","E6Code","E6Name","E6Teach","E7Code","E7Name","E7Teach",
                "GeoEx","Credits","SumSchool","Proj", "needRec", "Notes"};
        for(int i = 0; i < headers.length; i++) {
            MediaFile.writeString(headers[i], false);
        }
        MediaFile.writeString("END", true);
        for(Student s : processedStudents) {
            MediaFile.writeString(s.getSubmitDate(), false);
            MediaFile.writeString(s.getFirstName(), false);
            MediaFile.writeString(s.getLastName(), false);
            MediaFile.writeString(s.getID(), false);
            MediaFile.writeString(((MSStudent)s).getTeam(), false);
            MediaFile.writeString(s.getSchool()+"", false);
            MediaFile.writeString(s.getGradYear(), false);
            //System.out.println(s.getPhoneNumber() + "|"+ s.getCounselor());
            MediaFile.writeString(s.getCounselor(), false);
            Parent p = s.getParent();
            MediaFile.writeString(p.getName(), false);
            MediaFile.writeString(p.getEmail(), false);
            MediaFile.writeString(p.getPhoneNumber(), false);
            ArrayList<Course> cList = s.getRequestList();
            int numCourses = 0;
            for(int courseCount = 0; courseCount < cList.size(); courseCount++) {
                Course c = cList.get(courseCount);
                if (courseCount == 1) { //Social Studies block
                    MediaFile.writeString(c.getACCN(), false);
                    MediaFile.writeString(c.getName(), false);
                    if (c.getNumSem() == 1) {
                        courseCount++;
                        c = cList.get(courseCount);
                        MediaFile.writeString(c.getACCN(), false);
                        MediaFile.writeString(c.getName(), false);
                    } else {
                        MediaFile.writeString(" ", false);
                        MediaFile.writeString(" ", false);
                    }
                    MediaFile.writeString(c.getTeacher(), false);
                } else {
                    MediaFile.writeString(c.getACCN(), false);
                    MediaFile.writeString(c.getName(), false);
                    MediaFile.writeString(c.getTeacher(), false);
                }
                numCourses++;
                if(numCourses > 12) { //12 courses to make spreadsheet end nicely
                    break;
                }
            }
            for(int i = 0; i < 13 - numCourses; i++) {
                MediaFile.writeString(" ", false);
                MediaFile.writeString(" ", false);
                MediaFile.writeString(" ", false);
            }
            MediaFile.writeString(s.getGeType(), false);
            MediaFile.writeString(String.valueOf(s.getActiveCredits()), false);
            MediaFile.writeString(s.getSummerCourse(), false);
            MediaFile.writeString(""+((MSStudent)s).isProjected(), false);

            if(s.getGradYear().equals("2029")) {
                MediaFile.writeString(""+((MSStudent)s).needRecommendations(), false);
            }
            MediaFile.writeString(""+((MSStudent)s).getNotes(), false);
            MediaFile.writeString("END", true);
        }
        System.out.println("Num Exported to print: " + processedStudents.size());
        MediaFile.saveAndClose();
    }

    public static void printStudent(String id) {
        for(Student s : processedStudents) {
            if(id.equals(s.getID())) {
                s.printRequests();
                return;
            }
        }
        System.out.println("Student not found");
    }

    public static void exportBadACCN() {
        MediaFile.setOutputFile("badACCN");
        MediaFile.writeString("Student ID", "\t", false);
        MediaFile.writeString("Last Name", "\t", false);
        MediaFile.writeString("Bad ACCN", "\t", false);
        MediaFile.writeString("Course Name", "\t", true);
        for(Student s : processedStudents) {
            for(Course c : s.getRequests()) {
                if(!c.isGoodACCN()) {
                    MediaFile.writeString(s.getID(), "\t", false);
                    MediaFile.writeString(s.getLastName(), "\t", false);
                    MediaFile.writeString(c.getACCN(), "\t", false);
                    MediaFile.writeString(c.getName(), "\t", true);
                }
            }
        }
        MediaFile.saveAndClose();
    }

    /**
     * This method goes through the student list, checks if they have requested culinary
     * as a top choice.  If it does, count their first alternate
     */
    public static void markCulinaryAlt() {
        for(Student s : processedStudents) {
            s.markCulinaryAlt();
        }
    }

    public static ArrayList<Student> getProcessedStudents() {
        return processedStudents;
    }

    public static void addProcessed(Student s) {
        processedStudents.add(s);
    }

    public static ArrayList<CourseMaster> getCourseMaster() {
        return courseList;
    }
}