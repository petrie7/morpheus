package net.morpheus;

import net.morpheus.config.MorpheusApplicationConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class Runner {

    public static void main(String[] args) {
        new Runner().startMorpheus();
    }

    public void startMorpheus() {
        new SpringApplicationBuilder(MorpheusApplicationConfig.class)
                .bannerMode(Banner.Mode.CONSOLE)
                .run();
    }
}
