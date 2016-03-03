package net.morpheus;

import net.morpheus.config.MorpheusApplicationConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class MorpheusRunner {

    public static void main(String[] args) {
        new MorpheusRunner().startMorpheus();
    }

    public void startMorpheus() {
        new SpringApplicationBuilder(MorpheusApplicationConfig.class)
                .bannerMode(Banner.Mode.CONSOLE)
                .run();
    }
}
