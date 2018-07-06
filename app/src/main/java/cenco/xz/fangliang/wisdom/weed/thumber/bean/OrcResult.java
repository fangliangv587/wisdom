package cenco.xz.fangliang.wisdom.weed.thumber.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26.
 */
public class OrcResult {


    /**
     * errorcode : 0
     * errormsg : OK
     * items : [{"itemstring":"手机","itemcoord":{"x":0,"y":1,"width":2,"height":3},"words":[{"character":"手","confidence":98.99},{"character":"机","confidence":87.99}]}]
     * session_id : xxxxxx
     */

    private int errorcode;
    private String errormsg;
    private String session_id;
    private List<ItemsBean> items;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrcResult{" +
                "errorcode=" + errorcode +
                ", errormsg='" + errormsg + '\'' +
                ", session_id='" + session_id + '\'' +
                ", items=" + items +
                '}';
    }

    public static class ItemsBean {
        /**
         * itemstring : 手机
         * itemcoord : {"x":0,"y":1,"width":2,"height":3}
         * words : [{"character":"手","confidence":98.99},{"character":"机","confidence":87.99}]
         */

        private String itemstring;
        private ItemcoordBean itemcoord;
        private List<WordsBean> words;

        public String getItemstring() {
            return itemstring;
        }

        public void setItemstring(String itemstring) {
            this.itemstring = itemstring;
        }

        public ItemcoordBean getItemcoord() {
            return itemcoord;
        }

        public void setItemcoord(ItemcoordBean itemcoord) {
            this.itemcoord = itemcoord;
        }

        public List<WordsBean> getWords() {
            return words;
        }

        public void setWords(List<WordsBean> words) {
            this.words = words;
        }

        @Override
        public String toString() {
            return "ItemsBean{" +
                    "itemstring='" + itemstring + '\'' +
                    ", itemcoord=" + itemcoord +
                    ", words=" + words +
                    '}';
        }

        public static class ItemcoordBean {
            /**
             * x : 0
             * y : 1
             * width : 2
             * height : 3
             */

            private int x;
            private int y;
            private int width;
            private int height;

            public int getX() {
                return x;
            }

            public void setX(int x) {
                this.x = x;
            }

            public int getY() {
                return y;
            }

            public void setY(int y) {
                this.y = y;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }

        public static class WordsBean {
            /**
             * character : 手
             * confidence : 98.99
             */

            private String character;
            private double confidence;

            public String getCharacter() {
                return character;
            }

            public void setCharacter(String character) {
                this.character = character;
            }

            public double getConfidence() {
                return confidence;
            }

            public void setConfidence(double confidence) {
                this.confidence = confidence;
            }
        }
    }
}
