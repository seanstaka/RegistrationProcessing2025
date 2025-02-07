import java.util.ArrayList;

public class MiddleSchool {
    private final static int STUDENT_INFO = 10;  //Number of slots for student's info before elective choices
    private final static int ID_INDEX = 4;
    private final static int NUM_ELECTIVE_CHOICES = 5;
    private static int numElectivesProcessed = 0;
    private static ArrayList<ActiveStudent> msActiveStudentList = new ArrayList<ActiveStudent>();
    private static ArrayList<Student> msStudents = new ArrayList<Student>();

    /**
     * Reads in the data from google form and adds elective requests to students
     * @param file String that represents tab delimited file of elective choices from Google Form
     */
    public static void processElectives(String file) {
        /*
        Input file is data from Google Form as a tab delimited file.
        May need to adjust code in importElectives() based on changes to the Google Form
         */
        MediaFile.setInputFile(file);
        MediaFile.readString();
        boolean hasNext = true;
        //String clear = MediaFile.readString();  //clear header row
        while(hasNext) {
        //for(int i = 0; i < 1; i++) {
            hasNext = importElectives();
        }
        System.out.println("Num MS Electives Processed: " + numElectivesProcessed);
        MediaFile.saveAndClose();
    }

    /**
     * Reads in tab delimited file of incoming 9th graders projected to school.
     * Creates ActiveStudent objects and populates msStudentList arrayList
     * @param file the tab delimited file that contains the list of project 9th graders
     */
    public static void msLoadStudents(String file) {
        /*List of projected 9th graders.
            Need HIDOE Custom Report Next Year Enrollment (must be in current year of IC)
            Copy data from second tab
            Delete any current high school students
         */
        MediaFile.setInputFile(file);

        String info = MediaFile.readString(); //clear header
        info = MediaFile.readString();
        while(info != null){
            String[] results = info.split("\t");
            msActiveStudentList.add(new ActiveStudent(results, false));
            info = MediaFile.readString();
        }
        System.out.println("MS STUDENTS LOADED: " + msActiveStudentList.size());
        MediaFile.saveAndClose();
    }

    /**
     *  Create msStudent objects and populates msStudents List, adding core classes to the students courseList
     * @param file the tab delimited file with core recommendations from middle school teachers
     *             Format: SID-sLast-sFirst-ELA-SS-Math-Sci-school
     */
    public static void coreRecommendations(String file) {
        MediaFile.setInputFile(file);
        System.out.println("FILE"+ file);
        String info = MediaFile.readString(); //clear header
        //System.out.println("CORE" + info);
        info = MediaFile.readString();
        //System.out.println("CORE" + info);
        while(info != null){
            //System.out.println("CORE INFO: " + info);
            String[] results = info.split("\t");
            msStudents.add(new MSStudent(results));
            info = MediaFile.readString();
        }
        System.out.println("MS Cores LOADED: " + msStudents.size());
        MediaFile.saveAndClose();
    }

