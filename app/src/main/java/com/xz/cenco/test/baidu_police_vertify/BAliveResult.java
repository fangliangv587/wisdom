package com.xz.cenco.test.baidu_police_vertify;

import java.util.List;

/**
 * Created by Administrator on 2018/4/21.
 */

public class BAliveResult {


    /**
     * log_id : 1900901488032821
     * result_num : 1
     * result : [{"rotation_angle":10,"yaw":11.357421875,"faceliveness":8.1253347161692E-5,"location":{"width":96,"top":73,"height":96,"left":283},"qualities":{"illumination":211,"occlusion":{"right_eye":0,"left_eye":0.039751552045345,"left_cheek":0.12623985111713,"mouth":0,"nose":0,"chin":0.037661049515009,"right_cheek":0.024720622226596},"completeness":1,"type":{"cartoon":0,"human":0},"blur":2.5251445032182E-11},"pitch":1.0063140392303,"roll":12.760620117188,"face_probability":1}]
     */

    private long log_id;
    private int result_num;
    private List<ResultBean> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * rotation_angle : 10
         * yaw : 11.357421875
         * faceliveness : 8.1253347161692E-5
         * location : {"width":96,"top":73,"height":96,"left":283}
         * qualities : {"illumination":211,"occlusion":{"right_eye":0,"left_eye":0.039751552045345,"left_cheek":0.12623985111713,"mouth":0,"nose":0,"chin":0.037661049515009,"right_cheek":0.024720622226596},"completeness":1,"type":{"cartoon":0,"human":0},"blur":2.5251445032182E-11}
         * pitch : 1.0063140392303
         * roll : 12.760620117188
         * face_probability : 1
         */

        private int rotation_angle;
        private double yaw;
        private double faceliveness;
        private LocationBean location;
        private QualitiesBean qualities;
        private double pitch;
        private double roll;
        private int face_probability;

        public int getRotation_angle() {
            return rotation_angle;
        }

        public void setRotation_angle(int rotation_angle) {
            this.rotation_angle = rotation_angle;
        }

        public double getYaw() {
            return yaw;
        }

        public void setYaw(double yaw) {
            this.yaw = yaw;
        }

        public double getFaceliveness() {
            return faceliveness;
        }

        public void setFaceliveness(double faceliveness) {
            this.faceliveness = faceliveness;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public QualitiesBean getQualities() {
            return qualities;
        }

        public void setQualities(QualitiesBean qualities) {
            this.qualities = qualities;
        }

        public double getPitch() {
            return pitch;
        }

        public void setPitch(double pitch) {
            this.pitch = pitch;
        }

        public double getRoll() {
            return roll;
        }

        public void setRoll(double roll) {
            this.roll = roll;
        }

        public int getFace_probability() {
            return face_probability;
        }

        public void setFace_probability(int face_probability) {
            this.face_probability = face_probability;
        }

        public static class LocationBean {
            /**
             * width : 96
             * top : 73
             * height : 96
             * left : 283
             */

            private int width;
            private int top;
            private int height;
            private int left;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }
        }

        public static class QualitiesBean {
            /**
             * illumination : 211
             * occlusion : {"right_eye":0,"left_eye":0.039751552045345,"left_cheek":0.12623985111713,"mouth":0,"nose":0,"chin":0.037661049515009,"right_cheek":0.024720622226596}
             * completeness : 1
             * type : {"cartoon":0,"human":0}
             * blur : 2.5251445032182E-11
             */

            private int illumination;
            private OcclusionBean occlusion;
            private int completeness;
            private TypeBean type;
            private double blur;

            public int getIllumination() {
                return illumination;
            }

            public void setIllumination(int illumination) {
                this.illumination = illumination;
            }

            public OcclusionBean getOcclusion() {
                return occlusion;
            }

            public void setOcclusion(OcclusionBean occlusion) {
                this.occlusion = occlusion;
            }

            public int getCompleteness() {
                return completeness;
            }

            public void setCompleteness(int completeness) {
                this.completeness = completeness;
            }

            public TypeBean getType() {
                return type;
            }

            public void setType(TypeBean type) {
                this.type = type;
            }

            public double getBlur() {
                return blur;
            }

            public void setBlur(double blur) {
                this.blur = blur;
            }

            public static class OcclusionBean {
                /**
                 * right_eye : 0
                 * left_eye : 0.039751552045345
                 * left_cheek : 0.12623985111713
                 * mouth : 0
                 * nose : 0
                 * chin : 0.037661049515009
                 * right_cheek : 0.024720622226596
                 */

                private int right_eye;
                private double left_eye;
                private double left_cheek;
                private int mouth;
                private int nose;
                private double chin;
                private double right_cheek;

                public int getRight_eye() {
                    return right_eye;
                }

                public void setRight_eye(int right_eye) {
                    this.right_eye = right_eye;
                }

                public double getLeft_eye() {
                    return left_eye;
                }

                public void setLeft_eye(double left_eye) {
                    this.left_eye = left_eye;
                }

                public double getLeft_cheek() {
                    return left_cheek;
                }

                public void setLeft_cheek(double left_cheek) {
                    this.left_cheek = left_cheek;
                }

                public int getMouth() {
                    return mouth;
                }

                public void setMouth(int mouth) {
                    this.mouth = mouth;
                }

                public int getNose() {
                    return nose;
                }

                public void setNose(int nose) {
                    this.nose = nose;
                }

                public double getChin() {
                    return chin;
                }

                public void setChin(double chin) {
                    this.chin = chin;
                }

                public double getRight_cheek() {
                    return right_cheek;
                }

                public void setRight_cheek(double right_cheek) {
                    this.right_cheek = right_cheek;
                }
            }

            public static class TypeBean {
                /**
                 * cartoon : 0
                 * human : 0
                 */

                private int cartoon;
                private int human;

                public int getCartoon() {
                    return cartoon;
                }

                public void setCartoon(int cartoon) {
                    this.cartoon = cartoon;
                }

                public int getHuman() {
                    return human;
                }

                public void setHuman(int human) {
                    this.human = human;
                }
            }
        }
    }
}
