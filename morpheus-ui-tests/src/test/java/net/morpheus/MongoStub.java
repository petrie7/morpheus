package net.morpheus;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import java.io.IOException;

public class MongoStub {

    private MongodExecutable mongodExecutable;
    private final MongodStarter starter;
    private final IMongodConfig mongodConfig;

    public MongoStub() throws IOException {
        starter = MongodStarter.getDefaultInstance();

        mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(27017, Network.localhostIsIPv6()))
                .build();

    }

    public void start() throws IOException {
        mongodExecutable = starter.prepare(mongodConfig);
    }

    public void stop() {
        mongodExecutable.stop();
    }
}
