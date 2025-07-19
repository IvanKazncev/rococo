package guru.qa.rococo.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ArtistGrpc {

    public ManagedChannel getStub() {
        return ManagedChannelBuilder.forAddress("127.0.0.1", 50001).usePlaintext().build();
    }
}
