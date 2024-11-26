import java.util.Scanner;

/////////////////////////////////////////////////////////////////////////////////////
/*
 *   Possible Improvments:
 *    - Check for multiple world languages
 *    - Check Algebra 2 vs Geometry
 *    - Check for US History
 *    - Add 5th choice to Google Form
 */
/////////////////////////////////////////////////////////////////////////////////////

public class Processing {
    public final static int STUDENT_INFO = 12;
    public final static int CORE_INFO = 15;
    public static int numProcessed = 0;

    public static void main(String[] args) {
        System.out.println("CHANGE MADE TO 2025");
        System.out.println("Change made again after renaming");
        System.out.println("Change made again after again 2025");
        School.importCourses();
        //School.loadStudents();

        /*
         * incomingMSstudent is tab delimited version of HIDOE-Next Year Enrollment report
         * Need to reformat to delete extra data
         * Leave headers in data file as follows
         * sID-sLast-sFirst-nextGrade-GE-School
         */
        MiddleSchool.msLoadStudents("InputFiles\\incomingMSstudents");

        /*
         * Create spreadsheet based on middle school teacher recommendations
         * Leave headers in data file as follows:
         *   SID-sLast-sFirst-ELA-SS-Math-Sci-School
         */
        MiddleSchool.coreRecommendations("InputFiles\\msCoreClasses"); 

        /*
         * msElectiveChoices tab delimited data from Google Form
         * delete row of headers in data file
         * data should match following order:
         * date-sEmail-sFirst-sLast-sID-sPhone-grade-prevSchool-BLANK-pName-pEmail-pPhone-summer-elect1-elect2-elect3-elect4
         */
        MiddleSchool.processElectives("InputFiles\\msElectiveChoices");
        School.markCulinaryAlt();

        //MiddleSchool.markProjected();
        MiddleSchool.processMissingRecommendations();
        //MediaFile.setInputFile("InputFiles\\registrationData");
        MediaFile.setOutputFile("studentRequests");
        MediaFile.writeString("Student ID", false);
        MediaFile.writeString("Last Name", false);
        MediaFile.writeString("First Name", false);
        MediaFile.writeString("ACCN", false);
        MediaFile.writeString("Course", false);
        MediaFile.writeString("Teacher", false);
        MediaFile.writeString("Period", true);

/*
        //Read all lines in registrationData
        boolean hasNext = true;
        //String clear = MediaFile.readString();  //clear header row
        while(hasNext) {
        //for(int i = 0; i < 1; i++) {
            hasNext = HighSchool.studentInfo();
        }
        */
        MediaFile.saveAndClose();

        System.out.println("Number of Cards Processed: " + numProcessed);
        exportFiles();
        infoLookup();
        System.out.println("Program Ended");
    }



    private static void infoLookup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1) Student Lookup or 2) Export Class List (type n to end)");
        String input = sc.nextLine();
        while(input.indexOf("n") == -1) {
            if(input.equals("1")) {
                studentLookup();
            } else if(input.equals("2")) {
                exportClass();
            } else {
                System.out.println("Enter a either 1 or 2");
            }
            System.out.println("1) Student Lookup or 2) Export Class List");
            input = sc.next();
        }
        //sc.close();
    }

    public static void studentLookup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Student ID");
        String input = sc.next();
        while(input.indexOf("n") == -1) {
            School.printStudent(input);
            System.out.println("Enter Student ID");
            input = sc.next();
        }
        //sc.close();
    }

    private static void exportFiles() {
        /*
        boolean invalid = true;
        do {
            invalid = true;
            int input = -1;
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Export Files? 1)Yes 2)No");
                input = sc.nextInt();
                sc.close();
            } catch (Exception e) {
                invalid = false;
                System.out.println("Please enter a number.");
            }
            if (input == 1) {
                School.exportMissing();
                MiddleSchool.exportMissing("missingCardsMS");
                School.exportMissingSPED();
                School.exportRequestCount();
                School.shortClasses();
                School.exportBadACCN();
                School.exportBadIDs();
                School.exportMergeData();
            }
        } while(!invalid);
        */
        int input = -1;
        Scanner sc = new Scanner(System.in);
        System.out.println("Export Files? 1)Yes 2)No");
        input = sc.nextInt();
        //sc.close();
        if (input == 1) {
            School.exportMissing();
            MiddleSchool.exportMissing("missingCardsMS");
            School.exportMissingSPED();
            School.exportRequestCount();
            //School.shortClasses();
            School.exportBadACCN();
            School.exportBadIDs();
            School.exportMergeData();
        }
    }

    private static void exportClass() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Course ACCN");
        String input = sc.next();
        while(input.indexOf("n") == -1) {
            School.exportAllClassRequest(input);
            System.out.println("Enter Course ACCN");
            input = sc.next();
        }
        //sc.close();
    }
}