package com.example.medicine;

    public class DataType_Post {
        int user_id, id;
        String title, body;
        boolean isExpand;

        public DataType_Post() {
        }

        public DataType_Post(int user_id, String title, String body) {
            //this.user_id = user_id;
            this.id = id;
            this.title = title;
            this.body = body;
            isExpand = false;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public boolean isExpand() {
            return isExpand;
        }

        public void setExpand(boolean expand) {
            isExpand = expand;
        }
    }
