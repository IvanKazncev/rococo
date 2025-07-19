package guru.qa.rococo.jupiter.extension;

import guru.qa.rococo.entity.user.Authority;
import guru.qa.rococo.entity.user.AuthorityEntity;
import guru.qa.rococo.entity.user.UserAuthEntity;
import guru.qa.rococo.entity.user.UserDataEntity;
import guru.qa.rococo.jupiter.annotation.CreatingUser;
import guru.qa.rococo.service.DataService;
import guru.qa.rococo.utils.FakerData;
import io.qameta.allure.Allure;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreatingUserImpl implements BeforeEachCallback, ParameterResolver,TestWatcher {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreatingUser.class);

    List<AuthorityEntity> authorityEntityList = new ArrayList<>();
    UserAuthEntity userAuthEntity = new UserAuthEntity();
    AuthorityEntity aeRead = new AuthorityEntity();
    AuthorityEntity aeWrite = new AuthorityEntity();
    UserDataEntity userDataEntity = new UserDataEntity();

    @Override
    public void beforeEach(@NotNull ExtensionContext context) throws Exception {
        CreatingUser creatingUser = context.getRequiredTestMethod().getAnnotation(CreatingUser.class);
        if (creatingUser != null) {

            userAuthEntity.setId(UUID.randomUUID());
            userAuthEntity.setPassword(FakerData.getPassword());
            userAuthEntity.setUsername(FakerData.getLogin());
            userAuthEntity.setEnabled(true);
            userAuthEntity.setAccountNonExpired(true);
            userAuthEntity.setAccountNonLocked(true);
            userAuthEntity.setCredentialsNonExpired(true);


            aeRead.setId(UUID.randomUUID());
            aeRead.setAuthority(Authority.read);
            aeRead.setUser(userAuthEntity);


            aeWrite.setId(UUID.randomUUID());
            aeWrite.setAuthority(Authority.write);
            aeWrite.setUser(userAuthEntity);

            authorityEntityList.add(aeRead);
            authorityEntityList.add(aeWrite);
            userAuthEntity.setAuthorities(authorityEntityList);


            userDataEntity.setId(UUID.randomUUID());
            userDataEntity.setUsername(userAuthEntity.getUsername());
            if (creatingUser.firstName().isEmpty()) {
                userDataEntity.setFirstname(null);
            } else {
                userDataEntity.setFirstname(creatingUser.firstName());
            }
            if (creatingUser.lastName().isEmpty()) {
                userDataEntity.setLastname(null);
            } else {
                userDataEntity.setLastname(creatingUser.lastName());
            }
            userDataEntity.setAvatar(null);

            new DataService().addUser(userAuthEntity, userDataEntity);
            System.out.println(userAuthEntity.getUsername());

            context.getStore(NAMESPACE).put(context.getUniqueId(), userAuthEntity);

        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserAuthEntity.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), UserAuthEntity.class);
    }

    @SneakyThrows
    @Override
    public void testSuccessful(ExtensionContext context) {
        new DataService().deleteUserData(userAuthEntity.getUsername());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable throwable) {
        Allure.addAttachment("Login", userAuthEntity.getUsername());
        Allure.addAttachment("Password", userAuthEntity.getPassword());
    }

}
