package cenco.xz.fangliang.wisdom.weed.thumber.bean;

/**
 * Created by Administrator on 2018/4/26.
 */
public class CarNumberBody {


    public CarNumberBody(String app_id, String image) {
        this.app_id = app_id;
        this.image = image;
    }

    /**
     * {
     "app_id": "123456",
     "image": "SALDKHKAFLASD",
     "url": "SALDKHKAFLASD",
     "session_id": "1000000111111"
     }
     */




    private String app_id;
    private String image;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
