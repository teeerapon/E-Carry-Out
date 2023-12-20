package com.sm.sdk.myapplication;

public class DataAssets {
    private String Description;
    private String Qty;
    private String serial_no;
    private String fixed_no;
    private String remark;

    private String AssetsData;

    public DataAssets(
            String Description,
            String Qty,
            String serial_no,
            String fixed_no,
            String remark,
            String AssetsData
    ) {
        this.Description = Description;
        this.Qty = Qty;
        this.serial_no = serial_no;
        this.fixed_no = fixed_no;
        this.remark = remark;
        this.AssetsData = AssetsData;
    }

    public String getAssetsData() {
        return AssetsData;
    }

    public void setAssetsData(String assetsData) {
        AssetsData = assetsData;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setFixed_no(String fixed_no) {
        this.fixed_no = fixed_no;
    }

    public String getFixed_no() {
        return fixed_no;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }
}
