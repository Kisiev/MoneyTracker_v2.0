package money.android.bignerdranch.com.moneytracker.UI.utils;

/**
 * Created by User on 07.10.2016.
 */

public final class ConstantsManager {
    public static final String REGISTRATION_SUCCEED = "success";
    public static final String LOGIN_BUSY = "Login busy already";
    public static final String SHARED_PREF = "shared_pref";
    public static final String WRONG_LOGIN = "Wrong login";

    public static final String TOKEN_KEY = "token_key";
    public static final String GOOGLE_TOKEN_KEY = "google_token_key";
    public static final String LOGIN_SUCCEED = "success";
    private final static String G_PLUS_SCOPE =
            "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE =
            "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE =
            "https://www.googleapis.com/auth/userinfo.email";
    public final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;


}