    /**
     * Helper method for processElectives()
     * Adds PE and Health to a student's request based on summer school question
     * Adds electives to student's request based on google form
     * Marks extra classes (beyond 7 credits) as alternates
     * @return true
     */
    private static boolean importElectives() {

        String info = MediaFile.readString();
        //System.out.println("STUDENT INFO " + info);
        if(info == null) {
            return false;
        }
        numElectivesProcessed++;

        String[] results = info.split("\t");
        String[] studentInfo = new String[STUDENT_INFO + 1];  //+1 for the ge info at the end
        //System.out.println("RESULTS" + studentInfo.length + " " + results.length);

        //System.out.println("CHECKING STUDENT INFO READ");
        for(int i = 0; i < studentInfo.length; i++) {
            //System.out.println(results[i]);
            studentInfo[i] = results[i];
        }
        studentInfo[studentInfo.length-1] = results[16];
        //System.out.println("TESTING" + results[16] + studentInfo.length);
        Student applicant = findStudent(studentInfo[ID_INDEX]);
        if(applicant == null) {
            //System.out.println("ELECTIVES BUT NO CORE!!!!");
            //System.out.println(studentInfo[ID_INDEX]);
            applicant = new MSStudent(studentInfo, false);
        } else {
            applicant.updateInfo(studentInfo);
            School.cardProcessed(applicant);
        }

        //for(int i = 0; i < studentInfo.length; i++) {  System.out.println(studentInfo[i]);  }
        String[] electivesInfo  = new String[NUM_ELECTIVE_CHOICES+2];  // +2 to hold summer plans in first slot
                                                                        // and geType is last slot
        for(int i = 0; i < electivesInfo.length; i++) {
            electivesInfo[i] = results[STUDENT_INFO + i];
            //System.out.println("CHECK " + i + " " + electivesInfo[i]);
        }
        //for(int i = 0; i < electivesInfo.length; i++) {  System.out.println(electivesInfo[i]);  }

        //System.out.println("APPLICANT" + applicant);

        //Add PE and Health as Needed
        //System.out.println("PECHECK:"+electivesInfo[0]);
        String PE9ans = electivesInfo[0].substring(0,1);
        if(PE9ans.equalsIgnoreCase("\"")) {
            PE9ans = electivesInfo[0].substring(1,2);
        }
        String healthCombined = "HLE1000 - Health Today and Tomorrow";
        String PE9combined = "PEP1005 - Phys. Ed. Lifetime Fitness";
        //applicant.notePE9(PE9ans);

        //System.out.println(PE9ans + " : " + applicant);
        if(PE9ans.equalsIgnoreCase("N")) {
            applicant.setSummer("No");
            applicant.addCourse(new Course(PE9combined, "NONE", "0"));
            applicant.addCourse(new Course(healthCombined, "NONE", "0"));
        } else {
            applicant.setSummer("Yes");
        }
        /*
        //Used when all grades completed online form
        if(PE9ans.equalsIgnoreCase("A")) {
            applicant.addCourse(new Course(PE9combined, "NONE", "OTHER"));
            applicant.addCourse(new Course(healthCombined, "NONE", "OTHER"));
        } else if(PE9ans.equalsIgnoreCase("B")) {
            applicant.addCourse(new Course(PE9combined, "NONE", "OTHER"));
        } else if(PE9ans.equalsIgnoreCase("C")) {
            applicant.addCourse(new Course(healthCombined, "NONE", "OTHER"));
        }

         */
            /*
            if(applicant.doesNeedPE9()) {
                applicant.addCourse(new Course(PE9combined, "NONE", "OTHER"));
            }
            if(applicant.doesNeedHealth()) {
                applicant.addCourse(new Course(healthCombined, "NONE", "OTHER"));
            }
            */



        //Add Electives
        for(int i = 1; i <= NUM_ELECTIVE_CHOICES; i++) {
            //System.out.println("ELECTIVE INFO: " + electivesInfo[i]);
            applicant.addCourse(new Course(electivesInfo[i], "NONE", "NONE"));
        }

        //System.out.println("GE" + electivesInfo[electivesInfo.length-1]);
        //if(electivesInfo[electivesInfo.length-1].indexOf("Yes") != -1) {
            //System.out.println(applicant + " is a GE");
            applicant.setGE(electivesInfo[electivesInfo.length-1]);
        //}
        if(electivesInfo[electivesInfo.length-3].equalsIgnoreCase("Yes")) {
            applicant.setSummer(electivesInfo[electivesInfo.length-2]);
        }

        //applicant.printRequests();
        applicant.markAlternates();
        //applicant.printRequests();  //Make sure alternates aren't printed
        //applicant.exportRequests();
        return true;
    }

    /**
     * Find and return a student in msStudents based on the given ID numbers (as a String
     * @param id String that represents the students ID that needs to be found
     * @return Student object that matches the given ID
     */
    public static Student findStudent(String id) {
        for(Student s : msStudents) {
            if(s.getID().equals(id)){
                return s;
            }
        }
        return null;
    }

    /**
     * Provides of list of students that are projected to the high school, but did not complete the goole form
     * @param file String name of the file to be written
     */
    public static void exportMissing(String file) {
        MediaFile.setOutputFile(file);
        MediaFile.writeString("Student ID", false);
        MediaFile.writeString("Student Name", false);
        MediaFile.writeString("School", false);
        MediaFile.writeString("Notes", true);
        for(ActiveStudent s : msActiveStudentList) {
            if(!s.didSubmit()) {
                MediaFile.writeString(s.getID(), false);
                MediaFile.writeString(s.getName(), false);
                MediaFile.writeString(s.getTeacher(), true);
            }
        }
        MediaFile.saveAndClose();
    }

    /**
     * Add all the students msStudents that have not submitted the google form (they are not in processedStudents)
     * and add them to the processed students list.  Also adds PE and Health to their request list.
     */
    public static void processMissingRecommendations() {
        for(Student ms : msStudents) {
            boolean found = false;
            for(Student processed : School.getProcessedStudents()) {
                if(ms.getID().equals(processed.getID())) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                ms.addCourse(new Course("PEP1005 - Phys. Ed. Lifetime Fitness", "NONE", "0"));
                ms.addCourse(new Course("HLE1000 - Health Today and Tomorrow", "NONE", "0"));
                School.addProcessed(ms);
            }
        }
    }

    public static void printStudents() {
        for(Student s : msStudents) {
            System.out.println(s);
            //s.printRequests();
        }
    }

    public static ArrayList<ActiveStudent> getMsActiveStudentList() {
        return msActiveStudentList;
    }
}
