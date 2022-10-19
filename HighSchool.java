public class HighSchool {
        /*
        For high school students?
    */
    public static boolean studentInfo() {
        String info = MediaFile.readString();
        //System.out.println("STUDENT INFO " + info);
        if(info == null) {
            return false;
        }
        Processing.numProcessed++;


        String[] results = info.split("\t", Processing.STUDENT_INFO+1);
        String[] studentInfo = new String[Processing.STUDENT_INFO];
        String[] coreInfo = new String[Processing.CORE_INFO];
        for(int i = 0; i < studentInfo.length; i++) {
            studentInfo[i] = results[i];
        }
        //System.out.println(results[Processing.STUDENT_INFO]);
        String[] requestInfo = results[Processing.STUDENT_INFO].split("\t",Processing.CORE_INFO+1);
        for(int i = 0; i < coreInfo.length; i++) {
            coreInfo[i] = requestInfo[i];
        }
        //for(int i = 0; i < coreInfo.length; i++) {  System.out.println(coreInfo[i]);  }
        String[] electivesInfo  = requestInfo[Processing.CORE_INFO].split("\t");
        //for(int i = 0; i < electivesInfo.length; i++) {  System.out.println(electivesInfo[i]);  }

        Student applicant = new Student(studentInfo);
        //System.out.println(applicant);

        //Add English Request
        if(!coreInfo[0].equalsIgnoreCase("none")) {
            applicant.addCourse(new Course(coreInfo[0], coreInfo[1], coreInfo[2]));
        } else {
            applicant.addCourse(new Course("NONE - NONE", coreInfo[1], coreInfo[2]));
        }

        //Add Social Studies Request
        if(!coreInfo[3].equalsIgnoreCase("none")) {
            Course ssClass = new Course(coreInfo[3], coreInfo[5], coreInfo[6]);
            applicant.addCourse(ssClass);
            //System.out.println(coreInfo[4]);
            if (ssClass.getNumSem() < 2) {
                if (!coreInfo[4].equalsIgnoreCase("none")) {
                    applicant.addCourse(new Course(coreInfo[4], coreInfo[5], coreInfo[6]));
                } else {
                    applicant.addCourse(new Course("NONE - NONE", coreInfo[5], coreInfo[6]));
                }
            }
        } else {
            applicant.addCourse(new Course("NONE - NONE", coreInfo[5], coreInfo[6]));
        }

        //Add Math Request
        if(!coreInfo[7].equalsIgnoreCase("none")) {
            applicant.addCourse(new Course(coreInfo[7], coreInfo[8], coreInfo[9]));
        } else {
            applicant.addCourse(new Course("NONE - NONE", coreInfo[8], coreInfo[9]));
        }

        //Add Science Request
        if(!coreInfo[10].equalsIgnoreCase("none")) {
            applicant.addCourse(new Course(coreInfo[10], coreInfo[11], coreInfo[12]));
        } else {
            applicant.addCourse(new Course("NONE - NONE", coreInfo[11], coreInfo[12]));
        }

        //Add PE and Health as Needed
        String PE9ans = coreInfo[13].substring(0,1);
        String PE10ans = coreInfo[14].substring(0,1);
        String healthCombined = "HLE1000 - Health Today and Tomorrow";
        String PE9combined = "PEP1005 - Phys. Ed. Lifetime Fitness";
        String PE10combined = "PEP1010 - Phys. Ed. Lifetime Activities";
        //applicant.notePE9(PE9ans);

        boolean addedSemElect = false;
        boolean addSemElectAtEnd = false;
        //System.out.println(PE9ans + " " + PE10ans + " : "  + applicant);
        if(PE10ans.equalsIgnoreCase("A")) {
            applicant.addCourse(new Course(PE10combined, "NONE", "OTHER"));  //Add PE10 for anyone that needs it
            if(PE9ans.equalsIgnoreCase("A") || PE9ans.equalsIgnoreCase("C")) {  //If they need health, add it)
                applicant.addCourse(new Course(healthCombined, "NONE", "OTHER"));
                addSemElectAtEnd = true; //Added PE10, but not sem elect
                //System.out.println("PE10HEALTH: " + applicant);
            } else if(PE9ans.equalsIgnoreCase("B")) {
                applicant.addCourse(new Course(PE9combined, "NONE", "OTHER"));
            } else {
                addedSemElect = true;
                applicant.addCourse(new Course(electivesInfo[0], electivesInfo[2], electivesInfo[3]));  //Otherwise, add the sem elective
            }
            if(applicant.getGradYear().equals("2022")) { //If they're a senior
                if(PE9ans.equalsIgnoreCase("A") || PE9ans.equalsIgnoreCase("B")) {  ////and need PE9, add it
                    applicant.addCourse(new Course(healthCombined, "NONE", "OTHER"));
                    //System.out.println("andPE9: " + applicant);
                    if(!addedSemElect) { //If didn't add sem elect
                        applicant.addCourse(new Course(electivesInfo[0], electivesInfo[2], electivesInfo[3]));  //add the sem elective
                        addSemElectAtEnd = false;  //don't add sem elect again
                        //System.out.println("andELECT: " + applicant);
                    }
                }
            }
        } else {
            if(PE9ans.equalsIgnoreCase("A")) {
                applicant.addCourse(new Course(PE9combined, "NONE", "OTHER"));
                applicant.addCourse(new Course(healthCombined, "NONE", "OTHER"));
            } else if(PE9ans.equalsIgnoreCase("B")) {
                applicant.addCourse(new Course(PE9combined, "NONE", "OTHER"));
            } else if(PE9ans.equalsIgnoreCase("C")) {
                applicant.addCourse(new Course(healthCombined, "NONE", "OTHER"));
            }
            /*
            if(applicant.doesNeedPE9()) {
                applicant.addCourse(new Course(PE9combined, "NONE", "OTHER"));
            }
            if(applicant.doesNeedHealth()) {
                applicant.addCourse(new Course(healthCombined, "NONE", "OTHER"));
            }
            */

        }

        //Add Electives
        int electNumber = 1;
        for(int i = 4; i < electivesInfo.length-4; i += 4) {
            if(electNumber > 4) {
                i++;  //skip over the add more electives question
            }
            if(electivesInfo[i].length() > 0) {
                String checkACCN = electivesInfo[i];  //Get the ACCN from the elective
                String ACCN2 = null;
                int index = checkACCN.indexOf("/");  //See if there is a slash in the ACCN
                if(index != -1) {
                    //System.out.println("SPLIT ACCN");
                    ACCN2 = checkACCN.substring(index+1).trim();
                    checkACCN = checkACCN.substring(0, index).trim();
                    applicant.addCourse(new Course(checkACCN, electivesInfo[i + 1], electivesInfo[i + 2], electivesInfo[i + 3]));
                    applicant.addCourse(new Course(ACCN2, electivesInfo[i + 1], electivesInfo[i + 2], electivesInfo[i + 3]));
                } else {
                    applicant.addCourse(new Course(checkACCN, electivesInfo[i + 1], electivesInfo[i + 2], electivesInfo[i + 3]));
                }
            }
            electNumber++;
        }

        if(addSemElectAtEnd) {
            applicant.addCourse(new Course(electivesInfo[0], electivesInfo[2], electivesInfo[3]));
        }

        if(electivesInfo[electivesInfo.length-3].equalsIgnoreCase("Yes")) {
            applicant.setSummer(electivesInfo[electivesInfo.length-2]);
        }

        //applicant.printRequests();
        applicant.markAlternates();
        //applicant.printRequests();  //Make sure alternates aren't printed
        //applicant.exportRequests();
        return true;
    }
}
