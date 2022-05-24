import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources("classpath:config.properties")
public interface ConfigServer extends Config {

    @Key("otusUrl")
    String otusUrl();

    @Key("email")
    String email();

    @Key("password")
    String password();
}
