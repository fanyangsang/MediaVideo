package com.zut.lpb.bean;

public class UserBean {

    /**
     * data : {"id":9,"phone":"15355041963","password":"19772a06f7320c2af28ee03a0ca55345","nickname":"安东尼","age":0,"email":null,"sex":null}
     * code : 200
     * msg : 操作成功！
     */

    private DataBean data;
    private String code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * id : 9
         * phone : 15355041963
         * password : 19772a06f7320c2af28ee03a0ca55345
         * nickname : 安东尼
         * age : 0
         * email : null
         * sex : null
         */

        private String token;
        private int id;
        private String phone;
        private String password;
        private String nickname;
        private int age;
        private String email;
        private String sex;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
