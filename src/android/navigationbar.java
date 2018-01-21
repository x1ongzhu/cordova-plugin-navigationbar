package cn.x1ongzhu.navigationbar;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.izouma.lingxiancar.R;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class echoes a string called from JavaScript.
 */
public class navigationbar extends CordovaPlugin {
    private RelativeLayout navBar;
    private TextView title;
    private ImageView ivBack;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        } else if (action.equals("create")) {
            create();
            return true;
        } else if (action.equals("setBg")) {
            setBg(args, callbackContext);
            return true;
        } else if (action.equals("setGradientBg")) {
            setGradientBg(args, callbackContext);
            return true;
        } else if (action.equals("setStyle")) {
            setStyle(args, callbackContext);
            return true;
        } else if (action.equals("setTitle")) {
            setTitle(args, callbackContext);
            return true;
        } else if (action.equals("showBack")) {
            showBack(args, callbackContext);
            return true;
        } else if (action.equals("hideBack")) {
            hideBack(args, callbackContext);
            return true;
        }
        return false;
    }

    private void create() {
        if (navBar != null) {
            return;
        }
        navBar = new RelativeLayout(cordova.getActivity());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, cordova.getActivity().getResources().getDisplayMetrics()));
        navBar.setLayoutParams(params);
        navBar.setBackgroundColor(ContextCompat.getColor(cordova.getActivity(), android.R.color.white));

        title = new TextView(cordova.getActivity());
        title.setText("");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        title.setTextColor(Color.BLACK);
        RelativeLayout.LayoutParams titleParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParam.addRule(RelativeLayout.CENTER_IN_PARENT);
        title.setLayoutParams(titleParam);
        navBar.addView(title);

        ivBack = new ImageView(cordova.getActivity());
        ivBack.setImageResource(R.drawable.icon_back_dark);
        RelativeLayout.LayoutParams backParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        backParam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        backParam.addRule(RelativeLayout.CENTER_VERTICAL);
        ivBack.setLayoutParams(backParam);
        ivBack.setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, cordova.getActivity().getResources().getDisplayMetrics()),
                0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, cordova.getActivity().getResources().getDisplayMetrics()),
                0);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.backHistory();
            }
        });
        navBar.addView(ivBack);

        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) webView.getView().getLayoutParams();
                params1.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, cordova.getActivity().getResources().getDisplayMetrics());
                webView.getView().setLayoutParams(params1);

                ViewGroup view = (ViewGroup) cordova.getActivity().getWindow().getDecorView();
                FrameLayout content = view.findViewById(android.R.id.content);
                content.addView(navBar);
            }
        });
    }

    private void setBg(JSONArray args, CallbackContext callbackContext) {
        if (navBar == null) {
            return;
        }
        try {
            final int color = Color.parseColor(args.getString(0));
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    navBar.setBackgroundColor(color);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setGradientBg(JSONArray args, CallbackContext callbackContext) {
        if (navBar == null) {
            return;
        }
        try {
            int[] colors = new int[args.length()];
            for (int i = 0; i < args.length(); i++) {
                colors[i] = Color.parseColor(args.getString(i));
            }
            final GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
            gradientDrawable.setBounds(0, 0, navBar.getMeasuredWidth(), navBar.getMeasuredHeight());
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    navBar.setBackground(gradientDrawable);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setTitle(JSONArray args, CallbackContext callbackContext) {
        if (navBar == null) {
            return;
        }
        try {
            final String t = args.getString(0);
            cordova.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    title.setText(t);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setStyle(JSONArray args, CallbackContext callbackContext) {
        if (navBar == null) {
            return;
        }
        try {
            String style = args.getString(0);
            if ("light".equals(style.toLowerCase())) {
                title.setTextColor(Color.WHITE);
                ivBack.setImageResource(R.drawable.icon_back_light);
            } else if ("dark".equals(style.toLowerCase())) {
                title.setTextColor(Color.BLACK);
                ivBack.setImageResource(R.drawable.icon_back_dark);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showBack(JSONArray args, CallbackContext callbackContext) {
        if (navBar == null) {
            return;
        }
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivBack.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideBack(JSONArray args, CallbackContext callbackContext) {
        if (navBar == null) {
            return;
        }
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ivBack.setVisibility(View.GONE);
            }
        });
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

}
