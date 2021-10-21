package com.web.instafx.fileupload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerResponse {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("userdata")
    @Expose
    private UserData userdata;


    @SerializedName("kyc_info")
    @Expose
    private Kyc_info kyc_info;



    @SerializedName("token")
    @Expose
    private String token;


    @SerializedName("r_token")
    @Expose
    private String r_token;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserData getUserdata() {
        return userdata;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getR_token() {
        return r_token;
    }

    public void setR_token(String r_token) {
        this.r_token = r_token;
    }

    public void setUserdata(UserData userdata) {
        this.userdata = userdata;
    }


    public Kyc_info getKyc_info() {
        return kyc_info;
    }

    public void setKyc_info(Kyc_info kyc_info) {
        this.kyc_info = kyc_info;
    }

    public class UserData
    {
        @SerializedName("user_id")
        @Expose
        private String user_id;


        @SerializedName("profile_pic")
        @Expose
        private String profile_pic;

        @SerializedName("email")
        @Expose
        private String email;


        @SerializedName("first_name")
        @Expose
        private String first_name;


        @SerializedName("last_name")
        @Expose
        private String last_name;


        @SerializedName("mobile")
        @Expose
        private String mobile;


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }


    public  class  Kyc_info
    {
        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("address_f_status")
        @Expose
        private String address_f_status;


        @SerializedName("address_b_status")
        @Expose
        private String address_b_status;


        @SerializedName("pan_status")
        @Expose
        private String pan_status;


        public Kyc_info(String status, String address_f_status, String address_b_status, String pan_status) {
            this.status = status;
            this.address_f_status = address_f_status;
            this.address_b_status = address_b_status;
            this.pan_status = pan_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddress_f_status() {
            return address_f_status;
        }

        public void setAddress_f_status(String address_f_status) {
            this.address_f_status = address_f_status;
        }

        public String getAddress_b_status() {
            return address_b_status;
        }

        public void setAddress_b_status(String address_b_status) {
            this.address_b_status = address_b_status;
        }

        public String getPan_status() {
            return pan_status;
        }

        public void setPan_status(String pan_status) {
            this.pan_status = pan_status;
        }
    }

}



