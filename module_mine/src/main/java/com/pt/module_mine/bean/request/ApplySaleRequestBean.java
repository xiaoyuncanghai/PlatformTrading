package com.pt.module_mine.bean.request;

public class ApplySaleRequestBean {

    /**
     * busiLicPic :
     * cardPic :
     * cardPic2 :
     * company :
     * uscc :
     * userQuality : 0
     */

    private String busiLicPic;
    private String company;
    private String uscc;
    private int userQuality;

    public String getBusiLicPic() {
        return busiLicPic;
    }

    public void setBusiLicPic(String busiLicPic) {
        this.busiLicPic = busiLicPic;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc;
    }

    public int getUserQuality() {
        return userQuality;
    }

    public void setUserQuality(int userQuality) {
        this.userQuality = userQuality;
    }
}
