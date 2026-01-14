public class MSStudent extends Student {
    //private String middleSchool;
    private boolean needCoreRecommendations = true;
    private boolean projected = false;

    //instance variables added SY'25'26
    private String team = " ";
    private String notes = "";

    /**
     * Data format SY2526: sID|sLast|sFirst|Gender|Team|advTeacher|room|ELA|SS|Math|Sci|Note
     * Core recommendations start with H (honors), N (inclusion), S (resource), J (ELL)
     * Math recommendations also include Geom, AlgII, Math Workshop (in addition to Alg 1)
     * @param info comma separate String read from middle school recommendation template
     */
    public MSStudent(String[] info) {
        super(info[0], info[1], info[2], info[5]);
        team = info[4];
        needCoreRecommendations = false;
        for(ActiveStudent s : MiddleSchool.getMsActiveStudentList()) {
            if(s.getID().equals(this.getID())) {
                projected = true;
                break;
            }
        }

        //English Recommendation
        if(info[7].indexOf("H") == 0) {
            super.addCourse(new Course("LCY1010HNR - English Grade 9 (HNR)", "NONE", "0"));
        } else if(info[7].indexOf("N") == 0) {
            super.addCourse(new Course("LCY1010NT - English Grade 9 (NT)", "NONE", "0"));
        } else if(info[7].indexOf("S") == 0) {
            super.addCourse(new Course("LCY1010S - English Grade 9 (S)", "NONE", "0"));
        } else if(info[7].indexOf("J") == 0) {
            super.addCourse(new Course("LCY1010J - English Grade 9 (EL)", "NONE", "0"));
        } else {
            super.addCourse(new Course("LCY1010 - English Grade 9", "NONE", "0"));
        }

        //Social Studies Recommendation
        if(info[8].indexOf("H") == 0) {
            super.addCourse(new Course("CHR1100HNR - Modern History of Hawaii (HNR)", "NONE", "0"));
            super.addCourse(new Course("CGU1100HNR - Participation in a Democracy (HNR)", "NONE", "0"));
        } else if(info[8].indexOf("N") == 0) {
            super.addCourse(new Course("CHR1100NT - Modern History of Hawaii (NT)", "NONE", "0"));
            super.addCourse(new Course("CGU1100NT - Participation in a Democracy (NT)", "NONE", "0"));
        } else if(info[8].indexOf("S") == 0) {
            super.addCourse(new Course("CHR1100S - Modern History of Hawaii (S)", "NONE", "0"));
            super.addCourse(new Course("CGU1100S - Participation in a Democracy (S)", "NONE", "0"));
        } else if(info[8].indexOf("J") == 0) {
            super.addCourse(new Course("CHR1100J - Modern History of Hawaii (EL)", "NONE", "0"));
            super.addCourse(new Course("CGU1100J - Participation in a Democracy (EL)", "NONE", "0"));
        } else {
            super.addCourse(new Course("CHR1100 - Modern History of Hawaii", "NONE", "0"));
            super.addCourse(new Course("CGU1100 - Participation in a Democracy", "NONE", "0"));
        }

        //Math Recommendation
        if(info[9].indexOf("Geom") != -1) {
            super.addCourse(new Course("MGX1150 - Geometry", "NONE", "0"));
        } else if(info[9].indexOf("Alg II") != -1) {
            super.addCourse(new Course("MAX1200 - Algebra 2", "NONE", "0"));
        } else if(info[9].indexOf("Workshop") != -1) {
            super.addCourse(new Course("MAX1155 - Algebra 1", "NONE", "0"));
            //super.addCourse(new Course("MSW11011S - Math Workshop 9A", "NONE", "0"));
            notes = "Teacher recommends math workshop                                  ";
        } else if(info[9].indexOf("N") == 0) {
            super.addCourse(new Course("MAX1155NT - Algebra 1 (NT)", "NONE", "0"));
        } else if(info[9].indexOf("S") == 0) {
            super.addCourse(new Course("MAX1155S - Algebra 1 (S)", "NONE", "0"));
        } else {
            super.addCourse(new Course("MAX1155 - Algebra 1", "NONE", "0"));
        }

        //Science Recommendation
        if(info[10].indexOf("N") == 0) {
            super.addCourse(new Course("SPH5603NT - Physics (NT)", "NONE", "0"));
        } else if(info[10].indexOf("S") == 0) {
            System.out.println(this.getLastName());
            super.addCourse(new Course("SPH5603S - Physics (S)", "NONE", "0"));
        } else {
            super.addCourse(new Course("SPH5603 - Physics", "NONE", "0"));
        }

        notes += info[11];
        //middleSchool = info[7];
    }

    public MSStudent(String[] info, boolean haveCore) {
        super(info);
        //Does this ever happen?  Projected but no core recommendation
        for(ActiveStudent s : MiddleSchool.getMsActiveStudentList()) {
            if(s.getID().equals(this.getID())) {
                projected = true;
                //System.out.println("PROJECTED ---- CREATED in 2nd Constructor");
                break;
            }
        }
        super.addCourse(new Course("LCY1010 - English Grade 9", "NONE", "0"));
        super.addCourse(new Course("CHR1100 - Modern History of Hawaii", "NONE", "0"));
        super.addCourse(new Course("CGU1100 - Participation in a Democracy", "NONE", "0"));
        super.addCourse(new Course("MAX1155 - Algebra 1", "NONE", "0"));
        super.addCourse(new Course("SPH5603 - Physics", "NONE", "0"));
        //super.addCourse(new Course("PEP1005 - Phys. Ed. Lifetime Fitness", "NONE", "0"));
        //super.addCourse(new Course("HLE1000 - Health Today and Tomorrow", "NONE", "0"));
    }

    public boolean needRecommendations() {
        return needCoreRecommendations;
    }

    public void setProjected() {
        projected = true;
    }

    public boolean isProjected() {
        return projected;
    }

    public String getTeam() {
        return team;
    }

    public String getNotes() {
        return notes;
    }
}
