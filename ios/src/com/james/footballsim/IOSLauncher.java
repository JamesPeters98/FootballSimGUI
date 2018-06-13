package com.james.footballsim;

import com.badlogic.gdx.Gdx;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.james.footballsim.FootballSim;
import org.robovm.apple.uikit.UIScreen;

public class IOSLauncher extends IOSApplication.Delegate {
    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        double scale = UIScreen.getMainScreen().getNativeScale();
        System.out.println( "Scale:"+scale);
        IOSApplication  app = new IOSApplication(new FootballSim(scale), config);
        return app;
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}