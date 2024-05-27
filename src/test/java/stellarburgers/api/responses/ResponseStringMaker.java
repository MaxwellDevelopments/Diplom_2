package stellarburgers.api.responses;

import io.restassured.response.Response;

public interface ResponseStringMaker {
    String makePrettyResponse(Response response);
}
