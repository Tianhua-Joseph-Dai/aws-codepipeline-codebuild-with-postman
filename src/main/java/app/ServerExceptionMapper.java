package app;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServerExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable t) {

        // Klasse fängt alle internen Fehler, gibt sie auf der Server-Konsole aus und ...
        t.printStackTrace();

        // ... sendet sie auch an den Client
        return Response.serverError()
                .entity(t.getMessage())
                .build();
    }
}