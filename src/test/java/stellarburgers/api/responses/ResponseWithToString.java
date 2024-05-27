package stellarburgers.api.responses;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ResponseWithToString implements Response {
    private final Response response;
    private final String toString;

    public ResponseWithToString(Response response, ResponseStringMaker responseStringMaker) {
        this.response = response;
        this.toString = responseStringMaker.makePrettyResponse(response);
    }


    @Override
    public String print() {
        return response.print();
    }

    @Override
    public String prettyPrint() {
        return response.prettyPrint();
    }

    @Override
    public Response peek() {
        return response.peek();
    }

    @Override
    public Response prettyPeek() {
        return response.prettyPeek();
    }

    @Override
    public <T> T as(Class<T> aClass) {
        return response.as(aClass);
    }

    @Override
    public <T> T as(Class<T> aClass, ObjectMapperType objectMapperType) {
        return response.as(aClass, objectMapperType);
    }

    @Override
    public <T> T as(Class<T> aClass, ObjectMapper objectMapper) {
        return response.as(aClass, objectMapper);
    }

    @Override
    public <T> T as(TypeRef<T> typeRef) {
        return response.as(typeRef);
    }

    @Override
    public <T> T as(Type type) {
        return response.as(type);
    }

    @Override
    public <T> T as(Type type, ObjectMapperType objectMapperType) {
        return response.as(type, objectMapperType);
    }

    @Override
    public <T> T as(Type type, ObjectMapper objectMapper) {
        return response.as(type, objectMapper);
    }

    @Override
    public JsonPath jsonPath() {
        return response.jsonPath();
    }

    @Override
    public JsonPath jsonPath(JsonPathConfig jsonPathConfig) {
        return response.jsonPath();
    }

    @Override
    public XmlPath xmlPath() {
        return response.xmlPath();
    }

    @Override
    public XmlPath xmlPath(XmlPathConfig xmlPathConfig) {
        return response.xmlPath(xmlPathConfig);
    }

    @Override
    public XmlPath xmlPath(XmlPath.CompatibilityMode compatibilityMode) {
        return response.xmlPath(compatibilityMode);
    }

    @Override
    public XmlPath htmlPath() {
        return response.htmlPath();
    }

    @Override
    public <T> T path(String s, String... strings) {
        return response.path(s, strings);
    }

    @Override
    public String asString() {
        return response.asString();
    }

    @Override
    public String asPrettyString() {
        return response.asPrettyString();
    }

    @Override
    public byte[] asByteArray() {
        return response.asByteArray();
    }

    @Override
    public InputStream asInputStream() {
        return response.asInputStream();
    }

    @Override
    public Response andReturn() {
        return response.andReturn();
    }

    @Override
    public Response thenReturn() {
        return response.thenReturn();
    }

    @Override
    public ResponseBody body() {
        return response.body();
    }

    @Override
    public ResponseBody getBody() {
        return response.getBody();
    }

    @Override
    public Headers headers() {
        return response.headers();
    }

    @Override
    public Headers getHeaders() {
        return response.getHeaders();
    }

    @Override
    public String header(String s) {
        return response.header(s);
    }

    @Override
    public String getHeader(String s) {
        return response.getHeader(s);
    }

    @Override
    public Map<String, String> cookies() {
        return response.cookies();
    }

    @Override
    public Cookies detailedCookies() {
        return response.detailedCookies();
    }

    @Override
    public Map<String, String> getCookies() {
        return response.getCookies();
    }

    @Override
    public Cookies getDetailedCookies() {
        return response.getDetailedCookies();
    }

    @Override
    public String cookie(String s) {
        return response.cookie(s);
    }

    @Override
    public String getCookie(String s) {
        return response.getCookie(s);
    }

    @Override
    public Cookie detailedCookie(String s) {
        return response.detailedCookie(s);
    }

    @Override
    public Cookie getDetailedCookie(String s) {
        return response.getDetailedCookie(s);
    }

    @Override
    public String contentType() {
        return response.contentType();
    }

    @Override
    public String getContentType() {
        return response.getContentType();
    }

    @Override
    public String statusLine() {
        return response.statusLine();
    }

    @Override
    public String getStatusLine() {
        return response.getStatusLine();
    }

    @Override
    public String sessionId() {
        return response.sessionId();
    }

    @Override
    public String getSessionId() {
        return response.getSessionId();
    }

    @Override
    public int statusCode() {
        return response.statusCode();
    }

    @Override
    public int getStatusCode() {
        return response.getStatusCode();
    }

    @Override
    public long time() {
        return response.time();
    }

    @Override
    public long timeIn(TimeUnit timeUnit) {
        return response.timeIn(timeUnit);
    }

    @Override
    public long getTime() {
        return response.getTime();
    }

    @Override
    public long getTimeIn(TimeUnit timeUnit) {
        return response.getTimeIn(timeUnit);
    }

    @Override
    public ValidatableResponse then() {
        return response.then();
    }

    @Override
    public String toString() {
        return toString;
    }
}
