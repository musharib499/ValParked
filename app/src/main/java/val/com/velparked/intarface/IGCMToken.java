package val.com.velparked.intarface;

/**
 * Created by User on 8/26/2015.
 *
 *  Exposed method notify BaseActivity
 * that GCM push token is generated and
 * then base activity update this token on server.
 *
 */
public interface IGCMToken {
    void receivedToken();
}
