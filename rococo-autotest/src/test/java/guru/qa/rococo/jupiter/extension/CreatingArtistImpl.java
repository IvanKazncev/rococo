package guru.qa.rococo.jupiter.extension;

import com.shared.proto.artists.AddArtistResponse;
import guru.qa.rococo.jupiter.annotation.CreatingArtist;
import guru.qa.rococo.service.GrpcArtistService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.*;


public class CreatingArtistImpl implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreatingArtist.class);

    GrpcArtistService grpcArtistService = new GrpcArtistService();
    AddArtistResponse addArtistResponse;

    @Override
    public void beforeEach(@NotNull ExtensionContext context) throws Exception {
        var anno =  context.getRequiredTestMethod().getAnnotation(CreatingArtist.class);
        if (anno != null) {
            addArtistResponse =  grpcArtistService.addArtist();
            System.out.println(addArtistResponse.getArtist().getName());
            context.getStore(NAMESPACE).put(context.getUniqueId(), addArtistResponse);
        }
    }

    @Override
    public boolean supportsParameter(@NotNull ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(AddArtistResponse.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, @NotNull ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), AddArtistResponse.class);
    }
}
