package com.example.batterent;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

public class WalkThroughActivity extends WelcomeActivity {
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultTitleTypefacePath("Montserrat-Bold.ttf")
                .defaultHeaderTypefacePath("Montserrat-Bold.ttf")
                .page(new BasicPage(R.drawable.ui,
                        "Header",
                        "More text.")
                        .background(R.color.colorPrimary)
                )                .page(new BasicPage(R.drawable.ui,
                        "Header",
                        "More text.")
                        .background(R.color.colorPrimary)
                )
                .page(new BasicPage(R.drawable.ui,
                        "Header",
                        "More text.")
                        .background(R.color.colorPrimary)
                )
                .swipeToDismiss(true)
                .exitAnimation(android.R.anim.fade_out)
                .build();
    }

}
