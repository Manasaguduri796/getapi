package org.sanketika.springbootproject1.response;
public class SimpleResponse {
        private String id;
        private String message;

        public SimpleResponse(String id, String message) {
            this.id = id;
            this.message = message;
        }

        // Getters and setters (or use Lombok @Data annotation)
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


