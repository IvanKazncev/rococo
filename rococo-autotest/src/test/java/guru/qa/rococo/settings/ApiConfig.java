package guru.qa.rococo.settings;

import lombok.Getter;


@Getter
public enum ApiConfig {

    AUTH_SERVICE_URL("http://127.0.0.1:9000");

    final String url;

    ApiConfig(String url) {
        this.url = url;
    }
}
