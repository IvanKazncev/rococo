package guru.qa.rococo.jupiter.extension;

import com.shared.proto.paintings.AddPaintingResponse;
import guru.qa.rococo.jupiter.annotation.CreatingPicture;
import guru.qa.rococo.service.GrpcPictureService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.*;

public class CreatingPictureImpl implements BeforeEachCallback, ParameterResolver{

    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CreatingPicture.class);

    GrpcPictureService grpcPictureService = new GrpcPictureService();
    AddPaintingResponse addPaintingResponse;

    @Override
    public void beforeEach(@NotNull ExtensionContext context) throws Exception {
        var anno = context.getRequiredTestMethod().getAnnotation(CreatingPicture.class);
        if (anno != null) {
            addPaintingResponse = grpcPictureService.addPicture();
            context.getStore(NAMESPACE).put(context.getUniqueId(), addPaintingResponse);
        }
    }

    @Override
    public boolean supportsParameter(@NotNull ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(AddPaintingResponse.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, @NotNull ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), AddPaintingResponse.class);
    }
}
