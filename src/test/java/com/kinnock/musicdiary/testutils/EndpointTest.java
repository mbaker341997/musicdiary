package com.kinnock.musicdiary.testutils;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class EndpointTest {
  private final MockHttpServletRequestBuilder request;
  private final Object requestBody;
  private final Object responseBody;
  private final Exception exception;
  private final ResultMatcher resultMatcher;


  public MockHttpServletRequestBuilder getRequest() {
    return request;
  }

  public Object getRequestBody() {
    return requestBody;
  }

  public Object getResponseBody() {
    return responseBody;
  }

  public ResultMatcher getResultMatcher() {
    return resultMatcher;
  }

  public Exception getException() {
    return exception;
  }

  private EndpointTest(Builder builder) {
    this.request = builder.request;
    this.requestBody = builder.requestBody;
    this.responseBody = builder.responseBody;
    this.exception = builder.exception;
    this.resultMatcher = builder.resultMatcher;
  }

  public static class Builder {
    private final MockHttpServletRequestBuilder request;
    private final ResultMatcher resultMatcher;
    private Exception exception;
    private Object requestBody;
    private Object responseBody;


    public Builder(MockHttpServletRequestBuilder request, ResultMatcher resultMatcher) {
      this.request = request;
      this.resultMatcher = resultMatcher;
    }

    public Builder setRequestBody(Object requestBody) {
      this.requestBody = requestBody;
      return this;
    }

    public Builder setResponseBody(Object responseBody) {
      this.responseBody = responseBody;
      return this;
    }

    public Builder setException(Exception exception) {
      this.exception = exception;
      return this;
    }

    public EndpointTest build() {
      return new EndpointTest(this);
    }
  }
}
