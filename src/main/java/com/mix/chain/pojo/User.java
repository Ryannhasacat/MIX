package com.mix.chain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int userid;

    private String userName;

    private String pwd;

    private int age;

    private Boolean hasSession;

    private Love pet;

    public User(Builder builder){
        this.userid = builder.userid;
        this.age = builder.age;
        this.userName = builder.userName;
        this.pet = builder.pet;
        this.pwd = builder.pwd;
        this.hasSession = builder.hasSession;
    }

    public static class Builder{
        private int userid;

        private String userName;

        private String pwd;

        private int age;

        private Boolean hasSession;

        private Love pet;


        public Builder setUserid(int userid) {
            this.userid = userid;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setPwd(String pwd) {
            this.pwd = pwd;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setHasSession(Boolean hasSession) {
            this.hasSession = hasSession;
            return this;
        }

        public Builder setPet(Love pet) {
            this.pet = pet;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

}
