package guru.qa.rococo.grpc;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


import java.io.IOException;


public class PictureGrpc {

    public ManagedChannel getStub() {
        return ManagedChannelBuilder.forAddress("127.0.0.1", 50003).usePlaintext().build();
    }

}
