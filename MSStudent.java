public class MSStudent extends Student {
    //private String middleSchool;
    private boolean needCoreRecommendations = true;
    private boolean projected = false;

    public MSStudent(String[] info) {
        super(info[0], info[1], info[2]);
        if(info[3].length() > 0) {
            super.addCourse(new Course("LCY1010HNR - Honors English Grade 9", "NONE", "0"));
        } else {
            super.addCourse(new Course("LCY1010 - English Grade 9", "NONE", "0"));
        }
        if(info[4].length() > 0) {
            super.addCourse(new Course("CHR1100HNR - Honors Modern History of Hawaii", "NONE", "0"));
            super.addCourse(new Course("CGU1100HNR - Honors Participation in a Democracy", "NONE", "0"));
        } else {
            super.addCourse(new Course("CHR1100 - Modern History of Hawaii", "NONE", "0"));
            super.addCourse(new Course("CGU1100 - Participation in a Democracy", "NONE", "0"));
        }
        if(info[5].length() > 0) {
            super.addCourse(new Course("MGX1150 - Geometry", "NONE", "0"));
        } else {
            super.addCourse(new Course("MAX1155 - Algebra 1", "NONE", "0"));
        }
        if(info[6].length() > 0) {
            super.addCourse(new Course("SPH5603HNR - Honors Physics", "NONE", "0"));
        } else {
            super.addCourse(new Course("SPH5603 - Physics", "NONE", "0"));
        }
        //middleSchool = info[7];
        needCoreRecommendations = false;
        for(ActiveStudent s : MiddleSchool.getMsActiveStudentList()) {
            if(s.getID().equals(this.getID())) {
                projected = true;
                break;
            }
        }
    }

    public MSStudent(String[] info, boolean haveCore) {
        super(info);
        super.addCourse(new Course("LCY1010 - English Grade 9", "NONE", "0"));
        super.addCourse(new Course("CHR1100 - Modern History of Hawaii", "NONE", "0"));
        super.addCourse(new Course("CGU1100 - Participation in a Democracy", "NONE", "0"));
        super.addCourse(new Course("MAX1155 - Algebra 1", "NONE", "0"));
        super.addCourse(new Course("SPH5603 - Physics", "NONE", "0"));
        //super.addCourse(new Course("PEP1005 - Phys. Ed. Lifetime Fitness", "NONE", "0"));
        //super.addCourse(new Course("HLE1000 - Health Today and Tomorrow", "NONE", "0"));

        //Does this ever happen?  Projected but no core recommendation
        for(ActiveStudent s : MiddleSchool.getMsActiveStudentList()) {
            if(s.getID().equals(this.getID())) {
                projected = true;
                System.out.println("PROJECTED ---- CREATED in 2nd Constructor");
                break;
            }
        }
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
}
