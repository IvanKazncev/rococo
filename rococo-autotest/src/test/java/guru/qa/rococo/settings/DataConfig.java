package guru.qa.rococo.settings;

import lombok.Getter;

@Getter
public enum DataConfig {

    USER_DATA_JDBC_DATA("jdbc:postgresql://127.0.0.1:5432/rococo-users", "postgres", "secret"),
    AUTH_DATA_JDBC_DATA("jdbc:postgresql://127.0.0.1:5432/rococo-auth", "postgres", "secret");

    final String url;

    DataConfig(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
    }

    final String login;
    final String password;
}
